package ru.citycheck.core.application.service.issue

import org.springframework.stereotype.Service
import ru.citycheck.core.domain.model.issue.IssueDocument
import ru.citycheck.core.domain.repository.impl.IssueDocumentRepository

@Service
class IssueDocumentService(
    private val issueDocumentRepository: IssueDocumentRepository,
) {
    fun createIssueDocument(issueDocument: IssueDocument): IssueDocument {
        return issueDocumentRepository.createIssueDocument(issueDocument)
    }

    fun updateIssueDocument(issueDocument: IssueDocument): IssueDocument {
        return issueDocumentRepository.updateIssueDocument(issueDocument)
    }

    fun deleteIssueDocument(issueDocumentId: Long) {
        issueDocumentRepository.deleteIssueDocument(issueDocumentId)
    }

    fun getIssueDocument(issueDocumentId: Long): IssueDocument? {
        return issueDocumentRepository.getIssueDocument(issueDocumentId)
    }

    fun getIssueDocuments(issueDocumentIds: List<Long>): List<IssueDocument> {
        return issueDocumentRepository.getIssueDocuments(issueDocumentIds)
    }
}