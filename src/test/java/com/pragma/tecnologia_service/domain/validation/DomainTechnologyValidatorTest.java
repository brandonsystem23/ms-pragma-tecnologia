package com.pragma.tecnologia_service.domain.validation;

import com.pragma.tecnologia_service.domain.exception.DomainErrorCode;
import com.pragma.tecnologia_service.domain.exception.DomainErrorMessages;
import com.pragma.tecnologia_service.domain.exception.DomainException;
import com.pragma.tecnologia_service.domain.model.TechnologyCommand;
import com.pragma.tecnologia_service.domain.validation.technology.DomainTechnologyValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DomainTechnologyValidatorTest {

    private DomainTechnologyValidator domainTechnologyValidator;

    @BeforeEach
    void setUp() {
        domainTechnologyValidator = new DomainTechnologyValidator();
    }

    @Test
    void shouldPassWhenCommandIsValid() {
        TechnologyCommand command = new TechnologyCommand(
                "Java",
                "Lenguaje de programación"
        );

        Assertions.assertDoesNotThrow(() ->
                domainTechnologyValidator.validateUserCommand(command)
        );
    }

    @Test
    void shouldFailWhenNameIsNull() {
        TechnologyCommand command = new TechnologyCommand(
                null,
                "Lenguaje de programación"
        );

        DomainException exception = Assertions.assertThrows(
                DomainException.class,
                () -> domainTechnologyValidator.validateUserCommand(command)
        );

        Assertions.assertEquals(DomainErrorCode.VALIDATION_ERROR, exception.getCode());
        Assertions.assertEquals(DomainErrorMessages.NAME_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldFailWhenNameIsBlank() {
        TechnologyCommand command = new TechnologyCommand(
                "   ",
                "Lenguaje de programación"
        );

        DomainException exception = Assertions.assertThrows(
                DomainException.class,
                () -> domainTechnologyValidator.validateUserCommand(command)
        );

        Assertions.assertEquals(DomainErrorCode.VALIDATION_ERROR, exception.getCode());
        Assertions.assertEquals(DomainErrorMessages.NAME_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldFailWhenNameExceedsMaxLength() {
        TechnologyCommand command = new TechnologyCommand(
                "A".repeat(51),
                "Lenguaje de programación"
        );

        DomainException exception = Assertions.assertThrows(
                DomainException.class,
                () -> domainTechnologyValidator.validateUserCommand(command)
        );

        Assertions.assertEquals(DomainErrorCode.VALIDATION_ERROR, exception.getCode());
        Assertions.assertEquals(DomainErrorMessages.NAME_MAX_LENGTH, exception.getMessage());
    }

    @Test
    void shouldFailWhenDescriptionIsNull() {
        TechnologyCommand command = new TechnologyCommand(
                "Java",
                null
        );

        DomainException exception = Assertions.assertThrows(
                DomainException.class,
                () -> domainTechnologyValidator.validateUserCommand(command)
        );

        Assertions.assertEquals(DomainErrorCode.VALIDATION_ERROR, exception.getCode());
        Assertions.assertEquals(DomainErrorMessages.DESCRIPTION_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldFailWhenDescriptionIsBlank() {
        TechnologyCommand command = new TechnologyCommand(
                "Java",
                "   "
        );

        DomainException exception = Assertions.assertThrows(
                DomainException.class,
                () -> domainTechnologyValidator.validateUserCommand(command)
        );

        Assertions.assertEquals(DomainErrorCode.VALIDATION_ERROR, exception.getCode());
        Assertions.assertEquals(DomainErrorMessages.DESCRIPTION_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldFailWhenDescriptionExceedsMaxLength() {
        TechnologyCommand command = new TechnologyCommand(
                "Java",
                "D".repeat(91)
        );

        DomainException exception = Assertions.assertThrows(
                DomainException.class,
                () -> domainTechnologyValidator.validateUserCommand(command)
        );

        Assertions.assertEquals(DomainErrorCode.VALIDATION_ERROR, exception.getCode());
        Assertions.assertEquals(DomainErrorMessages.DESCRIPTION_MAX_LENGTH, exception.getMessage());
    }
}
