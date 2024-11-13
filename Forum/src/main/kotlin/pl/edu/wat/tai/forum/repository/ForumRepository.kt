package pl.edu.wat.tai.forum.repository

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.edu.wat.tai.forum.model.ForumMessage
import pl.edu.wat.tai.forum.model.ForumTopic

@Repository
interface ForumRepository : JpaRepository<ForumMessage, Long> {
    @Query("SELECT fm FROM ForumMessage fm WHERE fm.topic = :topic ORDER BY fm.date DESC")
    fun findAllByTopicOrderByDateDesc(@Param("topic") topic: String): List<ForumMessage>
    @Query("SELECT fm FROM ForumTopic fm ORDER BY fm.date DESC")
    fun findAllByOrderByDateDesc(): List<ForumTopic>
    fun findAllByTopicAndId(topic: String, id: Long): ForumMessage?
    fun findByTopic(topic: String): ForumMessage?
    fun save(topic: ForumTopic): ForumTopic
    @Modifying
    @Transactional
    @Query("DELETE FROM ForumMessage m WHERE m.topic = :topic AND m.id = :articleId")
    fun deleteMessage(topic: String, articleId: Long)
    @Modifying
    @Transactional
    @Query("DELETE FROM ForumTopic m WHERE m.topic = :topic")
    fun deleteTopic(topic: String)
    fun existsByTopic(topic: String): Boolean
}
