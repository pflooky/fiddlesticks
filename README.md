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


### Running locally
1. Run Application.kt
1. [Sample response of access check](https://localhost:8080/acl/user/access?user=user1&accessLevel=read&catalog=cassandra-1)
1. [Sample response of getting resources user has access to](https://localhost:8080/acl/user/user1/access/resources)
1. [Sample response of checking resource details](https://localhost:8080/acl/resource/cassandra-1)
1. [Sample response of checking who has access to a resource](https://localhost:8080/acl/resource/access?resource=cassandra-1)

Sample responses can also be found in `src/test/resources/responses`

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