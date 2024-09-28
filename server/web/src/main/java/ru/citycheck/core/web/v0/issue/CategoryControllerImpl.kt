package ru.citycheck.core.web.v0.issue

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import ru.citycheck.core.api.v0.dto.issue.CategoryDto
import ru.citycheck.core.api.v0.issue.CategoryController
import ru.citycheck.core.application.service.issue.CategoryService
import ru.citycheck.core.web.v0.issue.converter.toDto
import ru.citycheck.core.web.v0.issue.converter.toModel

@Controller
class CategoryControllerImpl(
    private val categoryService: CategoryService,
) : CategoryController {
    override fun createCategory(categoryDto: CategoryDto): ResponseEntity<CategoryDto> {
        val category = categoryService.createCategory(categoryDto.toModel())
        return ResponseEntity.ok(category.toDto())
    }

    override fun updateCategory(id: Long, categoryDto: CategoryDto): ResponseEntity<CategoryDto> {
        if (id != categoryDto.id) return ResponseEntity.badRequest().build()
        if (categoryService.getCategory(id) == null) return ResponseEntity.notFound().build()

        val category = categoryService.updateCategory(categoryDto.toModel())
        return ResponseEntity.ok(category.toDto())
    }

    override fun deleteCategory(id: Long) {
        categoryService.deleteCategory(id)
    }

    override fun getCategory(id: Long): ResponseEntity<CategoryDto> {
        val category = categoryService.getCategory(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(category.toDto())
    }

    override fun getCategories(): ResponseEntity<List<CategoryDto>> {
        val categories = categoryService.getCategories()
        return ResponseEntity.ok(categories.map { it.toDto() })
    }
}
