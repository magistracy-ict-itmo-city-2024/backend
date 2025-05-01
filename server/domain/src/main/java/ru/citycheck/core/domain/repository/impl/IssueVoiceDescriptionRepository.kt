package ru.citycheck.core.domain.repository.impl

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import ru.citycheck.core.domain.db.Tables.ISSUE_VOICE_DESCRIPTION
import ru.citycheck.core.domain.db.tables.records.IssueVoiceDescriptionRecord
import ru.citycheck.core.domain.model.issue.IssueVoiceDescription
import ru.citycheck.core.domain.repository.IssueVoiceDescriptionRepository

@Repository
class IssueVoiceDescriptionRepository(
    private val dslContext: DSLContext
): IssueVoiceDescriptionRepository {
    override fun createIssueVoiceDescription(issueVoiceDescription: IssueVoiceDescription): IssueVoiceDescription {
        return dslContext
            .insertInto(ISSUE_VOICE_DESCRIPTION)
            .set(issueVoiceDescription.toRecord())
            .returning()
            .fetchOne()!!
            .toModel()
    }

    override fun updateIssueVoiceDescription(issueVoiceDescription: IssueVoiceDescription): IssueVoiceDescription {
        return dslContext
            .update(ISSUE_VOICE_DESCRIPTION)
            .set(issueVoiceDescription.toRecord())
            .where(ISSUE_VOICE_DESCRIPTION.ID.eq(issueVoiceDescription.id))
            .returning()
            .fetchOne()!!
            .toModel()
    }

    override fun deleteIssueVoiceDescription(issueVoiceDescriptionId: Long) {
        dslContext
            .deleteFrom(ISSUE_VOICE_DESCRIPTION)
            .where(ISSUE_VOICE_DESCRIPTION.ID.eq(issueVoiceDescriptionId))
            .execute()
    }

    override fun getIssueVoiceDescriptions(issueVoiceDescriptionIds: List<Long>): List<IssueVoiceDescription> {
        return dslContext
            .selectFrom(ISSUE_VOICE_DESCRIPTION)
            .where(ISSUE_VOICE_DESCRIPTION.ID.`in`(issueVoiceDescriptionIds))
            .fetch()
            .map { it.toModel() }
    }

    companion object {
        private fun IssueVoiceDescription.toRecord() = IssueVoiceDescriptionRecord(
            id,
            documentPath,
            contentType
        ).apply { changed(ISSUE_VOICE_DESCRIPTION.ID, false) }

        private fun IssueVoiceDescriptionRecord.toModel() = IssueVoiceDescription(
            id,
            documentPath,
            contentType
        )
    }
}
