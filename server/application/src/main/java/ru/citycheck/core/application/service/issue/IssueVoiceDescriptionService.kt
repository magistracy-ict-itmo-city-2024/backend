package ru.citycheck.core.application.service.issue

import org.springframework.stereotype.Service
import ru.citycheck.core.domain.model.issue.IssueVoiceDescription
import ru.citycheck.core.domain.repository.IssueVoiceDescriptionRepository

@Service
class IssueVoiceDescriptionService(
    private val issueVoiceDescriptionRepository: IssueVoiceDescriptionRepository,
) {
    fun createIssueVoiceDescription(issueVoiceDescription: IssueVoiceDescription): IssueVoiceDescription {
        return issueVoiceDescriptionRepository.createIssueVoiceDescription(issueVoiceDescription)
    }

    fun updateIssueVoiceDescription(issueVoiceDescription: IssueVoiceDescription): IssueVoiceDescription {
        return issueVoiceDescriptionRepository.updateIssueVoiceDescription(issueVoiceDescription)
    }

    fun deleteIssueVoiceDescription(issueVoiceDescriptionId: Long) {
        issueVoiceDescriptionRepository.deleteIssueVoiceDescription(issueVoiceDescriptionId)
    }

    fun getIssueVoiceDescription(issueVoiceDescriptionId: Long): IssueVoiceDescription? {
        return issueVoiceDescriptionRepository.getIssueVoiceDescription(issueVoiceDescriptionId)
    }

    fun getIssueVoiceDescriptions(issueVoiceDescriptionIds: List<Long>): List<IssueVoiceDescription> {
        return issueVoiceDescriptionRepository.getIssueVoiceDescriptions(issueVoiceDescriptionIds)
    }
}