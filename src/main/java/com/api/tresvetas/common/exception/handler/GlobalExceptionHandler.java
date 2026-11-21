package com.api.tresvetas.common.exception.handler;

import com.api.tresvetas.common.dto.ApiResponse;
import com.api.tresvetas.common.exception.base.ConflictException;
import com.api.tresvetas.common.exception.base.NotFoundException;
import com.api.tresvetas.common.factory.ApiResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(
            NotFoundException ex,
            HttpServletRequest request
    ) {
        return ApiResponseFactory.build(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                null,
                request
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<Void>> handleConflict(
            ConflictException ex,
            HttpServletRequest request
    ) {
        return ApiResponseFactory.build(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                null,
                request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.putIfAbsent(
                        error.getField(),
                        error.getDefaultMessage()
                )
        );

        return ApiResponseFactory.build(
                HttpStatus.BAD_REQUEST,
                "Error de validación de los datos de entrada.",
                errors,
                request
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        return ApiResponseFactory.build(
                HttpStatus.BAD_REQUEST,
                "El cuerpo de la solicitud no es válido. Comprueba el formato JSON y los tipos de datos.",
                null,
                request
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request
    ) {
        ResponseEntity<ApiResponse<Void>> response = ApiResponseFactory.build(
                HttpStatus.METHOD_NOT_ALLOWED,
                "El método HTTP utilizado no está permitido para esta ruta.",
                null,
                request
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .headers(ex.getHeaders())
                .body(response.getBody());
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request
    ) {
        return ApiResponseFactory.build(
                HttpStatus.FORBIDDEN,
                "No tienes permisos para realizar esta operación.",
                null,
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        if (ex instanceof ErrorResponse errorResponse) {
            ResponseEntity<ApiResponse<Void>> response = ApiResponseFactory.build(
                    errorResponse.getStatusCode(),
                    "No se ha podido procesar la solicitud HTTP.",
                    null,
                    request
            );

            return ResponseEntity.status(errorResponse.getStatusCode())
                    .headers(errorResponse.getHeaders())
                    .body(response.getBody());
        }

        log.error(
                "Error inesperado al procesar la solicitud {}",
                request.getRequestURI(),
                ex
        );

        return ApiResponseFactory.build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Se ha producido un error inesperado.",
                null,
                request
        );  
    }
}