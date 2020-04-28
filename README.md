# Fiddlesticks
Kotlin ACL Service

[![Build](https://travis-ci.com/skhatri/microservices-starter-kotlin.svg?branch=master)](https://travis-ci.com/github/skhatri/microservices-starter-kotlin)
[![Code Coverage](https://img.shields.io/codecov/c/github/skhatri/microservices-starter-kotlin/master.svg)](https://codecov.io/github/skhatri/microservices-starter-kotlin?branch=master)
[![Known Vulnerabilities](https://snyk.io/test/github/skhatri/microservices-starter-kotlin/badge.svg?targetFile=build.gradle.kts)](https://snyk.io/test/github/skhatri/microservices-starter-kotlin?targetFile=build.gradle.kts)


### ACL Features
1. Define resources and their users with role
1. Define users within teams
1. Define time based permissions
1. Define DevOps roster
1. Ability to add super users/service accounts

---

### Endpoints

|Request| Path          | Response                                         | Description                                |
|-------|---------------|--------------------------------------------------|--------------------------------------------|
|GET|/access/user/\<user>|`app/src/test/resources/response/user-access.json`|What resources does this user have access to and their details|
|GET|/access/user/\<user>/resource/\<resource>/subResource/\<subResource>|`app/src/test/resources/response/user-subresouce-access.json`|Does this user have access to this sub resource|
|GET|/access/resource/\<resource>|`app/src/test/resources/response/resource-access.json`|Who has access to this resource|
|GET|/access/resource/\<resource>/subResource/\<subResource>|`app/src/test/resources/response/subresource-access.json`|Who has access to this sub resource|


### Running locally
1. Using docker, `docker-compose up`
1. Run Application.kt
1. https://localhost:8080/ldap/members/admin
1. https://localhost:8080/ldap/user/admin/description
1. `curl -k -H "Content-Type: application/json" -X POST https://localhost:8080/jwt/create -d "hello world"`

### logging
log4j2

### code analysis
sonar

### testing
junit 5

### code-coverage
Jacoco

### code-style
Google Checkstyle modified to be compatible with 8.30.
Method Length, File Length, Cyclomatic Complexity have been added.

### load-testing
Gatling

Load test can be run using one of the following two approaches
```
gradle load-testing:runTest
IDE - com.github.starter.todo.Runner
```

### vulnerability

Install snyk and authenticate for CLI session
```
npm install -g snyk
snyk auth
```

Publish results using

```
snyk monitor --all-sub-projects
```

### container
build image using
```
gradle jib 
```