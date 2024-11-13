package pl.edu.wat.tai.forum.service

import org.springframework.stereotype.Service
import pl.edu.wat.tai.forum.dataClass.UserLoginComplexJSON
import pl.edu.wat.tai.forum.dataClass.UserLoginSimpleJSON
import pl.edu.wat.tai.forum.model.User
import pl.edu.wat.tai.forum.repository.LoginRepository
import pl.edu.wat.tai.forum.security.PasswordEncoder

@Service
class LoginService(
    private val loginRepository: LoginRepository,
    private val passService: PasswordEncoder,
    private val jwtService: JwtService
) {
    fun findUser(
        userRequest: UserLoginSimpleJSON
    ): Map<String, String> {
        val passwordHash = passService.hashPassword(userRequest.password + userRequest.username)
        val user = loginRepository.findByUsernameAndPassword(
            userRequest.username,
            passwordHash
        )
        return if (user != null) {
            mapOf("token" to jwtService.createJwtToken(user,6000),
                "refreshToken" to jwtService.createJwtToken(user,86400000))
        } else {
            throw Exception("User not exist")
        }
    }
    fun findUserByUsername(
        username: String
    ): Map<String, String> {
        val user = loginRepository.findByUsername(username)
        return if (user != null) {
            mapOf("token" to jwtService.createJwtToken(user,6000))
        } else {
            emptyMap()
        }
    }
    fun createUser(
        userRequest: UserLoginComplexJSON
    ): Map<String, String> {
        if (loginRepository.existsByUsername(userRequest.username)) {
            throw Exception("Username already exists")
        }
        val passwordHash = passService.hashPassword(userRequest.password + userRequest.username)
        val newUser = User(username = userRequest.username, password = passwordHash, email = userRequest.email, name = userRequest.name, surname = userRequest.surname)
        val savedUser = loginRepository.save(newUser)
        return mapOf("token" to jwtService.createJwtToken(savedUser,6000),
            "refreshToken" to jwtService.createJwtToken(savedUser,86400000))
    }

    fun findUserInfos(
        username: String
    ): Map<String, String> {
        val user = loginRepository.findByUsername(username)
        return if (user != null) {
            mapOf("surname" to user.surname,
                "name" to user.name,
                "username" to user.username,
                "email" to user.email
            )
        } else {
            throw Exception("User not exist")
        }
    }
}