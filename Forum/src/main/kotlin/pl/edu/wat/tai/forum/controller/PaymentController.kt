package pl.edu.wat.tai.forum.controller

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import pl.edu.wat.tai.forum.dataClass.PaymentRequest
import pl.edu.wat.tai.forum.service.PaymentService

@RestController
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RequestMapping("/payment")
class PaymentController(
    private val paymentService: PaymentService
) {
    @GetMapping
    fun getStatus(
        @RequestHeader("Authorization") authorization: String
    ): ResponseEntity<String> {
        val topics = paymentService.getAccessToken()
        return ResponseEntity.ok(topics)
    }
    @PostMapping
    fun setPayment(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody userRequest: PaymentRequest
    ): ResponseEntity<Map<String, String>> {
        val topics = paymentService.createOrder(userRequest)
        val responseMap = mapOf("redirectUrl" to topics.orEmpty())
        return ResponseEntity.ok(responseMap)
    }
}
