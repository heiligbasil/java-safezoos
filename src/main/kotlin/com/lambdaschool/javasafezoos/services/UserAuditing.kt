package com.lambdaschool.javasafezoos.services

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

import java.util.Optional

@Component
class UserAuditing : AuditorAware<String>
{

    override fun getCurrentAuditor(): Optional<String>
    {
        val uname: String
        val authentication = SecurityContextHolder.getContext().getAuthentication()
        if (authentication != null)
        {
            uname = authentication.getName()
        }
        else
        {
            uname = "SYSTEM"
        }
        return Optional.of(uname)
    }

}