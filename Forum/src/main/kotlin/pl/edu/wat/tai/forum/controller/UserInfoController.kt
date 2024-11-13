package pl.edu.wat.tai.forum.controller

import io.jsonwebtoken.Claims
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import pl.edu.wat.tai.forum.service.JwtService
import pl.edu.wat.tai.forum.service.LoginService

@RestController
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RequestMapping("/userinfo")
class UserInfoController(
    private val loginService: LoginService,
    private val jwtService: JwtService
) {
    @GetMapping
    fun getInfo(
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<out Any> {
        val decoded: Claims
        try {
            decoded = jwtService.safeDoing(authorization)
        } catch (e: Exception){
            return if (e.message == "Token zablokowany") ResponseEntity.status(401).body(mapOf("error" to "Odrzucono połączenie"))
            else ResponseEntity.status(460).body(mapOf("error" to "Token wygasł"))
        }
        val userData = decoded["User"] as? Map<*, *>
        if (userData != null){
            val content = loginService.findUserInfos(userData.get("username") as String)
            return ResponseEntity.ok().body(content)
        }
        return ResponseEntity.status(404).body(mapOf("error" to "Information not found"))
    }
}
