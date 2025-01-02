package de.mathes.kanban.backend.rest

import de.mathes.kanban.backend.model.Board
import de.mathes.kanban.backend.service.BoardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
class ApiController {

    @Autowired
    lateinit var boardService: BoardService

    @GetMapping("boards")
    fun getBoards() = boardService.getAllBoards()

    @GetMapping("board/{boardId}")
    fun getBoard(@PathVariable  boardId: String): Board? {
        return boardService.getBoard(boardId)
    }

    @PutMapping("board/{boardId}")
    fun updateBoard(@PathVariable boardId: String, @RequestBody board: Board): Board {
        return boardService.updateBoard(board)
    }

    @DeleteMapping("board/{boardId}")
    fun deleteBoard(@PathVariable boardId: String) {
        boardService.deleteBoard(boardId)
    }

}