package ru.citycheck.core.web.v0

import io.jsonwebtoken.JwtException
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Service
class ExceptionHandler: ResponseEntityExceptionHandler() {
    data class ErrorResponse(
        val message: String,
        val status: Int,
    )

    @ExceptionHandler(JwtException::class)
    fun handleJwtException(e: JwtException): ErrorResponse {
        return ErrorResponse(e.message ?: "Unknown error with JWT", 401)
    }
}