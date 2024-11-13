package pl.edu.wat.tai.forum.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.edu.wat.tai.forum.model.Statute

@Repository
interface StatuteRepository : JpaRepository<Statute, Long> {
    fun getFirstById(id: Long): Statute
}
