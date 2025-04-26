package de.mathes.kanban.backend.configuration

import de.mathes.kanban.backend.model.User
import de.mathes.kanban.backend.model.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) : UserDetailsService {

    val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()

    fun saveUser(user: User) : User{
        user.password = this.passwordEncoder.encode(user.password.toString())
        return userRepository.save(user)

    }

    fun findByUsername(username: String): User? {
        return userRepository.findByName(username)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByName(username)
        if (user == null) throw IllegalArgumentException("User not found")
        return org.springframework.security.core.userdetails.User.withUsername(user.name).password(user.password).roles("USER").build()
    }


}