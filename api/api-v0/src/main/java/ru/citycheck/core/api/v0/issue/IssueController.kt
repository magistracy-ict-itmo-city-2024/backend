package ru.citycheck.core.api.v0.issue

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.citycheck.core.api.v0.dto.issue.IssueDto

@RequestMapping(
    value = ["/v0/issues"],
    produces = ["application/json"],
)
@RestController
interface IssueController {
    @PostMapping("/")
    fun createIssue(
        @RequestBody issueDto: IssueDto,
    ): ResponseEntity<IssueDto>

    @PostMapping("/{id}")
    fun updateIssue(
        id: Long,
        issueDto: IssueDto,
    ): ResponseEntity<IssueDto>

    fun deleteIssue(
        id: Long,
    )

    fun getIssue(
        id: Long,
    ): ResponseEntity<IssueDto>

    fun getIssues(): ResponseEntity<List<IssueDto>>
}
