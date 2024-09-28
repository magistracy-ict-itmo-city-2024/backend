package ru.citycheck.core.application.service.issue

import org.springframework.stereotype.Service
import ru.citycheck.core.domain.model.issue.Category
import ru.citycheck.core.domain.repository.CategoryRepository

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
) {
    fun createCategory(category: Category): Category {
        return categoryRepository.createCategory(category)
    }

    fun updateCategory(category: Category): Category {
        return categoryRepository.updateCategory(category)
    }

    fun deleteCategory(categoryId: Long) {
        categoryRepository.deleteCategory(categoryId)
    }

    fun getCategory(categoryId: Long): Category? {
        return categoryRepository.getCategory(categoryId)
    }

    fun getCategories(): List<Category> {
        return categoryRepository.getCategories()
    }
}
