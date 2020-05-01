package com.github.starter.app.acl.exception

class InvalidResourceException(resource: String) :
        RuntimeException("resource=$resource, message=Incorrect format for resource, should contain '/' as delimiter")