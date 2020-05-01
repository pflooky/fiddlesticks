package com.github.starter.app.config

import com.github.starter.app.acl.model.EntityAccess
import com.github.starter.app.acl.model.Resource

interface ResourceAccess {
    fun getResourceAccessConfig(): List<Resource>

    fun getTeamAccessConfig(): List<EntityAccess>

    fun getUserAccessConfig(): List<EntityAccess>

    fun getTeamMembers(): Map<String, Set<String>>
}