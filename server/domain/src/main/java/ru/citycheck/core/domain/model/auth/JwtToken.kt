package ru.citycheck.core.domain.model.auth

data class JwtToken(
    val token: String,
    val expiration: Long,
    val userId: Long,
    val type: Type,
    val isRevoked: Boolean,
) {
    enum class Type {
        ACCESS,
        REFRESH,
    }
}
