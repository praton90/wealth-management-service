package com.vanguard.wealthmanagementservice.dto

import org.springframework.http.HttpStatus
import java.time.OffsetDateTime

data class ErrorDTO(
    val timestamp: OffsetDateTime,
    val status: Int,
    val errorCode: String,
    val message: String?
) {
    companion object Factory {
        fun badRequest(errorCode: String, message: String, status: Int = HttpStatus.BAD_REQUEST.value()): ErrorDTO =
            ErrorDTO(
                timestamp = OffsetDateTime.now(),
                status = status,
                errorCode = errorCode,
                message = message
            )

        fun internalServerError(errorCode: String, message: String): ErrorDTO = ErrorDTO(
            timestamp = OffsetDateTime.now(),
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            errorCode = errorCode,
            message = message
        )
    }
}
