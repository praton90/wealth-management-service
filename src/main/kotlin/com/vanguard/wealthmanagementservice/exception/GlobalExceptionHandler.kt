package com.vanguard.wealthmanagementservice.exception

import com.vanguard.wealthmanagementservice.dto.ErrorDTO
import feign.FeignException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class GlobalExceptionHandler {

    companion object {
        val log: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(CsvParseException::class, WealthPortfolioBalanceException::class)
    fun handleMissingRequestHeaderException(
        request: HttpServletRequest,
        ex: MissingRequestHeaderException
    ): ResponseEntity<ErrorDTO> {
        log.warn("${request.requestURI} - ${ex.message}")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorDTO.internalServerError(errorCode = "error.internal", message = ex.message))
    }

    @ExceptionHandler(
        HttpMessageNotReadableException::class,
        MethodArgumentTypeMismatchException::class,
        MethodArgumentNotValidException::class,
        MissingServletRequestParameterException::class,
        BindException::class,
    )
    fun handleBadRequestException(request: HttpServletRequest, ex: Exception): ResponseEntity<ErrorDTO> {
        log.warn("${request.requestURI} - ${ex.message}")
        return ResponseEntity.badRequest()
            .body(ErrorDTO.badRequest(errorCode = "error.bad.request", message = ex.message ?: ""))
    }

    @ExceptionHandler(FeignException::class)
    fun handleFeignException(request: HttpServletRequest, ex: FeignException): ResponseEntity<ErrorDTO> {
        if (HttpStatus.valueOf(ex.status()).is4xxClientError) {
            log.warn("${request.requestURI} - ${ex.message}")
            return ResponseEntity.status(ex.status())
                .body(
                    ErrorDTO.badRequest(
                        status = ex.status(),
                        errorCode = "error.client.bad.request",
                        message = ex.message ?: ""
                    )
                )
        }
        log.error("${request.requestURI} - ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorDTO.internalServerError(errorCode = "error.unknown", message = ex.message ?: ""))
    }

    @ExceptionHandler(Exception::class)
    fun handleUnknownException(request: HttpServletRequest, ex: Exception): ResponseEntity<ErrorDTO> {
        log.error("${request.requestURI} - ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorDTO.internalServerError(errorCode = "error.unhandled", message = ex.message ?: ""))
    }

}
