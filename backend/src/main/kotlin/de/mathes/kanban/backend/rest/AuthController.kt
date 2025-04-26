package de.mathes.kanban.backend.rest

import de.mathes.kanban.backend.configuration.UserService
import de.mathes.kanban.backend.model.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = arrayOf("http://localhost:4200"))
@RestController
@RequestMapping("/auth")
class AuthController(val userService: UserService) {


    @PostMapping("/register")
    fun register(@RequestBody userDto: UserDto) : ResponseEntity<Unit>{
        if (userService.findByUsername(userDto.name) == null) {
            val user = User()
            user.name = userDto.name
            user.password = userDto.password
            userService.saveUser(user)
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build()
    }

    @GetMapping("/login")
    fun getCurrentUser(): ResponseEntity<Unit> {
        return ResponseEntity.noContent().build()
    }
}