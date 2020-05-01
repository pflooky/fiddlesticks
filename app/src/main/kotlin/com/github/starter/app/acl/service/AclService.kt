package com.github.starter.app.acl.service

import com.github.starter.app.acl.model.EntityAccess
import com.github.starter.app.acl.model.Resource

interface AclService {
    fun getUserAccess(user: String): EntityAccess

    fun getUserAccessForResource(user: String, accessLevel: String, catalog: String, schema: String?, objectValue: String?): Boolean

    fun getResourceDetails(resource: String): Resource

    fun getResourceAccess(resource: String): List<EntityAccess>

    fun getTeamMembers(team: String): Set<String>
}
