package pl.edu.wat.tai.forum.service

import io.jsonwebtoken.Claims
import org.springframework.stereotype.Service
import pl.edu.wat.tai.forum.model.Dashboard
import pl.edu.wat.tai.forum.repository.DashboardRepository

@Service
class DashboardService(
    private val dashboardRepository: DashboardRepository
) {
    fun getAll(decoded: Claims): List<Dashboard> {
        return dashboardRepository.findAllByOrderByDateDesc()
    }
}
