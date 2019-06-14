package com.lambdaschool.javasafezoos.services

import com.lambdaschool.javasafezoos.models.Role
import com.lambdaschool.javasafezoos.repositories.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityNotFoundException
import java.util.ArrayList

@Service(value = "roleService")
class RoleServiceImpl : RoleService
{
    @Autowired
    internal var rolerepos: RoleRepository? = null

    override fun findAll(): List<Role>
    {
        val list = ArrayList<Role>()
        rolerepos!!.findAll().iterator().forEachRemaining({ list.add(it) })
        return list
    }


    override fun findRoleById(id: Long): Role
    {
        return rolerepos!!.findById(id).orElseThrow({ EntityNotFoundException(java.lang.Long.toString(id)) })
    }


    override fun delete(id: Long)
    {
        rolerepos!!.findById(id).orElseThrow({ EntityNotFoundException(java.lang.Long.toString(id)) })
        rolerepos!!.deleteById(id)
    }


    @Transactional
    override fun save(role: Role): Role
    {
        return rolerepos!!.save(role)
    }
}
