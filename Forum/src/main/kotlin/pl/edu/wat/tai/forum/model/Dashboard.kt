package pl.edu.wat.tai.forum.model

import jakarta.persistence.*
@Entity
@Table(name = "dashboard")
data class Dashboard(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @Column(name = "title", length = 1000)
    val title: String = "",
    @Column(name = "text", length = 10000)
    val text: String = "",
    @Column(name = "date")
    val date: String = ""
)