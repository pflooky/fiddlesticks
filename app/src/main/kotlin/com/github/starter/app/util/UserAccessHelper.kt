package com.github.starter.app.util

import com.github.starter.app.acl.exception.InvalidResourceException
import com.github.starter.app.acl.model.Access
import com.github.starter.app.acl.model.EntityAccess
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserAccessHelper(@Qualifier("TeamAccess") val teamAccess: List<EntityAccess>,
                       @Qualifier("UserAccess") val userAccess: List<EntityAccess>,
                       @Qualifier("TeamMembers") val teamMembers: Map<String, Set<String>>) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(UserAccessHelper::class.java)
    }

    @Bean("MergedUserAccess")
    fun createUserOverrideAccess(): List<EntityAccess> {
        return userAccess.map { user ->
            val overrideAccess = findTeamAccessForUser(user.name)
                    .map { teamAccess ->
                        mergeTeamAndUserAccess(teamAccess, user.access)
                    }.orElse(user.access)

            EntityAccess(user.name, overrideAccess)
        }
    }

    protected fun findTeamAccessForUser(user: String): Optional<List<Access>> {
        val teams = teamMembers.filter { team ->
            team.value.contains(user)
        }

        return if (teams.isNotEmpty()) {
            val numberOfTeams = teams.size
            val teamName = teams.keys.first()
            if (numberOfTeams > 1) {
                LOGGER.warn("task=team.access, user=$user, number-of-teams=$numberOfTeams," +
                        "message=User is part of multiple teams, only using the first team for access")
            }

            LOGGER.info("task=team.access, user=$user, team=$teamName, message=Using team's access as base access")
            val teamAccess = teamAccess.find { entityAccess -> entityAccess.name == teamName }

            return if (teamAccess != null) {
                Optional.of(teamAccess.access)
            } else {
                LOGGER.warn("task=team.access, user=$user, team=$teamName, message=Team does not have any resource access rules set")
                Optional.empty()
            }
        } else {
            LOGGER.warn("task=team.access, user=$user, message=User is not part of any team")
            Optional.empty()
        }
    }

    protected fun mergeTeamAndUserAccess(teamAccess: List<Access>, userAccess: List<Access>): List<Access> {
        val finalAccess = userAccess.map { access ->
            Pair(access.level, access.resource.toMutableList())
        }.toMap()

        teamAccess.forEach { tAccess ->
            tAccess.resource.forEach { resource ->
                if (addToUserAccess(userAccess, tAccess.level, resource)){
                    finalAccess.getOrDefault(tAccess.level, mutableListOf()).add(resource)
                }
            }
        }

        return finalAccess.map { entry -> Access(entry.key, entry.value) }
    }

    protected fun addToUserAccess(accessList: List<Access>, accessLevel: String, resource: String): Boolean {
        val sptResource = resource.split("/")
        val wildcardAccess = when (sptResource.size) {
            3 -> "${sptResource.first()}/${sptResource[1]}/*"
            2 -> "${sptResource.first()}/*"
            else -> {
                LOGGER.error("task=merge.team.user.access, resource=$resource," +
                        "message=Incorrect format for resource, should contain '/' as delimiter")
                throw InvalidResourceException(resource)
            }
        }

        //if current user does not have the resource access or wildcard access, add the team access
        return accessList.any { access ->
            access.level == accessLevel &&
                    !(access.resource.contains(resource) || access.resource.contains(wildcardAccess))
        }
    }
}