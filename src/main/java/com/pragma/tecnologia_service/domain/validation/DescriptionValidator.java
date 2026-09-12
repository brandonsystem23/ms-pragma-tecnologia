package com.pragma.tecnologia_service.domain.validation;

public final class DescriptionValidator {

    private static final int MAX_LENGTH = 90;

    private DescriptionValidator() {
    }

    public static boolean isValid(String description) {
        return description != null && description.length() <= MAX_LENGTH;
    }
}
