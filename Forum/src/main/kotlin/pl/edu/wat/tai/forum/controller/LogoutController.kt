package pl.edu.wat.tai.forum.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.edu.wat.tai.forum.dataClass.Tokens
import pl.edu.wat.tai.forum.service.LogoutService

@RestController
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RequestMapping("/logout")
class LogoutController(
    private val logoutService: LogoutService
) {
    @PostMapping
    fun postTokens(
        @RequestBody tokens: Tokens
    ): ResponseEntity<Map<String, String>> {
        logoutService.banToken(tokens.token)
        logoutService.banToken(tokens.refreshToken)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapOf("message" to "Done"))
    }
}
