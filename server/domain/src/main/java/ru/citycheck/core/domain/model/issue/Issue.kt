package ru.citycheck.core.domain.model.issue

data class Issue(
    val id: Long?,
    val description: String,
    val status: Status,
    val priority: Priority,
    val categoryId: Long,
    val reporterId: String,
    val assigneeId: Long?,
    val createdAt: Long,
    val updatedAt: Long,
    val documentPath: String?,
    val contentType: String?,
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
