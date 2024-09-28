package ru.citycheck.core.api.v0.dto.user

import com.fasterxml.jackson.annotation.JsonProperty

data class UserDto(
    val id: Long? = null,
    val username: String,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) val password: String,
) {
    override fun toString(): String {
        return "UserDto(id=$id, username=$username, password=\"HIDDEN\")"
    }
}
