package com.lambdaschool.javasafezoos.services

import com.lambdaschool.javasafezoos.models.User

interface UserService
{

    fun findAll(): List<User>

    fun findUserById(id: Long): User

    fun delete(id: Long)

    fun save(user: User): User

    fun update(user: User, id: Long): User
}