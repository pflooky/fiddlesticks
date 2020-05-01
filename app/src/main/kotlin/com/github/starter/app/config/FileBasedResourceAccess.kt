package com.github.starter.app.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.CollectionType
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.starter.app.acl.model.EntityAccess
import com.github.starter.app.acl.model.Resource
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.io.File
import java.util.*

@Component
class FileBasedResourceAccess: ResourceAccess {

    companion object {
        val objectMapper = ObjectMapper(YAMLFactory())
    }

    @Bean
    override fun getResourceAccessConfig(): List<Resource> {
        val resourceAccessFiles = File(javaClass.getResource("/sample/resources").toURI()).listFiles()

        return resourceAccessFiles.map { file ->
            val resourceConfig = objectMapper.readValue(file, Resource::class.java)
            resourceConfig
        }
    }

    //TODO check if resource value is valid
    @Bean("TeamAccess")
    override fun getTeamAccessConfig(): List<EntityAccess> {
        val teamAccessFiles = File(javaClass.getResource("/sample/teams").toURI()).listFiles()
        val listType: CollectionType = objectMapper.typeFactory
                .constructCollectionType(ArrayList::class.java, EntityAccess::class.java)

        return teamAccessFiles.map { file ->
            val resourceConfig = objectMapper.readValue<List<EntityAccess>>(file, listType)
            resourceConfig
        }.flatten()
    }

    //TODO check if resource value is valid
    @Bean("UserAccess")
    override fun getUserAccessConfig(): List<EntityAccess> {
        val userAccessFiles = File(javaClass.getResource("/sample/users").toURI()).listFiles()
        val listType: CollectionType = objectMapper.typeFactory
                .constructCollectionType(ArrayList::class.java, EntityAccess::class.java)

        return userAccessFiles.map { file ->
            val resourceConfig = objectMapper.readValue<List<EntityAccess>>(file, listType)
            resourceConfig
        }.flatten()
    }

    @Bean("TeamMembers")
    override fun getTeamMembers(): Map<String, Set<String>> {
        val teamMembersFile = File(javaClass.getResource("/sample/teamMembers/team-members.yml").toURI())
        return objectMapper.readValue(teamMembersFile)
    }
}