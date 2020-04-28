package com.github.starter.app.acl.endpoints

import com.github.starter.app.ldap.service.LdapService
import com.github.starter.core.container.Container
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/access")
class AclEndpoints(private val ldapService: LdapService) {

    /*
    Endpoint for:
    does this user have access to this resource?
    need to check:
    - is it time based user access?
    - what type of permission? (read, write, etc)
    - which part of the resource? (keyspace, schema etc)
     */

    @GetMapping("/members/{group}")
    fun getMembers(@PathVariable("group") group: String): Mono<Container<List<String>>> {
        return ldapService.checkMembers(group).map { Container(it) }
    }

    @GetMapping("/user/{user}/{attribute}")
    fun getAttribute(@PathVariable("user") user: String, @PathVariable("attribute") attribute: String): Mono<Container<String>> {
        return ldapService.getAttribute(user, attribute).map { Container(it) }
    }
}
