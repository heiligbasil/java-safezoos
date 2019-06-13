package com.lambdaschool.javasafezoos.services

import com.lambdaschool.javasafezoos.models.Role

interface RoleService
{
    fun findAll(): List<Role>

    fun findRoleById(id: Long): Role

    fun delete(id: Long)

    fun save(role: Role): Role
}