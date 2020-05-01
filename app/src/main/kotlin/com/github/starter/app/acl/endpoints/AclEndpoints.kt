package com.github.starter.app.acl.endpoints

import com.github.starter.app.acl.model.EntityAccess
import com.github.starter.app.acl.model.Resource
import com.github.starter.app.acl.service.AclService
import com.github.starter.app.util.ResourceUtil
import com.github.starter.core.container.Container
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/acl")
class AclEndpoints(private val aclService: AclService) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(AclEndpoints::class.java)
    }

    /*
    Endpoint for:
    does this user have access to this resource?
    need to check:
    - is it time based user access?
    - what type of permission? (read, write, etc)
    - which part of the resource? (keyspace, schema etc)
     */

    @GetMapping("/user/{user}/access/resources")
    fun getUserAccess(@PathVariable("user") user: String): Container<EntityAccess> {
        LOGGER.info("task=user.access, user=$user, message=Attempting to get user access details")
        return Container(aclService.getUserAccess(user))
    }

    @GetMapping("/user/access")
    fun getUserResourceAccess(@RequestParam("user", required = true) user: String,
                              @RequestParam("accessLevel", required = true) accessLevel: String,
                              @RequestParam("catalog", required = true) catalog: String,
                              @RequestParam("schema", required = false) schema: String?,
                              @RequestParam("object", required = false) objectValue: String?): Container<Map<String, Any>> {
        val resourceValue = ResourceUtil.generateResourceFormat(catalog, schema, objectValue)
        val entityAccess = aclService.getUserAccessForResource(user, accessLevel, catalog, schema, objectValue)

        LOGGER.info("task=user.resource.access, access=$entityAccess, user=$user, accessLevel=$accessLevel, resource=$resourceValue")
        return Container(mapOf(
                Pair("access", entityAccess),
                Pair("user", user),
                Pair("accessLevel", accessLevel),
                Pair("resource", resourceValue)
        ))
    }

    @GetMapping("/resource/{resource}")
    fun getResourceDetails(@PathVariable("resource") resource: String): Container<Resource> {
        LOGGER.info("task=resource.details, resource=$resource, message=Attempting to get resource details")
        return Container(aclService.getResourceDetails(resource))
    }

    @GetMapping("/resource/access")
    fun getResourceAccess(@RequestParam("resource") resource: String): Container<Map<String, Any>> {
        LOGGER.info("task=resource.access.details, resource=$resource, message=Attempting to get resource access details")
        return Container(mapOf(
                Pair("resource", resource),
                Pair("access", aclService.getResourceAccess(resource))
        ))
    }

    @GetMapping("/teamMembers/{team}")
    fun getTeamMembers(@PathVariable("team") team: String): Container<Set<String>> {
        LOGGER.info("task=team.details, team=$team, message=Attempting to get team members")
        return Container(aclService.getTeamMembers(team))
    }

}
