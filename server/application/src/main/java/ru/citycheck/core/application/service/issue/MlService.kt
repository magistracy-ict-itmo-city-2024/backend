package ru.citycheck.core.application.service.issue

import com.fasterxml.jackson.databind.ObjectMapper
import org.asynchttpclient.Dsl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.citycheck.core.domain.model.issue.Issue

@Service
class MlService(
    private val objectMapper: ObjectMapper,

    @Value("\${ml.url:http://localhost:5000/}")
    private val mlUrl: String,
) {
    private val httpClient = Dsl.asyncHttpClient()

    fun getPrediction(issue: Issue): Double {
        val response = httpClient.preparePost(mlUrl)
            .setBody(objectMapper.writeValueAsString(issue))
            .addHeader("Content-Type", "application/json")
            .execute()
            .get()

        log.debug("Response: ${response.responseBody}")

        val predictionResult = objectMapper.readTree(response.responseBodyAsBytes).get("prediction").asDouble()

        log.info("Prediction result: $predictionResult")

        return predictionResult
    }

    companion object {
        private val log = LoggerFactory.getLogger(MlService::class.java)
    }
}
