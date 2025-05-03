package de.mathes.kanban.backend.service

import de.mathes.kanban.backend.model.User
import de.mathes.kanban.backend.model.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConfigService {

    @Autowired
    lateinit var userRepository: UserRepository

    fun hasUsers() = userRepository.count() > 0

    fun createUser(username: String, password: String) {
        if (!hasUsers()) {
            val user = User()
            user.name = username
            user.password = password
            userRepository.save(user)
        }
    }
}