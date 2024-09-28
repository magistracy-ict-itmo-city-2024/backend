package ru.citycheck.core.api.v0.dto.issue

data class IssueDto(
    val id: Long?,
    val description: String,
    val status: StatusDto,
    val priority: PriorityDto,
    val categoryId: Long,
    val reporterId: Long,
    val assigneeId: Long,
    val createdAt: Long,
    val updatedAt: Long,
    val documentPath: String?,
    val actualityStatus: ActualStatusDto,
    val location: LocationDto,
) {
    enum class StatusDto {
        OPEN,
        IN_PROGRESS,
        RESOLVED,
        CLOSED,
    }

    enum class PriorityDto {
        LOW,
        MEDIUM,
        HIGH,
    }

    enum class ActualStatusDto {
        ACTUAL,
        NOT_ACTUAL,
        FAKE,
    }
}
