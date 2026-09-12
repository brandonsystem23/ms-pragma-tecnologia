package com.pragma.tecnologia_service.domain.validation;

public final class NameValidator {

    private static final int MAX_LENGTH = 50;

    private NameValidator() {
    }

    public static boolean isValid(String name) {
        return name != null && name.length() <= MAX_LENGTH;
    }
}
