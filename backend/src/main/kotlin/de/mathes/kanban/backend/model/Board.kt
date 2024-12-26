package de.mathes.kanban.backend.model

data class Board(val id: String, val name: String, val columns: List<Column>)

data class Column(val id: String, val name: String, val tasks: List<Card>)

data class Card(val id: String, val title: String)
