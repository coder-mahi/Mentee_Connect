package com.mahesh.mentee_connect.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to be thrown when a requested resource is not found
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with a default message
     */
    public ResourceNotFoundException() {
        super("Resource not found");
    }

    /**
     * Constructs a new ResourceNotFoundException with a custom message
     * @param message the detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new ResourceNotFoundException with a custom message and cause
     * @param message the detail message
     * @param cause the root cause
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new ResourceNotFoundException for a specific resource type and field
     * @param resourceName the name of the resource type (e.g., "Student", "Mentor")
     * @param fieldName the name of the field being searched
     * @param fieldValue the value of the field that wasn't found
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}