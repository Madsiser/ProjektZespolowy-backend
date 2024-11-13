package pl.edu.wat.tai.forum.service

import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import pl.edu.wat.tai.forum.dataClass.PaymentRequest
import java.util.*

@Service
class PaymentService() {
    private val PAYU_TOKEN_URL = "https://secure.snd.payu.com/pl/standard/user/oauth/authorize"
    private val PAYU_CREATE_ORDER_URL = "https://secure.snd.payu.com/api/v2_1/orders"
    private val CLIENT_ID = "300746"
    private val CLIENT_SECRET = "2ee86a66e5d97e3fadc400c9f19b065d"
    private val GRANT_TYPE = "client_credentials"
    fun getAccessToken(): String? {
        val restTemplate = RestTemplate()
        val requestBody =
            String.format("grant_type=%s&client_id=%s&client_secret=%s", GRANT_TYPE, CLIENT_ID, CLIENT_SECRET)
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val requestEntity = HttpEntity(requestBody, headers)
        val responseEntity: ResponseEntity<Map<*, *>> = restTemplate.postForEntity<Map<*, *>>(
            PAYU_TOKEN_URL, requestEntity,
            MutableMap::class.java
        )
        if (responseEntity.statusCode === HttpStatus.OK) {
            val responseBody: Map<*, *>? = responseEntity.body
            return responseBody?.get("access_token") as String?
        } else {
            throw RuntimeException("Failed to obtain access token from PayU")
        }
    }
    fun createOrder(
        userRequest: PaymentRequest
    ): String? {
        val restTemplate = RestTemplate()
        val extOrderId = UUID.randomUUID().toString()
        val orderJson = """
            {
                "continueUrl": "http://localhost:3000/user/thanks",
                "customerIp": "127.0.0.1",
                "merchantPosId": "300746",
                "description": "Donation",
                "currencyCode": "PLN",
                "totalAmount": "${userRequest.amount}",
                "extOrderId":"$extOrderId",
                "buyer": {
                    "email": "forum@example.com",
                    "phone": "654111654",
                    "firstName": "Krystian",
                    "lastName": "Madej",
                    "language": "pl"
                },
                "products": [
                    {
                        "name": "Pakiet Wsparcia",
                        "unitPrice": "${userRequest.amount}",
                        "quantity": "1"
                    }
                ]
            }
        """.trimIndent()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val token = getAccessToken()
        headers["Authorization"] = "Bearer $token"
        val requestEntity = HttpEntity(orderJson, headers)
        val responseEntity: ResponseEntity<Map<*, *>> = restTemplate.postForEntity<Map<*, *>>(
            PAYU_CREATE_ORDER_URL, requestEntity,
            MutableMap::class.java
        )
        if (responseEntity.statusCode === HttpStatus.OK || responseEntity.statusCode === HttpStatus.FOUND) {
            val responseBody: Map<*, *>? = responseEntity.body
            return responseBody?.get("redirectUri") as String?
        } else {
            throw RuntimeException("Failed to create order in PayU")
        }
    }
}