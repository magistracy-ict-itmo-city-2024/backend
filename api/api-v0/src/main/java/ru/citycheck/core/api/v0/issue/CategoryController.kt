package ru.citycheck.core.api.v0.issue

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.citycheck.core.api.v0.dto.issue.CategoryDto

@RequestMapping(
    value = ["/api/v0/categories"],
    produces = ["application/json"],
)
@RestController
interface CategoryController {
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    fun createCategory(
        @RequestBody categoryDto: CategoryDto,
    ): ResponseEntity<CategoryDto>

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}")
    fun updateCategory(
        @PathVariable id: Long,
        @RequestBody categoryDto: CategoryDto,
    ): ResponseEntity<CategoryDto>

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteCategory(
        @PathVariable id: Long,
    )

    @GetMapping("/{id}")
    fun getCategory(
        @PathVariable id: Long,
    ): ResponseEntity<CategoryDto>

    @GetMapping("/")
    fun getCategories(): ResponseEntity<List<CategoryDto>>
}
