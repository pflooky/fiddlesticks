package com.github.starter.app.ldap.service

import com.github.starter.app.ldap.model.TodoTask
import reactor.core.publisher.Mono

interface TodoService {
    fun listItems(): Mono<List<TodoTask>>

    fun findById(id: String): Mono<TodoTask>

    fun save(task: TodoTask): Mono<TodoTask>

    fun update(id: String, task: TodoTask): Mono<TodoTask>

    fun delete(id: String): Mono<Boolean>
}
