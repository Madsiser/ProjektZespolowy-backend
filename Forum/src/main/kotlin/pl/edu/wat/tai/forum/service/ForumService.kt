package pl.edu.wat.tai.forum.service

import io.jsonwebtoken.Claims
import org.springframework.stereotype.Service
import pl.edu.wat.tai.forum.model.ForumMessage
import pl.edu.wat.tai.forum.model.ForumRequest
import pl.edu.wat.tai.forum.model.ForumTopic
import pl.edu.wat.tai.forum.repository.ForumRepository

@Service
class ForumService(
    private val forumRepository: ForumRepository
) {
    fun getAllTopics(
        decoded: Claims
    ): List<ForumTopic> {
        return forumRepository.findAllByOrderByDateDesc()
    }
    fun getTopicMessages(
        decoded: Claims,
        topic: String
    ): List<ForumMessage> {
        return forumRepository.findAllByTopicOrderByDateDesc(topic)
    }
    fun addTopic(
        decoded: Claims,
        forumRequest: ForumRequest
    ) {
        val topicExists = forumRepository.existsByTopic(forumRequest.topic)
        if (!topicExists){
            val topic = ForumTopic(topic = forumRequest.topic, date = System.currentTimeMillis())
            forumRepository.save(topic)
        }
        val firstMessage = ForumMessage(message = forumRequest.message, topic = forumRequest.topic, username = forumRequest.username, date = System.currentTimeMillis())
        forumRepository.save(firstMessage)
    }
    fun addMessage(
        decoded: Claims,
        topic: String,
        forumRequest: ForumRequest
    ) {
        val userData = decoded["User"] as Map<*,*>
        val message = ForumMessage(message = forumRequest.message, date = System.currentTimeMillis(), username = userData["username"] as String, topic = topic)
        forumRepository.save(message)
    }
    fun deleteMessage(
        decoded: Claims,
        topic: String,
        articleId: Long
    ) {
        val message = forumRepository.findAllByTopicAndId(topic, articleId)
        val userData = decoded["User"] as? Map<*, *>
        if (message != null && userData!= null) {
            if ( message.username == userData["username"])
                forumRepository.deleteMessage(topic, articleId)
                val topics = forumRepository.findByTopic(topic)
                if(topics == null){
                    forumRepository.deleteTopic(topic)
                }
        }
    }
}