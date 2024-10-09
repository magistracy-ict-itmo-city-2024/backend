package ru.citycheck.core.api.v0

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.citycheck.core.api.v0.dto.user.JwtTokensDto
import ru.citycheck.core.api.v0.dto.user.RoleDto
import ru.citycheck.core.api.v0.dto.user.UserDto
import ru.citycheck.core.api.v0.dto.user.UserDtoNoPass

@RequestMapping(
    value = ["/api/v0/users"],
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
    fun me(): ResponseEntity<UserDtoNoPass>

    @GetMapping("/disable")
    fun disable(): ResponseEntity<String>

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/allUsers")
    fun allUsers(): ResponseEntity<List<UserDtoNoPass>>

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add_role")
    fun addRole(@RequestParam userId: Long, @RequestBody roleDto: RoleDto): ResponseEntity<String>

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/remove_role")
    fun removeRole(@RequestParam userId: Long, @RequestBody roleDto: RoleDto): ResponseEntity<String>
}
