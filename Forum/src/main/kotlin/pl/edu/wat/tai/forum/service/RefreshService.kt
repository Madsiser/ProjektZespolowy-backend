package pl.edu.wat.tai.forum.service

import io.jsonwebtoken.Claims
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import pl.edu.wat.tai.forum.repository.OldTokenRepository

@Service
class RefreshService(
    private val jwtService: JwtService,
    private val loginService: LoginService
) {
    fun refreshTokens(
        refreshToken: String
    ): ResponseEntity<out Map<String, String?>> {
        val decoded: Claims
        try {
            decoded = jwtService.safeDoing(refreshToken)
        } catch (e: Exception){
            return if (e.message == "Token zablokowany") ResponseEntity.status(401).body(mapOf("error" to "Odrzucono połączenie"))
            else ResponseEntity.status(460).body(mapOf("error" to "Token wygasł"))
        }
        val userData = decoded["User"] as? Map<*, *>
        var result: Map<String,String> = emptyMap()
        if (userData != null){
            result = loginService.findUserByUsername(userData.get("username") as String)
        }
        if (result.isEmpty()){
            return ResponseEntity.status(500).body(mapOf("error" to "Błąd tworzenia tokenu"))
        }
            return ResponseEntity.status(200).body(mapOf("token" to result["token"], "refreshToken" to refreshToken))
    }
}
