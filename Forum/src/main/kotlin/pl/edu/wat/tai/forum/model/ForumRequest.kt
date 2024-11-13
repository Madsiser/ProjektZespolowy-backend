package pl.edu.wat.tai.forum.model

data class ForumRequest(
    val topic: String,
    val message: String,
    val username: String
)
