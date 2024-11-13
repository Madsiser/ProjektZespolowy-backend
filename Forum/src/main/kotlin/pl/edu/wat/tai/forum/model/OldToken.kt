package pl.edu.wat.tai.forum.model

import jakarta.persistence.*

@Entity
@Table(name = "oldtokens")
data class OldToken(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @Column(name = "token", length = 1000)
    val token: String = "",

)