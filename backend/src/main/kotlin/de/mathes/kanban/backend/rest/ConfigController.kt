package de.mathes.kanban.backend.rest

import de.mathes.kanban.backend.service.ConfigService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
class ConfigController {

    @Autowired
    lateinit var configService: ConfigService

    @GetMapping("config")
    fun getConfig() = ConfigDto(configService.hasUsers())
}

data class ConfigDto(
    val hasUsers: Boolean
)

data class UserDto(
    val name: String,
    val password: String
)