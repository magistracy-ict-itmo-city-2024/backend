package ru.citycheck.core.domain.repository

import ru.citycheck.core.domain.model.issue.Issue
import ru.citycheck.core.domain.model.issue.IssueDocument

interface IssueDocumentRepository {
    fun createIssueDocument(issueDocument: IssueDocument): IssueDocument
    fun updateIssueDocument(issueDocument: IssueDocument): IssueDocument
    fun deleteIssueDocument(issueDocumentId: Long)

    fun getIssueDocument(issueDocumentId: Long): IssueDocument? = getIssueDocuments(listOf(issueDocumentId)).firstOrNull()
    fun getIssueDocuments(issueDocumentIds: List<Long>): List<IssueDocument>
}
