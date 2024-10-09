package ru.citycheck.core.web.v0.issue.converter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import ru.citycheck.core.api.v0.dto.issue.IssueDto

@Component
class StringToIssueConverter : Converter<String, IssueDto> {
    override fun convert(source: String): IssueDto {
        return objectMapper.readValue(source, IssueDto::class.java)
    }

    companion object {
        private val objectMapper = ObjectMapper().registerModule(KotlinModule())
    }
}