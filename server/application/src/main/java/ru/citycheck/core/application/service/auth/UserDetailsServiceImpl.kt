package ru.citycheck.core.application.service.auth

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.citycheck.core.domain.model.auth.Role
import ru.citycheck.core.domain.model.auth.User
import ru.citycheck.core.domain.repository.UserRepository

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
        return org.springframework.security.core.userdetails.User(user.username, user.password, emptyList())
    }

    fun getUser(username: String): User {
        return userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
    }

    fun saveUser(user: User) {
        userRepository.saveUser(user)
    }

    fun updateUser(userId: Long, user: User) {
        userRepository.updateUser(userId, user)
    }

    fun getAllUsers(): List<User> {
        return userRepository.getAllUsers()
    }

    fun disableUser(userId: Long) {
        userRepository.disableUser(userId)
    }

    fun addRole(userId: Long, role: Role) {
        userRepository.addRole(userId, role)
    }

    fun removeRole(userId: Long, role: Role) {
        userRepository.removeRole(userId, role)
    }

    fun createUserModel(
        id: Long?,
        username: String,
        password: String,
    ): User = User(
        id,
        username,
        passwordEncoder.encode(password),
        setOf(Role.USER),
        false,
    )
}
