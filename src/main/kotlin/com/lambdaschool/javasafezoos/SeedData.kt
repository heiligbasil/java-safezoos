package com.lambdaschool.javasafezoos

import com.lambdaschool.javasafezoos.models.Role
import com.lambdaschool.javasafezoos.models.User
import com.lambdaschool.javasafezoos.models.UserRoles
import com.lambdaschool.javasafezoos.repositories.RoleRepository
import com.lambdaschool.javasafezoos.repositories.UserRepository
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
    override fun run(args: Array<String>)
    {
        val r1 = Role("admin")
        val r2 = Role("user")

        val admins = ArrayList<UserRoles>()
        admins.add(UserRoles(User(), r1))
        admins.add(UserRoles(User(), r2))

        val users = ArrayList<UserRoles>()
        users.add(UserRoles(User(), r2))

        rolerepos.save(r1)
        rolerepos.save(r2)
        val u1 = User("barnbarn", "ILuvM4th!", users)
        val u2 = User("admin", "password", admins)

        userrepos.save(u1)
        userrepos.save(u2)
    }
}
