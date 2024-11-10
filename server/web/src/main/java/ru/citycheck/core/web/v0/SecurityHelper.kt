package ru.citycheck.core.web.v0

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.citycheck.core.application.service.auth.UserDetailsServiceImpl
import ru.citycheck.core.application.service.auth.models.JwtAuthentication
import ru.citycheck.core.domain.model.auth.User

@Service
class SecurityHelper(
    private val userService: UserDetailsServiceImpl,
) {
    fun getCurrentUser(): User {
        val context = SecurityContextHolder.getContext()
        if (context.authentication is JwtAuthentication && (context.authentication as JwtAuthentication).userId != null && context.authentication.authorities != null) {
            return User(
                (context.authentication as JwtAuthentication).userId,
                context.authentication.name,
                "",
                (context.authentication as JwtAuthentication).authorities,
                false,
            )
        }
        return userService.getUser(context.authentication.name)
    }
}