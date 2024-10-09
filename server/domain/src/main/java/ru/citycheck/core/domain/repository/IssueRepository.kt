package ru.citycheck.core.domain.repository

import ru.citycheck.core.domain.model.issue.Issue

interface IssueRepository {
    fun createIssue(issue: Issue): Issue
    fun updateIssue(issue: Issue): Issue
    fun deleteIssue(issueId: Long)

    fun getIssue(issueId: Long): Issue?
    fun getIssues(userUuid: String? = null): List<Issue>
}
