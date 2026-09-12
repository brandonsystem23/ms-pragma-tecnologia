package com.pragma.tecnologia_service.domain.exception;

public final class DomainErrorMessages {

    private DomainErrorMessages() {
    }

    public static final String NAME_REQUIRED = "El campo name es obligatorio";
    public static final String DESCRIPTION_REQUIRED = "El campo description es obligatorio";
    public static final String DUPLICATE_NAME = "El nombre de la tecnologia  ya está registrado";
    public static final String NAME_MAX_LENGTH = "El nombre no puede tener más de 50 caracteres";
    public static final String DESCRIPTION_MAX_LENGTH = "La descripcion no puede tener más de 90 caracteres";
}
