package com.lambdaschool.javasafezoos.models


import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import javax.persistence.*
import java.io.Serializable
import java.util.Objects

@Entity
@Table(name = "userroles")
class UserRoles : Auditable, Serializable
{
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("userRoles", "hibernateLazyInitializer")
    @JoinColumn(name = "userid")
    var user: User? = null

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleid")
    @JsonIgnoreProperties("userRoles", "hibernateLazyInitializer")
    var role: Role? = null

    constructor()
    {
    }

    constructor(user: User, role: Role)
    {
        this.user = user
        this.role = role
    }

    override fun equals(o: Any?): Boolean
    {
        if (this === o)
        {
            return true
        }
        if (o !is UserRoles)
        {
            return false
        }
        val userRoles = o as UserRoles?
        return user == userRoles!!.user && role == userRoles.role
    }

    override fun hashCode(): Int
    {
        return Objects.hash(user, role)
    }
}
