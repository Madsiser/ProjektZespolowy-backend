package pl.edu.wat.tai.forum.model

import jakarta.persistence.*
@Entity
@Table(name = "forum_messages")
data class ForumMessage(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @Column(name = "topic")
    val topic: String = "",
    @Column(name = "message", length = 1000)
    val message: String = "",
    @Column(name = "date")
    val date: Long = 0,
    @Column(name = "username")
    val username: String = ""
)