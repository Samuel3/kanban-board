package de.mathes.kanban.backend.model

import jakarta.persistence.*
import org.hibernate.annotations.JavaType
import org.hibernate.type.descriptor.java.CharacterArrayJavaType

@Entity
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    var name: String? = null

    var password: String? = null

    @ElementCollection
    @CollectionTable(name = "boards")
    @OneToMany(cascade = [CascadeType.ALL])
    var boards: MutableList<Board> = mutableListOf()
}
