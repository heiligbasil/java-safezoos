package com.lambdaschool.javasafezoos.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity
@Table(name = "users")
class User : Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var userid: Long = 0

    @Column(nullable = false, unique = true)
    var username: String? = null

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private var password: String? = null

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    @JsonIgnoreProperties("user")
    var userRoles: MutableList<UserRoles> = mutableListOf()

    val authority: List<SimpleGrantedAuthority>
        get()
        {
            val rtnList = mutableListOf<SimpleGrantedAuthority>()

            for (r in this.userRoles)
            {
                val myRole = "ROLE_" + r.role?.name?.toUpperCase()
                rtnList.add(SimpleGrantedAuthority(myRole))
            }
            return rtnList
        }

    constructor()

    constructor(username: String, password: String, userRoles: MutableList<UserRoles>)
    {
        this.username = username
        setPassword(password)
        for (ur in userRoles)
        {
            ur.user = this
        }
        this.userRoles = userRoles
    }

    fun getPassword(): String?
    {
        return password
    }

    fun setPassword(password: String)
    {
        val passwordEncoder = BCryptPasswordEncoder()
        this.password = passwordEncoder.encode(password)
    }

    fun setPasswordNoEncrypt(password: String)
    {
        this.password = password
    }
}