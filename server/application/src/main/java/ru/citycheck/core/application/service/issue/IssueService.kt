package ru.citycheck.core.application.service.issue

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.citycheck.core.application.service.issue.files.FileStorageService
import ru.citycheck.core.domain.model.issue.Issue
import ru.citycheck.core.domain.repository.IssueRepository
import java.security.MessageDigest
import java.time.Clock
import java.util.UUID

@Service
class IssueService(
    private val issueRepository: IssueRepository,
    private val clock: Clock,
    private val fileStorageService: FileStorageService,
) {
    fun createIssue(issue: Issue, fileContent: ByteArray): Issue {
        val fileHash = getAttachmentHash()
        val filePath = determineFilePath(fileHash)
        fileStorageService.saveFile(fileContent, filePath)

        return issueRepository.createIssue(
            issue.copy(
                createdAt = clock.millis(),
                updatedAt = clock.millis(),
                documentPath = filePath,
            ),
        )
    }

    fun updateIssue(issue: Issue, canChangeFile: Boolean = false): Issue {
        val oldIssue = getIssue(issue.id!!) ?: throw IllegalStateException("Issue not found")
        if (!canChangeFile && (issue.documentPath != oldIssue.documentPath || issue.contentType != oldIssue.contentType)) {
            throw IllegalStateException("File cannot be changed")
        }

        return issueRepository.updateIssue(issue.copy(updatedAt = clock.millis()))
    }

    fun deleteIssue(issueId: Long) {
        issueRepository.deleteIssue(issueId)
    }

    fun getIssue(issueId: Long): Issue? {
        return issueRepository.getIssue(issueId)
    }

    fun getIssues(userUuid: String? = null): List<Issue> {
        return issueRepository.getIssues(userUuid)
    }

    fun getFile(issueId: Long): ByteArray {
        val issue = getIssue(issueId) ?: throw IllegalStateException("Issue not found")
        return fileStorageService.getFile(issue.documentPath ?: throw IllegalStateException("File not found"))
    }

    private fun getAttachmentHash(): String {
        return UUID.randomUUID().toString()
    }

    private fun determineFilePath(fileHash: String): String {
        return "attachments/$fileHash"
    }

    companion object {
        private val log = LoggerFactory.getLogger(IssueService::class.java)
    }
}
