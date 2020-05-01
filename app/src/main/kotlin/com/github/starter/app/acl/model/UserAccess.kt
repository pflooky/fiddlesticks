package com.github.starter.app.acl.model

import java.util.Collections

data class Resource(
        val name: String = "",
        val type: String = "",
        val schema: List<Schema> = Collections.emptyList()
)

data class Schema(
        val name: String = "",
        val type: String = "",
        val objects: List<String> = Collections.emptyList()
)

data class EntityAccess(
        val name: String = "",
        val access: List<Access> = Collections.emptyList()
)

data class Access(
        val level: String = "",
        val resource: List<String> = Collections.emptyList()
)
