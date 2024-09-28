package ru.citycheck.core.application.service.auth

import ru.citycheck.core.domain.model.auth.JwtToken
import ru.citycheck.core.domain.repository.JwtTokensRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

@Service
class JwtRevokedCache(
    private val jwtTokensRepository: JwtTokensRepository,
) {
    private val atomicJwtRevokedTokens: AtomicReference<Set<String>> = AtomicReference()
    private val refresher = Executors.newScheduledThreadPool(1)

    init {
        refresh()
        refresher.scheduleAtFixedRate(::refresh, 0, 30, TimeUnit.SECONDS)
    }

    fun isRevoked(token: String): Boolean {
        return atomicJwtRevokedTokens.get().contains(token)
    }

    fun allRevoked(): Set<String> {
        return refresh()
    }

    private fun refresh(): Set<String> {
        return try {
            val revokedTokens = jwtTokensRepository.getRevokedTokens()
                .map { it.token }
                .toSet()
            atomicJwtRevokedTokens.set(revokedTokens)
            revokedTokens
        } catch (e: Exception) {
            LOG.error("Error refreshing revoked tokens", e)
            emptySet()
        }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(JwtRevokedCache::class.java)
    }
}
