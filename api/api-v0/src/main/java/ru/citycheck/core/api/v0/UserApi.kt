package ru.citycheck.core.api.v0

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.citycheck.core.api.v0.dto.user.*

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

    @PostMapping("/sign_in_phone")
    @Operation(
        summary = "Sign in by phone. Mocked. Used to create user",
        description = "Sign in by phone",
    )
    fun signInPhone(@RequestBody phone: Phone): ResponseEntity<Phone>

    @PostMapping("/sign_in_code")
    @Operation(
        summary = "Sign in by code. Mocked. Used to get JWT tokens by any code",
        description = "Sign in by code",
    )
    fun signInCode(@RequestBody phoneAndCode: PhoneAndCode): ResponseEntity<JwtTokensDto>
}
