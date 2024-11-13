package pl.edu.wat.tai.forum.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.edu.wat.tai.forum.dataClass.UserLoginSimpleJSON
import pl.edu.wat.tai.forum.service.LoginService

@RestController
@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RequestMapping("/login")
class LoginController(
    private val loginService: LoginService
) {
    @PostMapping
    @ResponseBody
    fun getUser(
        @RequestBody userRequest: UserLoginSimpleJSON
    ): ResponseEntity<Map<String, String>> {
        return try {
            ResponseEntity.status(HttpStatus.ACCEPTED).body(loginService.findUser(userRequest))
        }catch (e: Exception){
            print("Nieudane logowanie użytkownika")
            println(userRequest)
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

    }
}
