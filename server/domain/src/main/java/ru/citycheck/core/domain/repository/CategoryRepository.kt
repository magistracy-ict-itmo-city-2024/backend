package ru.citycheck.core.domain.repository

import ru.citycheck.core.domain.model.issue.Category

interface CategoryRepository {
    fun createCategory(category: Category): Category
    fun updateCategory(category: Category): Category
    fun deleteCategory(categoryId: Long)

    fun getCategory(categoryId: Long): Category?
    fun getCategories(): List<Category>
}
