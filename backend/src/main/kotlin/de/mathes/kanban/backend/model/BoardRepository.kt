package de.mathes.kanban.backend.model

import de.mathes.kanban.backend.model.Board
import org.springframework.data.repository.CrudRepository

interface BoardRepository : CrudRepository<Board, String> {

}