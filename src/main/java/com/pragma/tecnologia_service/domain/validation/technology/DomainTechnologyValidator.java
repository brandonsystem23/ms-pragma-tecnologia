package com.pragma.tecnologia_service.domain.validation.technology;


import com.pragma.tecnologia_service.domain.exception.DomainErrorCode;
import com.pragma.tecnologia_service.domain.exception.DomainErrorMessages;
import com.pragma.tecnologia_service.domain.exception.DomainException;
import com.pragma.tecnologia_service.domain.model.TechnologyCommand;
import com.pragma.tecnologia_service.domain.validation.DescriptionValidator;
import com.pragma.tecnologia_service.domain.validation.NameValidator;
import com.pragma.tecnologia_service.domain.validation.ValidationUtils;

public class DomainTechnologyValidator {

    public void validateUserCommand(TechnologyCommand command) {

        if (ValidationUtils.isBlank(command.name())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.NAME_REQUIRED);
        }

        if (!NameValidator.isValid(command.name())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.NAME_MAX_LENGTH);
        }

        if (ValidationUtils.isBlank(command.description())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.DESCRIPTION_REQUIRED);
        }

        if (!DescriptionValidator.isValid(command.description())) {
            throw new DomainException(DomainErrorCode.VALIDATION_ERROR, DomainErrorMessages.DESCRIPTION_MAX_LENGTH);
        }

    }

}
