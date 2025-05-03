package de.mathes.kanban.backend.configuration

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
public class CustomAuthenticationProvider(val userService: UserService) : AuthenticationProvider {

    val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val password = authentication.credentials.toString()
        val user = userService.findByUsername(username)
        if (user != null && passwordEncoder.matches(password, user.password)) {
            return UsernamePasswordAuthenticationToken(username, password, listOf(GrantedAuthority { "USER" }))
        } else {
            throw AuthenticationCredentialsNotFoundException("Invalid Credentials for $username")
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }


}