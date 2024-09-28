package ru.citycheck.core.domain.repository.impl

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import ru.citycheck.core.domain.db.Tables.CATEGORY
import ru.citycheck.core.domain.db.tables.records.CategoryRecord
import ru.citycheck.core.domain.model.issue.Category
import ru.citycheck.core.domain.repository.CategoryRepository

@Repository
class CategoryRepositoryImpl(
    private val dslContext: DSLContext,
) : CategoryRepository {
    override fun createCategory(category: Category): Category {
        return dslContext
            .insertInto(CATEGORY)
            .set(category.toRecord())
            .returning()
            .fetchOne()!!
            .toModel()
    }

    override fun updateCategory(category: Category): Category {
        return dslContext
            .update(CATEGORY)
            .set(category.toRecord())
            .where(CATEGORY.ID.eq(category.id))
            .returning()
            .fetchOne()!!
            .toModel()
    }

    override fun deleteCategory(categoryId: Long) {
        dslContext
            .deleteFrom(CATEGORY)
            .where(CATEGORY.ID.eq(categoryId))
            .execute()
    }

    override fun getCategory(categoryId: Long): Category? {
        return dslContext
            .selectFrom(CATEGORY)
            .where(CATEGORY.ID.eq(categoryId))
            .fetchOne()
            ?.toModel()
    }

    override fun getCategories(): List<Category> {
        return dslContext
            .selectFrom(CATEGORY)
            .fetch()
            .map { it.toModel() }
    }

    companion object {
        private fun Category.toRecord() = CategoryRecord(
            id,
            name,
            description,
        ).apply { changed(CATEGORY.ID, false) }

        private fun CategoryRecord.toModel() = Category(
            id,
            name,
            description,
        )
    }
}
