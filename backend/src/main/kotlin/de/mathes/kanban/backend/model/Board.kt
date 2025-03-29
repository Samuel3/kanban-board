package de.mathes.kanban.backend.model

import jakarta.persistence.*
@Entity
data class Board(
    @Id val id: String,

    val name: String,

    @OneToMany(cascade = [CascadeType.ALL])
    val columns: List<Column>
)

@Entity
data class Column(

    @Id var id: String?,
    val name: String,

    @OneToMany(cascade = [CascadeType.ALL])
    val tasks: List<Card>
)

@Entity
data class Card(
    @Id val id: String,
    val title: String
)

