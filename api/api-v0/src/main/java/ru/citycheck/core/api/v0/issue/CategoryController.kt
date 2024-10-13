package ru.citycheck.core.api.v0.issue

import io.swagger.v3.oas.annotations.Operation
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
    @Operation(
        summary = "Create category",
        description = "Create category available only for admin",
    )
    fun createCategory(
        @RequestBody categoryDto: CategoryDto,
    ): ResponseEntity<CategoryDto>

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}")
    @Operation(
        summary = "Update category",
        description = "Update category available only for admin",
    )
    fun updateCategory(
        @PathVariable id: Long,
        @RequestBody categoryDto: CategoryDto,
    ): ResponseEntity<CategoryDto>

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete category",
        description = "Delete category available only for admin",
    )
    fun deleteCategory(
        @PathVariable id: Long,
    )

    @GetMapping("/{id}")
    @Operation(
        summary = "Get category by id",
        description = "Get category by id",
    )
    fun getCategory(
        @PathVariable id: Long,
    ): ResponseEntity<CategoryDto>

    @GetMapping("/")
    @Operation(
        summary = "Get all categories",
        description = "Get all categories",
    )
    fun getCategories(): ResponseEntity<List<CategoryDto>>
}
