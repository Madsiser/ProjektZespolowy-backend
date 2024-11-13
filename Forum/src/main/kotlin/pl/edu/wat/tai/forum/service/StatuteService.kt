package pl.edu.wat.tai.forum.service

import org.springframework.stereotype.Service
import pl.edu.wat.tai.forum.repository.StatuteRepository

@Service
class StatuteService(
    private val statuteRepository: StatuteRepository
) {
    fun getStatuteInJSON(): Map<String,String> {
        val map = mapOf("statute" to statuteRepository.getFirstById(0).text)
        return map
    }
}
