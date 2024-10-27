package ru.citycheck.core.application.service.issue

import org.jobrunr.scheduling.JobScheduler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate
import ru.citycheck.core.application.service.issue.files.FileStorageService
import ru.citycheck.core.domain.model.issue.Issue
import ru.citycheck.core.domain.model.issue.IssueDocument
import ru.citycheck.core.domain.repository.IssueRepository
import java.time.Clock
import java.util.UUID

@Service
class IssueService(
    private val issueRepository: IssueRepository,
    private val issueDocumentService: IssueDocumentService,
    private val clock: Clock,
    private val fileStorageService: FileStorageService,
    private val scheduler: JobScheduler,
    private val mlService: MlService,

    private val transactionTemplate: TransactionTemplate,
) {
    fun createIssue(issue: Issue, contentType: String, fileContent: ByteArray): Issue {
        return transactionTemplate.execute {
            val fileHash = getAttachmentHash()
            val filePath = determineFilePath(fileHash)
            log.debug("Saving file to $filePath")
            fileStorageService.saveFile(fileContent, filePath)

            log.debug("Creating issue document")
            val issueDocument = issueDocumentService.createIssueDocument(
                IssueDocument(
                    id = null,
                    documentPath = filePath,
                    contentType = contentType,
                )
            )

            log.debug("Creating issue for document ${issueDocument.id}")
            return@execute issueRepository.createIssue(
                issue.copy(
                    createdAt = clock.millis(),
                    updatedAt = clock.millis(),
                    issueDocumentId = issueDocument.id,
                ),
            ).also { createdIssue ->
                scheduler.enqueue {
                    setPrediction(createdIssue.id!!)
                }
            }
        }!!
    }

    fun setPrediction(issueId: Long) {
        log.debug("Calculating prediction for issue $issueId")
        val issue = getIssue(issueId) ?: throw IllegalStateException("Issue not found")
        log.debug("Getting prediction for issue $issueId")
        val prediction = mlService.getPrediction(issue)
        log.debug("Prediction for issue $issueId is $prediction")
        issueRepository.updateIssue(
            issue.copy(
                actualityStatus = when (prediction) {
                    in 0.0..0.3 -> Issue.ActualStatus.FAKE
                    in 0.3..0.7 -> Issue.ActualStatus.NOT_ACTUAL
                    else -> Issue.ActualStatus.ACTUAL
                },
            ),
        )
    }

    fun updateIssue(issue: Issue, canChangeFile: Boolean = false): Issue {
        log.debug("Updating issue ${issue.id}")
        val oldIssue = getIssue(issue.id!!) ?: throw IllegalStateException("Issue not found")
        log.debug("Old issue: {}", oldIssue)
        if (!canChangeFile && oldIssue.issueDocumentId != issue.issueDocumentId) {
            throw IllegalStateException("File cannot be changed")
        }

        return issueRepository.updateIssue(issue.copy(updatedAt = clock.millis()))
    }

    fun deleteIssue(issueId: Long) {
        transactionTemplate.execute {
            val issue = getIssue(issueId) ?: throw IllegalStateException("Issue not found")
            issue.issueDocumentId?.let {
                log.debug("Deleting issue document $it")
                issueDocumentService.deleteIssueDocument(it)
            }
            log.debug("Deleting issue $issueId")
            issueRepository.deleteIssue(issueId)
        }
    }

    fun getIssue(issueId: Long): Issue? {
        return issueRepository.getIssue(issueId)
    }

    fun getIssues(userUuid: String? = null): List<Issue> {
        return issueRepository.getIssues(userUuid)
    }

    fun getFile(issueDocument: IssueDocument): ByteArray {
        log.debug("Getting file for issue document ${issueDocument.id}")
        return fileStorageService.getFile(issueDocument.documentPath)
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
