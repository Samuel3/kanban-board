package de.mathes.kanban.backend.rest

import de.mathes.kanban.backend.model.Board
import de.mathes.kanban.backend.model.Card
import de.mathes.kanban.backend.model.Column
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class ApiController {

    @GetMapping("boards")
    fun getBoards() = """["board1", "board2", "board3"]"""

    @GetMapping("board/{boardId}")
    fun getBoard(@PathVariable  boardId: String) = Board(boardId, "Board $boardId", listOf(
        Column("4711", "To Do", listOf(
            Card("1", "1st"),
            Card("2", "2nd"),
            Card("3", "3rd")
        )),
        Column("4712", "In Progress", listOf(
            Card("4", "4th"),
            Card("5", "5th")
        )),
        Column("4713", "Done", listOf(
            Card("6", "6th")
        ))
    ))

}