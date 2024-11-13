package pl.edu.wat.tai.forum.service

import org.springframework.stereotype.Service
import pl.edu.wat.tai.forum.model.OldToken
import pl.edu.wat.tai.forum.repository.OldTokenRepository

@Service
class LogoutService(
    private val oldTokenRepository: OldTokenRepository
) {
    fun banToken(
        token: String
    ): Boolean {
        val obj = OldToken(token = token)
        oldTokenRepository.save(obj)
        return true
    }
}