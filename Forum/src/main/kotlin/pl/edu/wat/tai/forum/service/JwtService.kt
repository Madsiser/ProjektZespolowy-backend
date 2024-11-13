package pl.edu.wat.tai.forum.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import pl.edu.wat.tai.forum.model.User
import pl.edu.wat.tai.forum.repository.OldTokenRepository
import java.util.*

@Suppress("DEPRECATION")
@Service
class JwtService(
    @Value("\${security.key}")
    private var secretKey: String,
    private var oldTokenRepository: OldTokenRepository
) {
    fun createJwtToken(
        user: User,
        expirationTime: Long
    ): String {
        val expirationDate = Date(Date().time + expirationTime)
        val claims = Jwts.claims()
        claims["User"] = user
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS256,secretKey)
            .compact()
    }
    fun decodeJwtToken(
        token: String
    ): Claims {
        val cleanToken = token.removePrefix("Bearer ").trim()
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(cleanToken)
            .body
    }
    fun safeDoing(
        authorization: String
    ): Claims {
        if (oldTokenRepository.findByToken(authorization) != null) throw Exception("Token zablokowany")
        return decodeJwtToken(authorization)
    }
}
