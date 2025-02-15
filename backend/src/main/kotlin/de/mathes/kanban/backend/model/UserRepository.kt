package de.mathes.kanban.backend.model

import de.mathes.kanban.backend.UserEntity
import de.mathes.kanban.backend.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, Long> {

    fun findByName(name: String): User?
}