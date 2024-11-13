package pl.edu.wat.tai.forum.controller

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import pl.edu.wat.tai.forum.service.StatuteService

@RestController
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RequestMapping("/statute")
class StatuteController(
    private val statuteService: StatuteService
) {
    @GetMapping
    fun getStatute(): ResponseEntity<out Any> {
        return ResponseEntity.ok().body(statuteService.getStatuteInJSON())
    }
}
