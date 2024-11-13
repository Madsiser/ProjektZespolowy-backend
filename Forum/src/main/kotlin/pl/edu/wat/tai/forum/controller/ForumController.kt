package pl.edu.wat.tai.forum.controller

import io.jsonwebtoken.Claims
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import pl.edu.wat.tai.forum.model.ForumMessage
import pl.edu.wat.tai.forum.model.ForumRequest
import pl.edu.wat.tai.forum.service.ForumService
import pl.edu.wat.tai.forum.service.JwtService

@RestController
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RequestMapping("/forum")
class ForumController(
    private val forumService: ForumService,
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
            return ResponseEntity.status(460).body(mapOf("error" to "Token wygasł"))
        }
        val topics = forumService.getAllTopics(decoded)
        return ResponseEntity.ok(topics)
    }
    @GetMapping("/{topic}")
    fun getTopicMessages(
        @RequestHeader("Authorization") authorization: String,
        @PathVariable topic: String
    ): ResponseEntity<Any> {
        val decoded: Claims
        try {
            decoded = jwtService.safeDoing(authorization)
        } catch (e: Exception){
            return ResponseEntity.status(460).body(mapOf("error" to "Token wygasł"))
        }
        val messages = forumService.getTopicMessages(decoded, topic)
        if(messages == emptyList<ForumMessage>()) return ResponseEntity.status(404).body(messages)
        return ResponseEntity.ok(messages)
    }
    @PostMapping
    fun addTopic(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody forumRequest: ForumRequest
    ): ResponseEntity<Map<String, String>> {
        val decoded: Claims
        try {
            decoded = jwtService.safeDoing(authorization)
        } catch (e: Exception){
            if (e.message == "Token zablokowany") return ResponseEntity.status(401).body(mapOf("error" to "Odrzucono połączenie"))
            else return ResponseEntity.status(460).body(mapOf("error" to "Token wygasł"))
        }
        val permissionClaim = decoded["permission"]
        if (permissionClaim is Int && permissionClaim < 0) {
            return ResponseEntity.status(403).build()
        }
        try {
            forumService.addTopic(decoded, forumRequest)
            return ResponseEntity.status(201).body(mapOf("message" to "Temat dodany"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(mapOf("error" to "Błąd z bazą danych"))
        }
    }
    @PostMapping("/{topic}")
    fun addMessage(
        @RequestHeader("Authorization") authorization: String,
        @PathVariable topic: String,
        @RequestBody forumRequest: ForumRequest
    ): ResponseEntity<Map<String, String>> {
        val decoded: Claims
        try {
            decoded = jwtService.safeDoing(authorization)
        } catch (e: Exception){
            return if (e.message == "Token zablokowany") ResponseEntity.status(401).body(mapOf("error" to "Odrzucono połączenie"))
            else ResponseEntity.status(460).body(mapOf("error" to "Token wygasł"))
        }
        val permissionClaim = decoded["permission"]
        if (permissionClaim is Int && permissionClaim < 0) {
            return ResponseEntity.status(403).build()
        }
        try {
            forumService.addMessage(decoded, topic, forumRequest)
            return ResponseEntity.status(201).body(mapOf("message" to "Wpis dodany"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(mapOf("error" to "Błąd z bazą danych"))
        }
    }
    @DeleteMapping("/{topic}/{articleId}")
    fun deleteMessage(
        @RequestHeader("Authorization") authorization: String,
        @PathVariable topic: String,
        @PathVariable articleId: Long
    ): ResponseEntity<Map<String, String>> {
        val decoded: Claims
        try {
            decoded = jwtService.safeDoing(authorization)
        } catch (e: Exception){
            if (e.message == "Token zablokowany") return ResponseEntity.status(401).body(mapOf("error" to "Odrzucono połączenie"))
            else return ResponseEntity.status(460).body(mapOf("error" to "Token wygasł"))
        }
        val permissionClaim = decoded["permission"]
        if (permissionClaim is Int && permissionClaim < 0){
            return ResponseEntity.status(403).build()
        }
        try {
            forumService.deleteMessage(decoded, topic, articleId)
            return ResponseEntity.ok(mapOf("message" to "Wpis usunięty"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(mapOf("error" to "Błąd z bazą danych"))
        }
    }
}
