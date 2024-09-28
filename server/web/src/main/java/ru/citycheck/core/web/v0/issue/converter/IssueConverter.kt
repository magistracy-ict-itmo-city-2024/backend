package ru.citycheck.core.web.v0.issue.converter

import ru.citycheck.core.api.v0.dto.issue.IssueDto
import ru.citycheck.core.api.v0.dto.issue.LocationDto
import ru.citycheck.core.domain.model.issue.Issue
import ru.citycheck.core.domain.model.issue.Location

fun IssueDto.toModel() = Issue(
    id = id,
    description = description,
    status = status.toModel(),
    priority = priority.toModel(),
    categoryId = categoryId,
    reporterId = reporterId,
    assigneeId = assigneeId,
    createdAt = createdAt,
    updatedAt = updatedAt,
    documentPath = documentPath,
    actualityStatus = actualityStatus.toModel(),
    location = location.toModel(),
)

fun IssueDto.StatusDto.toModel() = Issue.Status.valueOf(name)

fun IssueDto.PriorityDto.toModel() = Issue.Priority.valueOf(name)

fun IssueDto.ActualStatusDto.toModel() = Issue.ActualStatus.valueOf(name)

fun LocationDto.toModel() = Location(
    lat = lat,
    lon = lon,
)

fun Issue.toDto() = IssueDto(
    id = id,
    description = description,
    status = status.toDto(),
    priority = priority.toDto(),
    categoryId = categoryId,
    reporterId = reporterId,
    assigneeId = assigneeId,
    createdAt = createdAt,
    updatedAt = updatedAt,
    documentPath = documentPath,
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
