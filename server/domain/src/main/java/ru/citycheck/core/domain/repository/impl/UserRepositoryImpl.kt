package ru.citycheck.core.domain.repository.impl

import ru.citycheck.core.domain.db.Tables.USERS
import ru.citycheck.core.domain.db.tables.UserRoles.USER_ROLES
import ru.citycheck.core.domain.db.tables.records.UsersRecord
import ru.citycheck.core.domain.model.auth.Role
import ru.citycheck.core.domain.model.auth.User
import ru.citycheck.core.domain.repository.UserRepository
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.support.TransactionTemplate

@Repository
class UserRepositoryImpl(
    private val dsl: DSLContext,
    private val transactionTemplate: TransactionTemplate,
) : UserRepository {
    override fun get(id: Long, forUpdate: Boolean): User? {
        val user = dsl.selectFrom(USERS)
            .where(USERS.ID.eq(id))
            .let {
                if (forUpdate) {
                    it.forUpdate()
                } else {
                    it
                }
            }
            .fetchOne()?.toModel()

        return user?.copy(roles = getUserRoles(id))
    }

    override fun findByUsername(username: String): User? {
        val user = dsl.selectFrom(USERS)
            .where(USERS.USERNAME.eq(username))
            .and(USERS.IS_DISABLED.isFalse)
            .fetchOne()?.toModel()

        return user?.copy(roles = getUserRoles(user.id!!))
    }

    override fun saveUser(user: User) {
        dsl.insertInto(USERS)
            .set(user.toRecord().apply { changed(USERS.ID, false) })
            .execute()
    }

    override fun updateUser(id: Long, user: User) {
        transactionTemplate.execute {
            val userOld = get(id, forUpdate = true)

            validateUpdate(user, userOld)

            dsl.update(USERS)
                .set(user.copy(id = id).toRecord())
                .where(USERS.ID.eq(id))
        }
    }

    override fun getAllUsers(): List<User> {
        return dsl.selectFrom(USERS)
            .where(USERS.IS_DISABLED.isFalse)
            .fetch()
            .map { it.toModel().copy(roles = getUserRoles(it.id)) }
    }

    override fun disableUser(id: Long) {
        dsl.update(USERS)
            .set(USERS.IS_DISABLED, true)
            .where(USERS.ID.eq(id))
            .execute()
    }

    override fun addRole(userId: Long, role: Role) {
        dsl.insertInto(USER_ROLES)
            .set(USER_ROLES.USER_ID, userId)
            .set(USER_ROLES.ROLE, role.name)
            .onConflictDoNothing()
            .execute()
    }

    override fun removeRole(userId: Long, role: Role) {
        dsl.deleteFrom(USER_ROLES)
            .where(USER_ROLES.USER_ID.eq(userId))
            .and(USER_ROLES.ROLE.eq(role.name))
            .execute()
    }

    private fun getUserRoles(userId: Long): Set<Role> {
        return dsl.select(USER_ROLES.ROLE)
            .from(USER_ROLES)
            .where(USER_ROLES.USER_ID.eq(userId))
            .fetchSet(USER_ROLES.ROLE)
            .mapNotNull {
                try{
                    Role.valueOf(it)
                } catch (e: IllegalArgumentException) {
                    null
                }
            }
            .toSet()
    }

    private fun validateUpdate(user: User, userOld: User?) {
        if (userOld == null) throw IllegalStateException("No such user")

        requireNotNull(user.username == userOld.username)
        requireNotNull(user.id == userOld.id)
    }

    companion object {
        private fun User.toRecord(): UsersRecord = UsersRecord(
            id,
            username,
            password,
            isDisabled,
        )

        private fun UsersRecord.toModel(): User = User(
            id,
            username,
            password,
            emptySet(),
            isDisabled,
        )
    }
}
