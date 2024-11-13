package pl.edu.wat.tai.forum.security

import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.math.BigInteger

@Service
class PasswordEncoder {
    fun hashPassword(password: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val passwordBytes = password.toByteArray()
        val hashBytes = messageDigest.digest(passwordBytes)
        val hashString = BigInteger(1, hashBytes).toString(16).padStart(64, '0')
        return hashString
    }
}
