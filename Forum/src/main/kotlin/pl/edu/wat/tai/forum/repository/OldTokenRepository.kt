package pl.edu.wat.tai.forum.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.edu.wat.tai.forum.model.OldToken

@Repository
interface OldTokenRepository : JpaRepository<OldToken, Long> {
    fun findByToken(token: String): OldToken?
}