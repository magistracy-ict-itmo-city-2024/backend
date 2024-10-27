package ru.citycheck.core.api.v0.issue

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.headers.Header
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
    @Operation(
        summary = "Create issue",
        description = "Create issue from file and issue dto",
    )
    fun createIssue(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("issue") issue: IssueDto,
        @RequestHeader("X-User-UUID") reporterId: String,
    ): ResponseEntity<IssueDto>

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Operation(
        summary = "Update issue",
        description = "Update issue available only for admin. File data IS IMMUTABLE.",
    )
    fun updateIssue(
        @PathVariable id: Long,
        @RequestBody issueDto: IssueDto,
    ): ResponseEntity<IssueDto>

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete issue",
        description = "Delete issue available only for admin",
    )
    fun deleteIssue(
        @PathVariable id: Long,
    )

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    @Operation(
        summary = "Get all issues",
        description = "Get issues available only for admin",
    )
    fun getIssues(): ResponseEntity<List<IssueDto>>

    @GetMapping("/{id}")
    @Operation(
        summary = "Get issue by id",
        description = "Get issue by id",
    )
    fun getIssue(
        @PathVariable id: Long,
    ): ResponseEntity<IssueDto>

    @GetMapping("/my")
    @Operation(
        summary = "Get my issues",
        description = "Get my issues by X-User-UUID",
    )
    fun getMyIssues(
        @RequestHeader("X-User-UUID") reporterId: String,
    ): ResponseEntity<List<IssueDto>>

    @GetMapping("/{id}/downloadFile")
    @Operation(
        summary = "Download file",
        description = "Download file by issue id",
    )
    fun downloadFile(
        @PathVariable id: Long,
    ): ResponseEntity<ByteArray>

    @GetMapping("/trigger_prediction/{id}")
    @Operation(
        summary = "Trigger prediction",
        description = "Trigger ml prediction by issue id",
    )
    fun triggerPrediction(
        @PathVariable id: Long,
    ): ResponseEntity<String>
}
