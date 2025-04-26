package de.mathes.kanban.backend.service

import de.mathes.kanban.backend.configuration.UserService
import de.mathes.kanban.backend.model.Board
import de.mathes.kanban.backend.model.BoardRepository
import de.mathes.kanban.backend.model.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.security.Principal
import java.util.UUID

@Service
class BoardService {

    @Autowired
    lateinit var userRepository: UserRepository

    fun getAllBoards(principal: Principal):List<BoardDto> {
        return userRepository.findByName(principal.name)?.boards?.map { BoardDto(it.id, it.name) } ?: listOf()
    }

    fun getBoard(boardId: String, principal: Principal): Board? {
        return userRepository.findByName(principal.name)?.boards?.find { it.id == boardId }
    }

    fun updateBoard(board: Board, principal: Principal): Board {
        val user = userRepository.findByName(principal.name) ?: throw IllegalArgumentException("User not found")
        val boardIndex = user.boards.indexOfFirst { it.id == board.id }
        if (boardIndex == -1) throw IllegalArgumentException("Board not found")
        user.boards[boardIndex] = board
        userRepository.save(user)
        return board
    }

    fun deleteBoard(boardId: String, principal: Principal) {
        val user = userRepository.findByName(principal.name) ?: throw IllegalArgumentException("User not found")
        user.boards = user.boards.filter { it.id != boardId }.toMutableList()
        userRepository.save(user)
    }

    fun createBoard(boardName: String, principal: Principal): List<BoardDto> {
        val user = userRepository.findByName(principal.name) ?: throw IllegalArgumentException("User not found")
        val newBoard = Board(UUID.randomUUID().toString(), boardName, listOf())
        user.boards.add(newBoard)
        userRepository.save(user)
        return user.boards.map { BoardDto(it.id, it.name) }
    }
}

data class BoardDto(val id: String, val name: String)