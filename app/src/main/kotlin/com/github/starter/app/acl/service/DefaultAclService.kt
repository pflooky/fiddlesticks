package com.github.starter.app.acl.service

import com.github.starter.app.acl.model.Access
import com.github.starter.app.acl.model.EntityAccess
import com.github.starter.app.acl.model.Resource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Predicate

@Service
class DefaultAclService(@Qualifier("MergedUserAccess") val userAccessList: List<EntityAccess>,
                        @Qualifier("TeamMembers") val teamMembers: Map<String, Set<String>>,
                        val resourceAccessMap: List<Resource>) : AclService {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(DefaultAclService::class.java)
    }

    override fun getUserAccess(user: String): EntityAccess {
        return userAccessList.find { x -> x.name == user } ?: EntityAccess(name = user)
    }

    override fun getUserAccessForResource(user: String, accessLevel: String,
                                          catalog: String, schema: String?, objectValue: String?): Boolean {
        val userAccess = getUserAccess(user)

        return userAccess.access.any { access ->
            val resources = access.resource
            val predicateList = mutableListOf(resources.contains("$catalog/*"))

            if (!schema.isNullOrBlank()) {
                predicateList.add(resources.contains("$catalog/$schema/*"))
                predicateList.add(resources.contains("$catalog/$schema"))
            } else if (!objectValue.isNullOrBlank()){
                predicateList.add(resources.contains("$catalog/$schema/$objectValue"))
            }

            access.level == accessLevel && predicateList.any { x -> x }
        }
    }

    override fun getResourceDetails(resource: String): Resource {
        return resourceAccessMap.first { access -> access.name == resource }
    }

    override fun getResourceAccess(resource: String): List<EntityAccess> {
        return userAccessList.map { entity ->
            val filteredResources = entity.access.map { access ->
                val x = access.resource.filter { r -> r.contains(resource) }
                Access(access.level, x)
            }.filter { y -> y.resource.isNotEmpty() }

            EntityAccess(entity.name, filteredResources)
        }.filter { entity -> entity.access.isNotEmpty() }
    }

    override fun getTeamMembers(team: String): Set<String> {
        return teamMembers.getOrDefault(team, Collections.emptySet())
    }

}
