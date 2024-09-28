package ru.citycheck.core.application.service.auth

import ru.citycheck.core.application.service.auth.models.JwtAuthentication
import ru.citycheck.core.application.service.auth.models.JwtTokens
import ru.citycheck.core.domain.model.auth.JwtToken
import ru.citycheck.core.domain.model.auth.User
import ru.citycheck.core.domain.repository.JwtTokensRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate

@Service
class AuthService(
    private val userService: UserDetailsServiceImpl,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokensRepository: JwtTokensRepository,
    private val transactionTemplate: TransactionTemplate,
) {
    fun login(username: String, password: String): JwtTokens {
        val user: User = userService.getUser(username)
        if (passwordEncoder.matches(password, user.password)) {
            val accessToken = jwtProvider.generateAccessToken(user)
            val refreshToken = jwtProvider.generateRefreshToken(user)

            return transactionTemplate.execute {
                saveTokens(accessToken, refreshToken)

                JwtTokens(accessToken.token, refreshToken.token)
            }!!
        } else {
            throw IllegalArgumentException("Wrong password")
        }
    }

    fun refresh(refreshToken: String): JwtTokens {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            val saveRefreshToken = jwtTokensRepository.getToken(refreshToken)
            if (saveRefreshToken != null && saveRefreshToken.type == JwtToken.Type.REFRESH) {
                return transactionTemplate.execute {
                    val claims = jwtProvider.getRefreshClaims(refreshToken)
                    val login = claims.subject
                    val user: User = userService.getUser(login)

                    val accessToken = jwtProvider.generateAccessToken(user)
                    val newRefreshToken = jwtProvider.generateRefreshToken(user)

                    jwtTokensRepository.revokeToken(refreshToken)
                    saveTokens(accessToken, newRefreshToken)

                    JwtTokens(accessToken.token, newRefreshToken.token)
                }!!
            }
        }
        throw IllegalArgumentException("Illegal refresh token")
    }

    fun revokeTokensByUserId(userId: Long) {
        jwtTokensRepository.revokeTokensByUserId(userId)
    }

    private fun saveTokens(accessToken: JwtToken, refreshToken: JwtToken) {
        jwtTokensRepository.saveToken(accessToken)
        jwtTokensRepository.saveToken(refreshToken)
    }

    val authInfo: JwtAuthentication
        get() = SecurityContextHolder.getContext().authentication as JwtAuthentication
}
