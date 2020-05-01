package com.github.starter.app.acl.model

import java.util.Date

data class TimeBasedUserAccessModel(
        val resource: String,
        val subResource: String,
        val accessType: AccessType,
        val startDate: Date,
        val endDate: Date
)

data class TimeBasedResourceAccessModel(
        val user: String,
        val startDate: Date,
        val endDate: Date
)
