package de.mathes.kanban.backend.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RootResource {

    @GetMapping
    fun root() = "Welcome to Kanban Server"
}