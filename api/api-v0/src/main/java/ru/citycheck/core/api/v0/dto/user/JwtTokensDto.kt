package ru.citycheck.core.api.v0.dto.user

data class JwtTokensDto(
    val access: String?,
    val refresh: String?,
)
