package ru.citycheck.core.domain.model.issue

data class Issue(
    val id: Long?,
    val description: String,
    val status: Status,
    val priority: Priority,
    val categoryId: Long,
    val reporterId: Long,
    val assigneeId: Long?,
    val createdAt: Long,
    val updatedAt: Long,
    val issueDocumentId: Long?,
    val actualityStatus: ActualStatus,
    val location: Location,
) {
    enum class Status {
        OPEN,
        IN_PROGRESS,
        RESOLVED,
        CLOSED,
    }

    enum class Priority {
        LOW,
        MEDIUM,
        HIGH,
    }

    enum class ActualStatus {
        ACTUAL,
        NOT_ACTUAL,
        FAKE,
    }
}
