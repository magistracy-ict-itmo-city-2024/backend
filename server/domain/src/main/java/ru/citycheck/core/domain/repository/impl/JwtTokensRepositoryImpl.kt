package ru.citycheck.core.domain.repository.impl

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import ru.citycheck.core.domain.db.tables.JwtTokens
import ru.citycheck.core.domain.db.tables.records.JwtTokensRecord
import ru.citycheck.core.domain.model.auth.JwtToken
import ru.citycheck.core.domain.repository.JwtTokensRepository

@Repository
class JwtTokensRepositoryImpl(
    private val dsl: DSLContext,
) : JwtTokensRepository {
    override fun saveToken(token: JwtToken) {
        dsl.insertInto(JwtTokens.JWT_TOKENS)
            .set(token.toRecord())
            .execute()
    }

    override fun getToken(tokenString: String): JwtToken? {
        return dsl.selectFrom(JwtTokens.JWT_TOKENS)
            .where(JwtTokens.JWT_TOKENS.JWT_TOKEN.eq(tokenString))
            .and(JwtTokens.JWT_TOKENS.REVOKED.isFalse)
            .fetchOne()?.toModel()
    }

    override fun getRevokedTokens(): Collection<JwtToken> {
        return dsl.selectFrom(JwtTokens.JWT_TOKENS)
            .where(JwtTokens.JWT_TOKENS.REVOKED.isTrue)
            .map { it.toModel() }
    }

    override fun revokeToken(tokenString: String) {
        dsl.update(JwtTokens.JWT_TOKENS)
            .set(JwtTokens.JWT_TOKENS.REVOKED, true)
            .where(JwtTokens.JWT_TOKENS.JWT_TOKEN.eq(tokenString))
            .execute()
    }

    override fun revokeTokensByUserId(userId: Long) {
        dsl.update(JwtTokens.JWT_TOKENS)
            .set(JwtTokens.JWT_TOKENS.REVOKED, true)
            .where(JwtTokens.JWT_TOKENS.USER_ID.eq(userId))
            .execute()
    }

    override fun deleteExpiredTokens() {
        dsl.deleteFrom(JwtTokens.JWT_TOKENS)
            .where(JwtTokens.JWT_TOKENS.EXPIRATION_TS.lt(System.currentTimeMillis()))
            .execute()
    }

    companion object {
        private fun JwtToken.toRecord(): JwtTokensRecord = JwtTokensRecord(
            token,
            expiration,
            userId,
            type.toString(),
            isRevoked,
        )

        private fun JwtTokensRecord.toModel(): JwtToken = JwtToken(
            jwtToken,
            expirationTs,
            userId,
            JwtToken.Type.valueOf(type),
            revoked,
        )
    }
}
