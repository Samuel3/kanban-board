package de.mathes.kanban.backend.service

import de.mathes.kanban.backend.model.Board
import de.mathes.kanban.backend.rest.BoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BoardService {

    @Autowired
    lateinit var boardRepository: BoardRepository

    fun getAllBoards():List<BoardDto> = boardRepository.findAll().map { BoardDto(it.id, it.name) }

    fun getBoard(boardId: String): Board? = boardRepository.findById(boardId).orElse(null)

    fun updateBoard(board: Board) = boardRepository.save(board)

    fun deleteBoard(boardId: String) = boardRepository.deleteById(boardId)

    fun createBoard(boardName: String): List<BoardDto> {
        boardRepository.save(Board(UUID.randomUUID().toString(), boardName, listOf()))
        return getAllBoards()
    }
}

data class BoardDto(val id: String, val name: String)