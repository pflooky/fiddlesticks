package com.github.starter.app.util

import com.github.starter.app.R.App.ACCESS_LEVEL_READ
import com.github.starter.app.R.App.ACCESS_LEVEL_WRITE
import com.github.starter.app.acl.model.Access
import com.github.starter.app.acl.model.EntityAccess
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class UserAccessHelperTest {

    companion object {
        private val teamAccess = listOf(
                EntityAccess("team-a", listOf(
                        Access(ACCESS_LEVEL_READ, listOf("cassandra-1/account/*", "kafka-1/*", "elastic/docs")),
                        Access(ACCESS_LEVEL_WRITE, listOf("cassandra-1/account/info", "kafka-1/*", "elastic/docs"))
                ))
        )

        private val userAccess = listOf(
                EntityAccess("pflook", listOf(
                        Access(ACCESS_LEVEL_READ, listOf("cassandra-1/*", "kafka-1/base", "elastic/docs")),
                        Access(ACCESS_LEVEL_WRITE, listOf("cassandra-1/account/historical", "elastic/ops"))
                ))
        )

        private val teamMembers = mapOf(Pair("team-a", setOf("pflook")))
        private val userAccessHelper = UserAccessHelper(teamAccess, userAccess, teamMembers)
    }

    @Test
    fun createUserOverrideAccess() {
        //when
        val overrideAccess = userAccessHelper.createUserOverrideAccess()

        //then
        assertEquals(1,  overrideAccess.size)
        assertEquals("pflook", overrideAccess.first().name)
        val readAccess = overrideAccess.first().access.first { x -> x.level == ACCESS_LEVEL_READ }
        val writeAccess = overrideAccess.first().access.first { x -> x.level == ACCESS_LEVEL_WRITE }
        val expectedReadResources = listOf("cassandra-1/*", "kafka-1/base", "elastic/docs")
        val expectedWriteResources = listOf("cassandra-1/account/historical", "cassandra-1/account/info", "kafka-1/*",
                "elastic/docs", "elastic/ops")

        expectedReadResources.forEach { expRead -> assertTrue(readAccess.resource.contains(expRead)) }
        expectedWriteResources.forEach { expWrite -> assertTrue(writeAccess.resource.contains(expWrite)) }
    }

}