package com.pragma.tecnologia_service.domain.validation;

import com.pragma.tecnologia_service.domain.exception.DomainErrorCode;
import com.pragma.tecnologia_service.domain.exception.DomainErrorMessages;
import com.pragma.tecnologia_service.domain.exception.DomainException;
import com.pragma.tecnologia_service.domain.spi.ITechnologyPersistencePort;
import com.pragma.tecnologia_service.domain.validation.technology.TechnologyValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnologyValidatorTest {

    @Mock
    private ITechnologyPersistencePort technologyPersistencePort;

    @InjectMocks
    private TechnologyValidator technologyValidator;

    @Test
    void shouldCompleteWhenTechnologyNameDoesNotExist() {
        when(technologyPersistencePort.existsByName("Java")).thenReturn(Mono.just(false));

        StepVerifier.create(technologyValidator.validateTechnologyUniqueness("Java"))
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenTechnologyNameAlreadyExists() {
        when(technologyPersistencePort.existsByName("Java")).thenReturn(Mono.just(true));

        StepVerifier.create(technologyValidator.validateTechnologyUniqueness("Java"))
                .expectErrorMatches(error ->
                        error instanceof DomainException &&
                                ((DomainException) error).getCode() == DomainErrorCode.DUPLICATE_NAME &&
                                error.getMessage().equals(DomainErrorMessages.DUPLICATE_NAME))
                .verify();
    }
}
