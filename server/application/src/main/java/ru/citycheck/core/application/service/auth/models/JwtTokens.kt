package ru.citycheck.core.application.service.auth.models

data class JwtTokens(
    val accessToken: String?,
    val refreshToken: String?,
)