package com.lambdaschool.javasafezoos.services

import com.lambdaschool.javasafezoos.models.User
import com.lambdaschool.javasafezoos.models.UserRoles
import com.lambdaschool.javasafezoos.repositories.RoleRepository
import com.lambdaschool.javasafezoos.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityNotFoundException

@Service(value = "userService")
class UserServiceImpl : UserDetailsService, UserService
{
    @Autowired
    private val userrepos: UserRepository? = null

    @Autowired
    private val rolerepos: RoleRepository? = null

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails
    {
        val user = userrepos!!.findByUsername(username)
                ?: throw UsernameNotFoundException("Invalid username or password.")
        return org.springframework.security.core.userdetails.User(user.username, user.getPassword(), user.authority)
    }

    @Throws(EntityNotFoundException::class)
    override fun findUserById(id: Long): User
    {
        return userrepos!!.findById(id).orElseThrow({ EntityNotFoundException(java.lang.Long.toString(id)) })
    }

    override fun findAll(): List<User>
    {
        val list = ArrayList<User>()
        userrepos!!.findAll().iterator().forEachRemaining({ list.add(it) })
        return list
    }

    override fun delete(id: Long)
    {
        if (userrepos?.findById(id)!!.isPresent())
        {
            userrepos.deleteById(id)
        }
        else
        {
            throw EntityNotFoundException(java.lang.Long.toString(id))
        }
    }

    @Transactional
    override fun save(user: User): User
    {
        val newUser = User()
        newUser.username =user.username
        user.getPassword()?.let { newUser.setPasswordNoEncrypt(it) }

        val newRoles = ArrayList<UserRoles>()
        for (ur in user.userRoles)
        {
            ur.role?.let { UserRoles(newUser, it) }?.let { newRoles.add(it) }
        }
        newUser.userRoles =newRoles

        return userrepos!!.save(newUser)
    }

    @Transactional
    override fun update(user: User, id: Long): User
    {
        val authentication = SecurityContextHolder.getContext().getAuthentication()
        val currentUser = userrepos!!.findByUsername(authentication.getName())

        if (id == currentUser.userid)
        {
            if (user.username != null)
            {
                currentUser.username= user.username
            }

            if (user.getPassword() != null)
            {
                currentUser.setPasswordNoEncrypt(user.getPassword()!!)
            }

            if (user.userRoles.size > 0)
            {
                // with so many relationships happening, I decided to go
                // with old school queries
                // delete the old ones
                rolerepos!!.deleteUserRolesByUserId(currentUser.userid)

                // add the new ones
                for (ur in user.userRoles)
                {
                    ur.role?.roleid?.let { rolerepos.insertUserRoles(id, it) }
                }
            }

            return userrepos.save(currentUser)
        }
        else
        {
            throw EntityNotFoundException(java.lang.Long.toString(id) + " Not current user")
        }

    }
}
