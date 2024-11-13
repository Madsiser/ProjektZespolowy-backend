package pl.edu.wat.tai.forum.repository

import pl.edu.wat.tai.forum.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface LoginRepository : JpaRepository<User, Long> {
    fun findByUsernameAndPassword(username: String, password: String): User?
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): User?
}