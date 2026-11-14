package com.api.tresvetas.common.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({"metadata", "message", "data"})
public record ApiResponse<T>(
        ApiMetadata metadata,
        String message,
        T data
) {
}