package ru.citycheck.core.api.v0.dto.user

data class RoleDto(
    val role: Role,
) {
    enum class Role {
        ADMIN,
        USER,
    }
}
