package ru.citycheck.core.web.v0.issue.converter

import org.springframework.web.multipart.MultipartFile
import ru.citycheck.core.api.v0.dto.issue.IssueDto
import ru.citycheck.core.api.v0.dto.issue.LocationDto
import ru.citycheck.core.domain.model.issue.Issue
import ru.citycheck.core.domain.model.issue.IssueDocument
import ru.citycheck.core.domain.model.issue.Location

fun IssueDto.toModel(
    file: MultipartFile? = null,
    reporterId: Long? = null,
) = Issue(
    id = id,
    description = description,
    status = status?.toModel() ?: Issue.Status.OPEN,
    priority = priority?.toModel() ?: Issue.Priority.LOW,
    categoryId = categoryId,
    reporterId = this.reporterId ?: reporterId!!,
    assigneeId = assigneeId,
    createdAt = createdAt ?: 0,
    updatedAt = updatedAt ?: 0,
    issueDocumentId = null,
    actualityStatus = actualityStatus?.toModel() ?: Issue.ActualStatus.ACTUAL,
    location = location.toModel(),
)

fun IssueDto.StatusDto.toModel() = Issue.Status.valueOf(name)

fun IssueDto.PriorityDto.toModel() = Issue.Priority.valueOf(name)

fun IssueDto.ActualStatusDto.toModel() = Issue.ActualStatus.valueOf(name)

fun LocationDto.toModel() = Location(
    lat = lat,
    lon = lon,
)

fun Issue.toDto(issueDocument: IssueDocument) = IssueDto(
    id = id,
    description = description,
    status = status.toDto(),
    priority = priority.toDto(),
    categoryId = categoryId,
    reporterId = reporterId,
    assigneeId = assigneeId,
    createdAt = createdAt,
    updatedAt = updatedAt,
    documentPath = issueDocument.documentPath,
    contentType = issueDocument.contentType,
    actualityStatus = actualityStatus.toDto(),
    location = location.toDto(),
)

fun Issue.Status.toDto() = IssueDto.StatusDto.valueOf(name)

fun Issue.Priority.toDto() = IssueDto.PriorityDto.valueOf(name)

fun Issue.ActualStatus.toDto() = IssueDto.ActualStatusDto.valueOf(name)

fun Location.toDto() = LocationDto(
    lat = lat,
    lon = lon,
)
