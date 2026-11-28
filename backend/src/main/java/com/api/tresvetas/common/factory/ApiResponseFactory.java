package com.api.tresvetas.common.factory;

import com.api.tresvetas.common.dto.ApiMetadata;
import com.api.tresvetas.common.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Jabier Zurro Aduriz
 */
public final class ApiResponseFactory {

    private ApiResponseFactory() {
    }

    public static <T> ResponseEntity<ApiResponse<T>> build(
            HttpStatusCode status,
            String message,
            T data,
            HttpServletRequest request
    ) {
        ApiMetadata metadata = new ApiMetadata(
                Instant.now(),
                status.value(),
                request.getRequestURI()
        );

        ApiResponse<T> response = new ApiResponse<>(
                metadata,
                message,
                data
        );

        return ResponseEntity.status(status).body(response);
    }
}