package ru.citycheck.core.application.service.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import ru.citycheck.core.domain.model.auth.JwtToken
import ru.citycheck.core.domain.model.auth.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.security.SignatureException
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtProvider(
    private val revokedCache: JwtRevokedCache,
    @Value("\${jwt.secret.access}") jwtAccessSecret: String,
    @Value("\${jwt.secret.refresh}") jwtRefreshSecret: String,
    @Value("\${jwt.access.time:5}") private val jwtAccessTime: Long,
    @Value("\${jwt.access.time.unit:MINUTES}") private val jwtAccessTimeUnit: ChronoUnit,
    @Value("\${jwt.refresh.time:30}") private val jwtRefreshTime: Long,
    @Value("\${jwt.refresh.time.unit:DAYS}") private val jwtRefreshTimeUnit: ChronoUnit,
) {
    private val jwtAccessSecret: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret))
    private val jwtRefreshSecret: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret))

    fun generateAccessToken(user: User): JwtToken {
        val now: LocalDateTime = LocalDateTime.now()
        val accessExpirationInstant: Instant = now.plus(Duration.of(jwtAccessTime, jwtAccessTimeUnit))
            .atZone(ZoneId.systemDefault())
            .toInstant()
        val accessExpiration: Date = Date.from(accessExpirationInstant)
        return JwtToken(
            Jwts.builder()
                .setSubject(user.username)
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("roles", user.roles)
                .compact(),
            accessExpirationInstant.toEpochMilli(),
            user.id!!,
            JwtToken.Type.ACCESS,
            false,
        )
    }

    fun generateRefreshToken(user: User): JwtToken {
        val now: LocalDateTime = LocalDateTime.now()
        val refreshExpirationInstant: Instant = now.plus(Duration.of(jwtRefreshTime, jwtRefreshTimeUnit))
            .atZone(ZoneId.systemDefault())
            .toInstant()
        val refreshExpiration: Date = Date.from(refreshExpirationInstant)
        return JwtToken(
            Jwts.builder()
                .setSubject(user.username)
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact(),
            refreshExpirationInstant.toEpochMilli(),
            user.id!!,
            JwtToken.Type.REFRESH,
            false,
        )
    }

    fun validateAccessToken(accessToken: String): Boolean {
        return validateToken(accessToken, jwtAccessSecret)
    }

    fun validateRefreshToken(refreshToken: String): Boolean {
        return validateToken(refreshToken, jwtRefreshSecret)
    }

    private fun validateToken(token: String, secret: Key): Boolean {
        try {
            if (revokedCache.isRevoked(token)) {
                log.error("Token revoked")
                return false
            }
            Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
            return true
        } catch (expEx: ExpiredJwtException) {
            log.error("Token expired", expEx)
        } catch (unsEx: UnsupportedJwtException) {
            log.error("Unsupported jwt", unsEx)
        } catch (mjEx: MalformedJwtException) {
            log.error("Malformed jwt", mjEx)
        } catch (sEx: SignatureException) {
            log.error("Invalid signature", sEx)
        } catch (e: Exception) {
            log.error("invalid token", e)
        }
        return false
    }

    fun getAccessClaims(token: String): Claims {
        return getClaims(token, jwtAccessSecret)
    }

    fun getRefreshClaims(token: String): Claims {
        return getClaims(token, jwtRefreshSecret)
    }

    private fun getClaims(token: String, secret: Key): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .body
    }

    companion object {
        private val log = LoggerFactory.getLogger(JwtProvider::class.java)
    }
}
