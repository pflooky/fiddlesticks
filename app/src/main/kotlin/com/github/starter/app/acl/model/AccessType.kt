package com.github.starter.app.acl.model

enum class AccessType(val type: String) {
    READ("read"), WRITE("write"), ADMIN("admin")
}