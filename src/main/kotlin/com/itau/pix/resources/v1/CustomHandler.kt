package com.itau.pix.resources.v1

import com.itau.pix.domain.exceptions.PixKeyAlreadyInactivatedException
import com.itau.pix.domain.exceptions.PixKeyNotFoundException
import com.itau.pix.resources.v1.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomHandler {

    @ExceptionHandler(PixKeyAlreadyInactivatedException::class)
    fun handlePixKeyAlreadyInactivatedException(exception: PixKeyAlreadyInactivatedException):
            ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(
                ErrorResponse.Builder()
                    .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .error(HttpStatus.UNPROCESSABLE_ENTITY.name)
                    .message(PIX_KEY_MESSAGE_ALREADY_INACTIVATED)
                    .build()
            )
    }

    @ExceptionHandler(PixKeyNotFoundException::class)
    fun handlePixKeyNotFoundException(exception: PixKeyNotFoundException):
            ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponse.Builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .error(HttpStatus.NOT_FOUND.name)
                    .message(PIX_KEY_MESSAGE_NOT_FOUND)
                    .build()
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception):
            ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                exception.message?.let {
                    ErrorResponse.Builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.name)
                        .message(it)
                        .build()
                }
            )
    }

    companion object {
        private const val PIX_KEY_MESSAGE_ALREADY_INACTIVATED: String = "your pix key is already inactive"
        private const val PIX_KEY_MESSAGE_NOT_FOUND: String = "your pix key not found"
        private const val MESSAGE_AN_UNEXPECTED_ERROR_OCCURRED: String = "an unexpected error occurred"
    }
}