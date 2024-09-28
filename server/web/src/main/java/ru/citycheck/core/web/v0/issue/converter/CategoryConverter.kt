package ru.citycheck.core.web.v0.issue.converter

import ru.citycheck.core.api.v0.dto.issue.CategoryDto
import ru.citycheck.core.domain.model.issue.Category

fun CategoryDto.toModel() = Category(
    id = id,
    name = name,
    description = description,
)

fun Category.toDto() = CategoryDto(
    id = id,
    name = name,
    description = description,
)
