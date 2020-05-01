//package com.github.starter.app.acl.service
//
//import com.github.starter.app.config.LdapConfig
//import com.github.starter.app.ldap.exception.UnknownUserException
//import org.apache.directory.api.ldap.model.cursor.EntryCursor
//import org.apache.directory.api.ldap.model.entry.DefaultEntry
//import org.apache.directory.api.ldap.model.entry.Entry
//import org.apache.directory.api.ldap.model.message.SearchScope
//import org.apache.directory.ldap.client.api.LdapConnection
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Assertions.assertTrue
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.Mockito
//import org.springframework.test.context.junit.jupiter.SpringExtension
//
//@DisplayName("Ldap Service Test")
//@ExtendWith(SpringExtension::class)
//internal class DefaultAclServiceTest {
//
//    private val ldapConnection: LdapConnection = Mockito.mock(LdapConnection::class.java)
//    private val entryCursor: EntryCursor = Mockito.mock(EntryCursor::class.java)
//    private val ldapConfig: LdapConfig = LdapConfig()
//    private val aclService: AclService
//    private val entry: Entry
//    private val baseDn = "dc=example,dc=org"
//
//    init {
//        ldapConfig.baseDn = baseDn
//        aclService = DefaultAclService(ldapConnection, ldapConfig)
//        entry = DefaultEntry(baseDn, "cn:admin", "sAMAccountName:admin", "description:The admin")
//    }
//
//    @BeforeEach
//    private fun createLdapBase() {
//        Mockito.`when`(entryCursor.iterator())
//                .thenReturn(mutableListOf<Entry>(DefaultEntry(baseDn, "cn:admin", "sAMAccountName:admin", "description:The admin")).iterator())
//        Mockito.`when`(ldapConnection.search(Mockito.matches(baseDn), Mockito.anyString(), Mockito.any(SearchScope::class.java)))
//                .thenReturn(entryCursor)
//    }
//
//    @Test
//    fun `test get members list`() {
//        //when
//        val result = aclService.checkMembers("admin")
//
//        //then
//        result.subscribe {
//            assertEquals(1, it.size)
//            val memberName = it.first()
//            assertEquals("admin", memberName)
//        }.dispose()
//    }
//
//    @Test
//    fun `get attribute of a user`() {
//        //when
//        val result = aclService.getAttribute("admin", "description")
//
//        //then
//        result.subscribe {
//            assertTrue(it.isNotEmpty())
//            assertEquals("The admin", it)
//        }.dispose()
//    }
//
//    @Test
//    fun `try get attribute of a user that doesn't exist`() {
//        //when
//        Mockito.`when`(entryCursor.iterator())
//                .thenReturn(mutableListOf<Entry>().iterator())
//
//        //then
//        assertThrows<UnknownUserException> {
//            aclService.getAttribute("peter", "description").block()
//        }
//    }
//}