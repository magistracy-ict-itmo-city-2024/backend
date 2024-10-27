package ru.citycheck.core.domain.repository.impl

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import ru.citycheck.core.domain.db.Tables.ISSUE
import ru.citycheck.core.domain.db.Tables.ISSUE_DOCUMENT
import ru.citycheck.core.domain.db.tables.records.IssueDocumentRecord
import ru.citycheck.core.domain.model.issue.IssueDocument
import ru.citycheck.core.domain.repository.IssueDocumentRepository

@Repository
class IssueDocumentRepository(
    private val dslContext: DSLContext
): IssueDocumentRepository {
    override fun createIssueDocument(issueDocument: IssueDocument): IssueDocument {
        return dslContext
            .insertInto(ISSUE_DOCUMENT)
            .set(issueDocument.toRecord())
            .returning()
            .fetchOne()!!
            .toModel()
    }

    override fun updateIssueDocument(issueDocument: IssueDocument): IssueDocument {
        return dslContext
            .update(ISSUE_DOCUMENT)
            .set(issueDocument.toRecord())
            .where(ISSUE_DOCUMENT.ID.eq(issueDocument.id))
            .returning()
            .fetchOne()!!
            .toModel()
    }

    override fun deleteIssueDocument(issueDocumentId: Long) {
        dslContext
            .deleteFrom(ISSUE_DOCUMENT)
            .where(ISSUE_DOCUMENT.ID.eq(issueDocumentId))
            .execute()
    }

    override fun getIssueDocuments(issueDocumentIds: List<Long>): List<IssueDocument> {
        return dslContext
            .selectFrom(ISSUE_DOCUMENT)
            .where(ISSUE_DOCUMENT.ID.`in`(issueDocumentIds))
            .fetch()
            .map { it.toModel() }
    }

    companion object {
        private fun IssueDocument.toRecord() = IssueDocumentRecord(
            id,
            documentPath,
            contentType
        ).apply { changed(ISSUE.ID, false) }

        private fun IssueDocumentRecord.toModel() = IssueDocument(
            id,
            documentPath,
            contentType
        )
    }
}