package com.lambdaschool.javasafezoos

import com.lambdaschool.authenticatedusers.model.Quote
import com.lambdaschool.authenticatedusers.model.Role
import com.lambdaschool.authenticatedusers.model.User
import com.lambdaschool.authenticatedusers.model.UserRoles
import com.lambdaschool.authenticatedusers.repository.RoleRepository
import com.lambdaschool.authenticatedusers.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

import java.util.ArrayList

@Transactional
@Component
class SeedData(internal var rolerepos: RoleRepository, internal var userrepos: UserRepository) : CommandLineRunner
{

    @Override
    @Throws(Exception::class)
    fun run(args: Array<String>)
    {
        val r1 = Role("admin")
        val r2 = Role("user")

        val admins = ArrayList()
        admins.add(UserRoles(User(), r1))
        admins.add(UserRoles(User(), r2))

        val users = ArrayList()
        users.add(UserRoles(User(), r2))

        rolerepos.save(r1)
        rolerepos.save(r2)
        val u1 = User("barnbarn", "ILuvM4th!", users)

        val u2 = User("admin", "password", admins)

        userrepos.save(u1)
        userrepos.save(u2)
    }
}
