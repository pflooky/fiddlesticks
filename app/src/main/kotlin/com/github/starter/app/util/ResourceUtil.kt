package com.github.starter.app.util

class ResourceUtil {
    companion object {
        fun generateResourceFormat(catalog: String, schema: String?, objectValue: String?): String {
            return if (schema.isNullOrBlank() && objectValue.isNullOrBlank()) {
                "$catalog/*"
            } else if (objectValue.isNullOrBlank()) {
                "$catalog/$schema"
            } else {
                "$catalog/$schema/$objectValue"
            }
        }
    }
}