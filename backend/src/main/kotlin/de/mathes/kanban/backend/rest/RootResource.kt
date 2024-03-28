package de.mathes.kanban.backend.rest

import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView


@RestController
class RootResource {

    @GetMapping("/list/**")
    fun redirectWithUsingRedirectPrefix(model: ModelMap): ModelAndView {
        return ModelAndView("forward:/", model)
    }

    @GetMapping("newPath")
    fun root() = "Welcome to Kanban Server"
}