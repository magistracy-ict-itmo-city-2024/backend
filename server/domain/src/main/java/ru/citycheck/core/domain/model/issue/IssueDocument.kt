package ru.citycheck.core.domain.model.issue

data class IssueDocument(
    val id: Long?,
    val documentPath: String,
    val contentType: String,
)