package ru.citycheck.core.web.v0.issue

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.multipart.MultipartFile
import ru.citycheck.core.api.v0.dto.issue.IssueDto
import ru.citycheck.core.api.v0.issue.IssueController
import ru.citycheck.core.application.service.issue.IssueService
import ru.citycheck.core.application.service.issue.IssueDocumentService
import ru.citycheck.core.application.service.issue.MlService
import ru.citycheck.core.web.v0.SecurityHelper
import ru.citycheck.core.web.v0.issue.converter.toDto
import ru.citycheck.core.web.v0.issue.converter.toModel

@Controller
class IssueControllerImpl(
    private val issueService: IssueService,
    private val issueDocumentService: IssueDocumentService,
    private val mlService: MlService,
    private val securityHelper: SecurityHelper,
) : IssueController {
    override fun createIssue(
        file: MultipartFile,
        issue: IssueDto,
    ): ResponseEntity<IssueDto> {
        val reporterId = securityHelper.getCurrentUser().id!!

        val newIssue = issueService.createIssue(issue.toModel(file, reporterId), file.contentType!!, file.bytes)
        val issueDocument = issueDocumentService.getIssueDocument(newIssue.id!!)!!
        return ResponseEntity.ok(newIssue.toDto(issueDocument))
    }

    override fun updateIssue(id: Long, issueDto: IssueDto): ResponseEntity<IssueDto> {
        if (id != issueDto.id) return ResponseEntity.badRequest().build()
        if (issueService.getIssue(id) == null) return ResponseEntity.notFound().build()

        val issue = issueService.updateIssue(issueDto.toModel())
        val issueDocument = issueDocumentService.getIssueDocument(issue.id!!)!!
        return ResponseEntity.ok(issue.toDto(issueDocument))
    }

    override fun deleteIssue(id: Long) {
        issueService.deleteIssue(id)
    }

    override fun getIssue(id: Long): ResponseEntity<IssueDto> {
        val issue = issueService.getIssue(id) ?: return ResponseEntity.notFound().build()
        val issueDocument = issueDocumentService.getIssueDocument(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(issue.toDto(issueDocument))
    }

    override fun getIssues(): ResponseEntity<List<IssueDto>> {
        val issues = issueService.getIssues()
        val issueDocuments = issueDocumentService.getIssueDocuments(issues.map { it.id!! })
            .associateBy { it.id }
        return ResponseEntity.ok(issues.map {
            val issueDocument = issueDocuments[it.id]!!
            it.toDto(issueDocument)
        })
    }

    override fun getMyIssues(statusDto: IssueDto.StatusDto?): ResponseEntity<List<IssueDto>> {
        val reporterId = securityHelper.getCurrentUser().id!!

        val issues = issueService.getIssues(reporterId, statusDto?.toModel())
        val issueDocuments = issueDocumentService.getIssueDocuments(issues.map { it.id!! })
            .associateBy { it.id }
        return ResponseEntity.ok(issues.map {
            val issueDocument = issueDocuments[it.id]!!
            it.toDto(issueDocument)
        })
    }

    override fun downloadFile(id: Long): ResponseEntity<ByteArray> {
        val issue = issueService.getIssue(id) ?: return ResponseEntity.notFound().build()
        val issueDocument = issueDocumentService.getIssueDocument(issue.issueDocumentId!!) ?: return ResponseEntity.notFound().build()

        val file = issueService.getFile(issueDocument)
        return ResponseEntity.ok()
            .header("Content-Type", issueDocument.contentType)
            .body(file)
    }

    override fun triggerPrediction(id: Long): ResponseEntity<String> {
        val issue = issueService.getIssue(id) ?: return ResponseEntity.notFound().build()
        mlService.getPrediction(issue)
        return ResponseEntity.ok("")
    }
}
