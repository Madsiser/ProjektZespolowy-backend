package pl.edu.wat.tai.forum.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.edu.wat.tai.forum.dataClass.UserLoginComplexJSON
import pl.edu.wat.tai.forum.service.LoginService

@RestController
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RequestMapping("/register")
class RegisterController(
    private val loginService: LoginService
) {
    @PostMapping
    @ResponseBody
    fun registerUser(
        @RequestBody userRequest: UserLoginComplexJSON
    ): ResponseEntity<Map<String, String>> {
        return try {
            ResponseEntity.status(HttpStatus.CREATED).body(loginService.createUser(userRequest))
        }catch (e: Exception){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(mapOf("error" to "Username already exists"))
        }
    }
}
