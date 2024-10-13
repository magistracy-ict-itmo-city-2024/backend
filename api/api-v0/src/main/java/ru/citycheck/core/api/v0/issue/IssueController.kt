package ru.citycheck.core.api.v0.issue

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.citycheck.core.api.v0.dto.issue.IssueDto

@RequestMapping(
    value = ["/api/v0/issues"],
    produces = ["application/json"],
)
@RestController
interface IssueController {
    @PostMapping("/")
    fun createIssue(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("issue") issue: IssueDto,
        @RequestHeader("X-User-UUID") reporterId: String,
    ): ResponseEntity<IssueDto>

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    fun updateIssue(
        @PathVariable id: Long,
        @RequestBody issueDto: IssueDto,
    ): ResponseEntity<IssueDto>

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteIssue(
        @PathVariable id: Long,
    )

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    fun getIssues(): ResponseEntity<List<IssueDto>>

    @GetMapping("/{id}")
    fun getIssue(
        @PathVariable id: Long,
    ): ResponseEntity<IssueDto>

    @GetMapping("/my")
    fun getMyIssues(
        @RequestHeader("X-User-UUID") reporterId: String,
    ): ResponseEntity<List<IssueDto>>

    @GetMapping("/{id}/downloadFile")
    fun downloadFile(
        @PathVariable id: Long,
    ): ResponseEntity<ByteArray>

    @GetMapping("/trigger_prediction/{id}")
    fun triggerPrediction(
        @PathVariable id: Long,
    ): ResponseEntity<String>
}
