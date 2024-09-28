package ru.citycheck.core.domain.model.auth

data class User(
    val id: Long?,
    val username: String,
    val password: String,
    val roles: Set<Role>,
    val isDisabled: Boolean = false,
) {
    override fun toString(): String {
        return "UserDto(id=$id, username=$username, password=\"HIDDEN\", roles=$roles)"
    }
}
