package ru.citycheck.core.application.service.issue

import org.springframework.stereotype.Service
import ru.citycheck.core.domain.model.issue.Issue
import ru.citycheck.core.domain.repository.IssueRepository

@Service
class IssueService(
    private val issueRepository: IssueRepository,
) {
    fun createIssue(issue: Issue): Issue {
        return issueRepository.createIssue(issue)
    }

    fun updateIssue(issue: Issue): Issue {
        return issueRepository.updateIssue(issue)
    }

    fun deleteIssue(issueId: Long) {
        issueRepository.deleteIssue(issueId)
    }

    fun getIssue(issueId: Long): Issue? {
        return issueRepository.getIssue(issueId)
    }

    fun getIssues(): List<Issue> {
        return issueRepository.getIssues()
    }
}
