package ru.citycheck.core.web.v0.issue

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.multipart.MultipartFile
import ru.citycheck.core.api.v0.dto.issue.IssueDto
import ru.citycheck.core.api.v0.issue.IssueController
import ru.citycheck.core.application.service.issue.IssueService
import ru.citycheck.core.application.service.issue.MlService
import ru.citycheck.core.web.v0.issue.converter.toDto
import ru.citycheck.core.web.v0.issue.converter.toModel

@Controller
class IssueControllerImpl(
    private val issueService: IssueService,
    private val mlService: MlService,
) : IssueController {
    override fun createIssue(
        file: MultipartFile,
        issue: IssueDto,
        reporterId: String,
    ): ResponseEntity<IssueDto> {
        val newIssue = issueService.createIssue(issue.toModel(file, reporterId), file.bytes)
        return ResponseEntity.ok(newIssue.toDto())
    }

    override fun updateIssue(id: Long, issueDto: IssueDto): ResponseEntity<IssueDto> {
        if (id != issueDto.id) return ResponseEntity.badRequest().build()
        if (issueService.getIssue(id) == null) return ResponseEntity.notFound().build()

        val issue = issueService.updateIssue(issueDto.toModel())
        return ResponseEntity.ok(issue.toDto())
    }

    override fun deleteIssue(id: Long) {
        issueService.deleteIssue(id)
    }

    override fun getIssue(id: Long): ResponseEntity<IssueDto> {
        val issue = issueService.getIssue(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(issue.toDto())
    }

    override fun getIssues(): ResponseEntity<List<IssueDto>> {
        val issues = issueService.getIssues()
        return ResponseEntity.ok(issues.map { it.toDto() })
    }

    override fun getMyIssues(reporterId: String): ResponseEntity<List<IssueDto>> {
        return ResponseEntity.ok(issueService.getIssues(reporterId).map { it.toDto() })
    }

    override fun downloadFile(id: Long): ResponseEntity<ByteArray> {
        val issue = issueService.getIssue(id) ?: return ResponseEntity.notFound().build()

        val file = issueService.getFile(id)
        return ResponseEntity.ok()
            .header("Content-Type", issue.contentType)
            .body(file)
    }

    override fun triggerPrediction(id: Long): ResponseEntity<String> {
        val issue = issueService.getIssue(id) ?: return ResponseEntity.notFound().build()
        mlService.getPrediction(issue)
        return ResponseEntity.ok("")
    }
}
