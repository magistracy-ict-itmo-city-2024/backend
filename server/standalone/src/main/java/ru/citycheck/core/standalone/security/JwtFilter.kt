package ru.citycheck.core.standalone.security

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Claims
import ru.citycheck.core.application.service.auth.JwtProvider
import ru.citycheck.core.application.service.auth.models.JwtAuthentication
import ru.citycheck.core.domain.model.auth.Role
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class JwtFilter(
    private val jwtProvider: JwtProvider,
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse?, fc: FilterChain) {
        val token = getTokenFromRequest(request as HttpServletRequest)
        if (token != null && jwtProvider.validateAccessToken(token)) {
            val claims: Claims = jwtProvider.getAccessClaims(token)
            val jwtInfoToken: JwtAuthentication = claims.toAuthentication()
            jwtInfoToken.isAuthenticated = true
            SecurityContextHolder.getContext().authentication = jwtInfoToken
        }
        fc.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearer = request.getHeader(AUTHORIZATION)
        return if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            bearer.substring(7)
        } else null
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"

        private val json = ObjectMapper()

        private fun Claims.toAuthentication(): JwtAuthentication = JwtAuthentication(
            subject,
            json.readValue(get("roles").toString(),  object : TypeReference<Set<Role>>() { }),
            null,
            null,
            subject,
            true
        )
    }
}

