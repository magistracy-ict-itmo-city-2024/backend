package ru.citycheck.core.api.v0.dto.issue

data class IssueDto(
    val id: Long? = null,
    val description: String,
    val categoryId: Long,
    val location: LocationDto,
    val status: StatusDto? = null,
    val priority: PriorityDto? = null,
    val reporterId: String? = null,
    val assigneeId: Long? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val documentPath: String? = null,
    val contentType: String? = null,
    val actualityStatus: ActualStatusDto? = null,
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
