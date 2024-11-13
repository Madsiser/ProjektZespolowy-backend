package pl.edu.wat.tai.forum.model

import jakarta.persistence.*
@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    @Column
    var username: String = "",
    @Column
    var password: String = "",
    @Column
    var name: String = "",
    @Column
    var surname: String = "",
    @Column
    var permissions: Long = 0,
    @Column
    var email: String = "",
)