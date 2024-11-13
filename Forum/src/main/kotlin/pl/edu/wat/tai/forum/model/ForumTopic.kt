package pl.edu.wat.tai.forum.model

import jakarta.persistence.*

@Entity
@Table(name = "forum_topics")
data class ForumTopic(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @Column(name = "topic")
    val topic: String = "",
    @Column(name = "date")
    val date: Long = 0
)
