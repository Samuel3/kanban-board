package de.mathes.kanban.backend.rest

import de.mathes.kanban.backend.model.Board
import de.mathes.kanban.backend.service.BoardDto
import de.mathes.kanban.backend.service.BoardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("api")
class BoardController {

    @Autowired
    lateinit var boardService: BoardService

    @GetMapping("boards")
    fun getBoards(principal: Principal): List<BoardDto> {
        return boardService.getAllBoards(principal)
    }

    @GetMapping("board/{boardId}")
    fun getBoard(@PathVariable  boardId: String, principal: Principal): Board? {
        return boardService.getBoard(boardId, principal)
    }

    @PostMapping("board/{boardName}")
    fun createBoard(@PathVariable boardName: String, principal: Principal): List<BoardDto> {
        return boardService.createBoard(boardName, principal)
    }

    @PutMapping("board/{boardId}")
    fun updateBoard(@PathVariable boardId: String, @RequestBody board: Board, principal: Principal): Board {
        return boardService.updateBoard(board, principal)
    }

    @DeleteMapping("board/{boardId}")
    fun deleteBoard(@PathVariable boardId: String, principal: Principal) {
        boardService.deleteBoard(boardId, principal)
    }
}