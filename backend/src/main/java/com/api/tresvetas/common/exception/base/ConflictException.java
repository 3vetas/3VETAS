package com.api.tresvetas.common.exception.base;

/**
 * 
 *
 * @author Jabier Zurro Aduriz
 */
public abstract class ConflictException extends RuntimeException {

    protected ConflictException(String message) {
        super(message);
    }
}