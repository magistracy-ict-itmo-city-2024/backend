package ru.citycheck.core.web.v0

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import ru.citycheck.core.api.v0.UserApi
import ru.citycheck.core.api.v0.dto.user.JwtTokensDto
import ru.citycheck.core.api.v0.dto.user.RoleDto
import ru.citycheck.core.api.v0.dto.user.UserDto
import ru.citycheck.core.api.v0.dto.user.UserDtoNoPass
import ru.citycheck.core.application.service.auth.AuthService
import ru.citycheck.core.application.service.auth.UserDetailsServiceImpl
import ru.citycheck.core.application.service.auth.models.JwtTokens
import ru.citycheck.core.domain.model.auth.Role
import ru.citycheck.core.domain.model.auth.User

@Controller
class UserApiImpl(
    private val userDetailsServiceImpl: UserDetailsServiceImpl,
    private val authService: AuthService,
) : UserApi {
    override fun register(userDto: UserDto): ResponseEntity<Int> {
        LOG.info("Register $userDto")
        try {
            userDetailsServiceImpl.saveUser(userDto.toModel())
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.CONFLICT)
        }
        return ResponseEntity(HttpStatus.ACCEPTED)
    }

    override fun auth(userDto: UserDto): ResponseEntity<JwtTokensDto> {
        LOG.info("Login $userDto")
        val jwt = authService.login(userDto.username, userDto.password)

        return ResponseEntity(jwt.toDto(), HttpStatus.OK)
    }

    override fun refresh(refreshToken: String): ResponseEntity<JwtTokensDto> {
        val jwt = authService.refresh(refreshToken)

        return ResponseEntity(jwt.toDto(), HttpStatus.OK)
    }

    override fun me(): ResponseEntity<UserDtoNoPass> {
        val username = SecurityContextHolder.getContext().authentication.name
        LOG.info("Username $username")
        return ResponseEntity(userDetailsServiceImpl.getUser(username).toDto(), HttpStatus.OK)
    }

    override fun disable(): ResponseEntity<String> {
        val username = SecurityContextHolder.getContext().authentication.name
        LOG.info("Disable $username")
        userDetailsServiceImpl.disableUser(userDetailsServiceImpl.getUser(username).id!!)
        return ResponseEntity(HttpStatus.OK)
    }

    override fun allUsers(): ResponseEntity<List<UserDtoNoPass>> {
        val username = SecurityContextHolder.getContext().authentication.name
        LOG.info("All users $username")
        return ResponseEntity(userDetailsServiceImpl.getAllUsers().map { it.toDto() }, HttpStatus.OK)
    }

    override fun addRole(userId: Long, roleDto: RoleDto): ResponseEntity<String> {
        val username = SecurityContextHolder.getContext().authentication.name
        LOG.info("Adding role $roleDto to $username")
        userDetailsServiceImpl.addRole(userDetailsServiceImpl.getUser(username).id!!, Role.valueOf(roleDto.role.name))
        return ResponseEntity(HttpStatus.OK)
    }

    override fun removeRole(userId: Long, roleDto: RoleDto): ResponseEntity<String> {
        val username = SecurityContextHolder.getContext().authentication.name
        LOG.info("Removing role $roleDto to $username")
        userDetailsServiceImpl.removeRole(
            userDetailsServiceImpl.getUser(username).id!!,
            Role.valueOf(roleDto.role.name),
        )
        return ResponseEntity(HttpStatus.OK)
    }

    private fun UserDto.toModel(): User = userDetailsServiceImpl.createUserModel(id, username, password)

    companion object {
        private val LOG = LoggerFactory.getLogger(UserApiImpl::class.java)

        private fun User.toDto(): UserDtoNoPass = UserDtoNoPass(
            id,
            username,
        )

        private fun JwtTokens.toDto(): JwtTokensDto = JwtTokensDto(accessToken, refreshToken)
    }
}
