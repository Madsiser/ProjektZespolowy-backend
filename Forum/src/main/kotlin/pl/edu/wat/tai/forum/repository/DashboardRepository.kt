package pl.edu.wat.tai.forum.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.edu.wat.tai.forum.model.Dashboard

@Repository
interface DashboardRepository : JpaRepository<Dashboard, Long> {
    fun findAllByOrderByDateDesc(): List<Dashboard>
}
