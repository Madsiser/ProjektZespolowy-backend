package pl.edu.wat.tai.forum.model

import jakarta.persistence.*

@Entity
@Table(name = "statute")
data class Statute(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @Column(name = "text", length = 100000)
    val text: String = "",

)