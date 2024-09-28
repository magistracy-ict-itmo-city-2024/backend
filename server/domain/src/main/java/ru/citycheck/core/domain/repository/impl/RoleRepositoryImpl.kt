package ru.citycheck.core.domain.repository.impl

import ru.citycheck.core.domain.db.tables.UserRoles
import ru.citycheck.core.domain.db.tables.records.UserRolesRecord
import ru.citycheck.core.domain.model.auth.Role
import ru.citycheck.core.domain.repository.RoleRepository
import org.jooq.DSLContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class RoleRepositoryImpl(
    private val dsl: DSLContext,
) : RoleRepository {
    override fun getRoles(userId: Long): Set<Role> {
        return dsl.select(UserRoles.USER_ROLES.ROLE)
            .from(UserRoles.USER_ROLES)
            .where(UserRoles.USER_ROLES.USER_ID.eq(userId))
            .mapNotNull {
                try {
                    Role.valueOf(it.value1())
                } catch (e: IllegalArgumentException) {
                    LOG.warn("Unknown role for user $userId ${it.value1()}")
                    null
                }
            }
            .toSet()
    }

    override fun addRoles(userId: Long, roles: Set<Role>): Int = dsl.insertInto(UserRoles.USER_ROLES)
        .values(
            roles.map { UserRolesRecord(userId, it.toString()) },
        )
        .onConflictDoNothing()
        .execute()

    override fun removeRoles(userId: Long, roles: Set<Role>): Int = dsl.delete(UserRoles.USER_ROLES)
        .where(UserRoles.USER_ROLES.USER_ID.eq(userId))
        .and(UserRoles.USER_ROLES.ROLE.`in`(roles.map { it.toString() }))
        .execute()

    companion object {
        val LOG = LoggerFactory.getLogger(RoleRepositoryImpl::class.java)
    }
}
