package ru.citycheck.core.application.service.issue

import org.jobrunr.scheduling.JobScheduler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate
import ru.citycheck.core.application.model.FileData
import ru.citycheck.core.application.service.issue.files.FileStorageService
import ru.citycheck.core.domain.model.issue.Issue
import ru.citycheck.core.domain.model.issue.IssueDocument
import ru.citycheck.core.domain.model.issue.IssueVoiceDescription
import ru.citycheck.core.domain.repository.IssueRepository
import java.time.Clock
import java.util.*

@Service
class IssueService(
    private val issueRepository: IssueRepository,
    private val issueDocumentService: IssueDocumentService,
    private val issueVoiceDescriptionService: IssueVoiceDescriptionService,
    private val clock: Clock,
    private val fileStorageService: FileStorageService,
    private val scheduler: JobScheduler,
    private val mlService: MlService,

    private val transactionTemplate: TransactionTemplate,
) {
    fun createIssue(issue: Issue, issueDocumentData: FileData, voiceDescriptionData: FileData? = null): Issue {
        return transactionTemplate.execute {
            // Process files and create document records
            val issueDocument = processIssueDocument(issueDocumentData)
            val voiceDescription = processVoiceDescription(issue, voiceDescriptionData)

            // Create the issue record
            val createdIssue = createIssueRecord(issue, issueDocument, voiceDescription)

            // Schedule background jobs
            scheduleBackgroundJobs(createdIssue)

            createdIssue
        }!!
    }

    private fun processIssueDocument(issueDocumentData: FileData): IssueDocument {
        val fileHash = getAttachmentHash()
        val filePath = determineFilePath(fileHash)
        log.debug("Saving issue document file to $filePath")
        fileStorageService.saveFile(issueDocumentData.data, filePath)

        log.debug("Creating issue document record")
        return issueDocumentService.createIssueDocument(
            IssueDocument(
                id = null,
                documentPath = filePath,
                contentType = issueDocumentData.contentType,
            )
        )
    }

    private fun processVoiceDescription(issue: Issue, voiceDescriptionData: FileData?): IssueVoiceDescription? {
        return processVoiceDescriptionIfNeeded(issue, voiceDescriptionData)
            ?.let { filePath ->
                log.debug("Creating voice description record")
                issueVoiceDescriptionService.createIssueVoiceDescription(
                    IssueVoiceDescription(
                        id = null,
                        documentPath = filePath,
                        contentType = voiceDescriptionData!!.contentType,
                    )
                )
            }
    }

    private fun createIssueRecord(
        issue: Issue, 
        issueDocument: IssueDocument, 
        voiceDescription: IssueVoiceDescription?
    ): Issue {
        val currentTime = clock.millis()
        log.debug("Creating issue for document ${issueDocument.id}")
        return issueRepository.createIssue(
            issue.copy(
                createdAt = currentTime,
                updatedAt = currentTime,
                issueDocumentId = issueDocument.id,
                voiceDescriptionId = voiceDescription?.id,
            )
        )
    }

    private fun scheduleBackgroundJobs(createdIssue: Issue) {
        // Schedule prediction job
        scheduler.enqueue {
            setPrediction(createdIssue.id!!)
        }

        // Schedule voice-to-text job if needed
        if (createdIssue.isDescriptionByVoice) {
            scheduler.enqueue { 
                setDescriptionByAudio(createdIssue.id!!) 
            }
        }
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

    fun setDescriptionByAudio(issueId: Long) {
        log.debug("Setting description by audio for issue $issueId")
        val issue = getIssue(issueId) ?: throw IllegalStateException("Issue not found")
        log.debug("Issue before: {}", issue)
        val text = mlService.processSpeachToText(issue)
        log.debug("Text: {}", text)
        issueRepository.updateIssue(issue.copy(description = text))
        log.debug("Issue after: {}", getIssue(issueId))
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
            issue.voiceDescriptionId?.let {
                log.debug("Deleting issue voice description $it")
                issueVoiceDescriptionService.deleteIssueVoiceDescription(it)
            }
            log.debug("Deleting issue $issueId")
            issueRepository.deleteIssue(issueId)
        }
    }

    fun getIssue(issueId: Long): Issue? {
        return issueRepository.getIssue(issueId)
    }

    fun getIssues(userId: Long? = null, status: Issue.Status? = null): List<Issue> {
        return issueRepository.getIssues(userId, status)
    }

    fun getFile(issueDocument: IssueDocument): ByteArray {
        log.debug("Getting file for issue document ${issueDocument.id}")
        return fileStorageService.getFile(issueDocument.documentPath)
    }

    fun getVoiceDescriptionFile(issueVoiceDescription: IssueVoiceDescription): ByteArray {
        log.debug("Getting file for issue voice description ${issueVoiceDescription.id}")
        return fileStorageService.getFile(issueVoiceDescription.documentPath)
    }

    private fun getAttachmentHash(): String {
        return UUID.randomUUID().toString()
    }

    private fun determineFilePath(fileHash: String): String {
        return "attachments/$fileHash"
    }

    private fun processVoiceDescriptionIfNeeded(issue: Issue, voiceDescriptionData: FileData?): String? {
        if (issue.isDescriptionByVoice) {
            if (voiceDescriptionData == null) throw IllegalStateException("Voice description file is required if issue is description by voice")
            log.debug("Saving voice description file")
            val fileHash = getAttachmentHash()
            val filePath = determineFilePath(fileHash)
            fileStorageService.saveFile(voiceDescriptionData.data, filePath)
            return filePath
        }
        return null
    }

    companion object {
        private val log = LoggerFactory.getLogger(IssueService::class.java)
    }
}
