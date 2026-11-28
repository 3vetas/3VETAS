package com.api.tresvetas.common.exception.base;

/**
 *
 *
 * @author Jabier Zurro Aduriz
 */
public abstract class NotFoundException extends RuntimeException {

    protected NotFoundException(String message) {
        super(message);
    }
}