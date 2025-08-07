package com.nataliatsi.certificatesdataupload.api.core.exception;

import com.nataliatsi.certificatesdataupload.api.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnsupportedFormatException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnsupportedFormat(UnsupportedFormatException ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidFormat(InvalidFormatException ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));

        return buildResponseEntity(message, HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getReason(), (HttpStatus) ex.getStatusCode(), request.getRequestURI());
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponseDTO> handleMultipartException(MultipartException ex, HttpServletRequest request) {
        return buildResponseEntity("File upload error: " + ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex, HttpServletRequest request) {
        return buildResponseEntity("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI());
    }

    private ResponseEntity<ErrorResponseDTO> buildResponseEntity(String message, HttpStatus status, String path) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );
        return ResponseEntity.status(status).body(error);
    }
}
