package com.lambdaschool.javasafezoos.repositories

import com.lambdaschool.javasafezoos.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long>
{
    fun findByUsername(username: String): User
}
