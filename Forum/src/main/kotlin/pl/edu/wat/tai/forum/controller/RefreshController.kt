package pl.edu.wat.tai.forum.controller

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import pl.edu.wat.tai.forum.dataClass.Tokens
import pl.edu.wat.tai.forum.service.JwtService
import pl.edu.wat.tai.forum.service.RefreshService

@RestController
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RequestMapping("/refresh")
class RefreshController(
    private val refreshService: RefreshService,
    private val jwtService: JwtService
) {
    @PostMapping
    fun refreshToken(
        @RequestBody tokens: Tokens
    ): ResponseEntity<out Any> {
        try {
            jwtService.safeDoing(tokens.refreshToken)
        } catch (e: Exception){
            return ResponseEntity.status(401).body(mapOf("error" to "Token refresh wygasł"))
        }
        val content = refreshService.refreshTokens(tokens.refreshToken)
        return content
    }
}
