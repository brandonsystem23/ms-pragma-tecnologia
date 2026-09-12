package com.pragma.tecnologia_service.domain.validation;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
