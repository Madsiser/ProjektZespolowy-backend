package pl.edu.wat.tai.forum.dataClass

data class UserLoginSimpleJSON(
    val username: String,
    val password: String
)
data class UserLoginComplexJSON(
    val username: String,
    val password: String,
    val email: String,
    val name: String,
    val surname: String
)
data class PaymentRequest(
    val amount: Int
)
data class Tokens(
    val token: String,
    val refreshToken: String
)