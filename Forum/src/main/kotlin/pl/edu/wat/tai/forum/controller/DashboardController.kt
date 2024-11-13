package pl.edu.wat.tai.forum.controller

import io.jsonwebtoken.Claims
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import pl.edu.wat.tai.forum.service.DashboardService
import pl.edu.wat.tai.forum.service.JwtService

@RestController
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RequestMapping("/dashboard")
class DashboardController(
    private val dashboardService: DashboardService,
    private val jwtService: JwtService
) {
    @GetMapping
    fun getAllTopics(
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<out Any> {
        val decoded: Claims
        try {
            decoded = jwtService.safeDoing(authorization)
        } catch (e: Exception){
            if (e.message == "Token zablokowany") return ResponseEntity.status(401).body(mapOf("error" to "Odrzucono połączenie"))
            else return ResponseEntity.status(460).body(mapOf("error" to "Token wygasł"))
        }
        val content = dashboardService.getAll(decoded)
        return ResponseEntity.ok(content)
    }
}
