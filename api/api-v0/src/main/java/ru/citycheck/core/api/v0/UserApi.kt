package ru.citycheck.core.api.v0

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.citycheck.core.api.v0.dto.user.JwtTokensDto
import ru.citycheck.core.api.v0.dto.user.RoleDto
import ru.citycheck.core.api.v0.dto.user.UserDto

@RequestMapping(
    value = ["/v0/users"],
    produces = [APPLICATION_JSON_VALUE],
)
@RestController
interface UserApi {
    @PostMapping("/register")
    fun register(@RequestBody userDto: UserDto): ResponseEntity<Int>

    @PostMapping("/auth")
    fun auth(@RequestBody userDto: UserDto): ResponseEntity<JwtTokensDto>

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshToken: String): ResponseEntity<JwtTokensDto>

    @GetMapping("/me")
    fun me(): ResponseEntity<UserDto>

    @GetMapping("/disable")
    fun disable(): ResponseEntity<String>

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allUsers")
    fun allUsers(): ResponseEntity<List<UserDto>>

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add_role")
    fun addRole(@RequestParam userId: Long, @RequestBody roleDto: RoleDto): ResponseEntity<String>

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/remove_role")
    fun removeRole(@RequestParam userId: Long, @RequestBody roleDto: RoleDto): ResponseEntity<String>
}
