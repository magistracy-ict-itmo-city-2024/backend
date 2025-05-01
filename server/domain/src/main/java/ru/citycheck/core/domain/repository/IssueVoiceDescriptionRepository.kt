package ru.citycheck.core.domain.repository

import ru.citycheck.core.domain.model.issue.IssueVoiceDescription

interface IssueVoiceDescriptionRepository {
    fun createIssueVoiceDescription(issueVoiceDescription: IssueVoiceDescription): IssueVoiceDescription
    fun updateIssueVoiceDescription(issueVoiceDescription: IssueVoiceDescription): IssueVoiceDescription
    fun deleteIssueVoiceDescription(issueVoiceDescriptionId: Long)

    fun getIssueVoiceDescription(issueVoiceDescriptionId: Long): IssueVoiceDescription? = getIssueVoiceDescriptions(listOf(issueVoiceDescriptionId)).firstOrNull()
    fun getIssueVoiceDescriptions(issueVoiceDescriptionIds: List<Long>): List<IssueVoiceDescription>
}