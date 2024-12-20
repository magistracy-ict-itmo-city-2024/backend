package ru.citycheck.core.domain.repository.impl

import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository
import ru.citycheck.core.domain.db.Tables.ISSUE
import ru.citycheck.core.domain.db.tables.records.IssueRecord
import ru.citycheck.core.domain.model.issue.Issue
import ru.citycheck.core.domain.model.issue.Location
import ru.citycheck.core.domain.repository.IssueRepository

@Repository
class IssueRepositoryImpl(
    private val dslContext: DSLContext,
) : IssueRepository {
    override fun createIssue(issue: Issue): Issue {
        return dslContext
            .insertInto(ISSUE)
            .set(issue.toRecord())
            .returning()
            .fetchOne()!!
            .toModel()
    }

    override fun updateIssue(issue: Issue): Issue {
        return dslContext
            .update(ISSUE)
            .set(issue.toRecord())
            .where(ISSUE.ID.eq(issue.id))
            .returning()
            .fetchOne()!!
            .toModel()
    }

    override fun deleteIssue(issueId: Long) {
        dslContext
            .deleteFrom(ISSUE)
            .where(ISSUE.ID.eq(issueId))
            .execute()
    }

    override fun getIssue(issueId: Long): Issue? {
        return dslContext
            .selectFrom(ISSUE)
            .where(ISSUE.ID.eq(issueId))
            .fetchOne()
            ?.toModel()
    }

    override fun getIssues(userUuid: Long?, status: Issue.Status?): List<Issue> {
        var condition: Condition = DSL.trueCondition()

        if (userUuid != null) {
            condition = condition.and(ISSUE.REPORTER_ID.eq(userUuid))
        }

        if (status != null) {
            condition = condition.and(ISSUE.STATUS.eq(status.name))
        }

        return dslContext
            .selectFrom(ISSUE)
            .where(condition)
            .fetch()
            .map { it.toModel() }
    }

    companion object {
        private fun Issue.toRecord() = IssueRecord(
            id,
            description,
            status.name,
            priority.name,
            categoryId,
            assigneeId,
            createdAt,
            updatedAt,
            actualityStatus.name,
            location.lat,
            location.lon,
            issueDocumentId,
            reporterId,
        ).apply { changed(ISSUE.ID, false) }

        private fun IssueRecord.toModel() = Issue(
            id,
            description,
            Issue.Status.valueOf(status),
            Issue.Priority.valueOf(priority),
            categoryId,
            reporterId,
            assigneeId,
            createdAt,
            updatedAt,
            documentId,
            Issue.ActualStatus.valueOf(actualityStatus),
            Location(
                locationLat,
                locationLon,
            ),
        )
    }
}
