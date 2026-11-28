package com.api.tresvetas.common.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.Instant;

/**
 *
 * @author Jabier Zurro Aduriz
 */
@JsonPropertyOrder({"timestamp", "status", "path"})
public record ApiMetadata(
        Instant timestamp,
        int status,
        String path
) {
}