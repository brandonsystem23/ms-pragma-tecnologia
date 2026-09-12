package com.pragma.tecnologia_service.domain.usecase;

import com.pragma.tecnologia_service.domain.model.Technology;
import com.pragma.tecnologia_service.domain.model.TechnologyCommand;
import com.pragma.tecnologia_service.domain.spi.ITechnologyPersistencePort;
import com.pragma.tecnologia_service.domain.validation.technology.DomainTechnologyValidator;
import com.pragma.tecnologia_service.domain.validation.technology.TechnologyValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnologyRegisterUseCaseTest {

    @Mock
    private ITechnologyPersistencePort technologyPersistencePort;

    @Mock
    private DomainTechnologyValidator domainTechnologyValidator;

    @Mock
    private TechnologyValidator technologyValidator;

    @InjectMocks
    private TechnologyRegisterUseCase technologyRegisterUseCase;

    @Test
    void shouldCreateTechnologySuccessfully() {
        TechnologyCommand command = new TechnologyCommand("Java", "Lenguaje de programación");

        Technology savedTechnology = Technology.builder()
                .id(1L)
                .name("Java")
                .description("Lenguaje de programación")
                .build();

        doNothing().when(domainTechnologyValidator).validateUserCommand(command);
        when(technologyValidator.validateTechnologyUniqueness("Java")).thenReturn(Mono.empty());
        when(technologyPersistencePort.save(org.mockito.ArgumentMatchers.any(Technology.class)))
                .thenReturn(Mono.just(savedTechnology));

        StepVerifier.create(technologyRegisterUseCase.create(command))
                .expectNext(savedTechnology)
                .verifyComplete();
    }

    @Test
    void shouldPropagateErrorWhenUniquenessValidationFails() {
        TechnologyCommand command = new TechnologyCommand("Java", "Lenguaje de programación");

        doNothing().when(domainTechnologyValidator).validateUserCommand(command);
        when(technologyValidator.validateTechnologyUniqueness("Java"))
                .thenReturn(Mono.error(new RuntimeException("nombre duplicado")));

        StepVerifier.create(technologyRegisterUseCase.create(command))
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().equals("nombre duplicado"))
                .verify();
    }

    @Test
    void shouldPropagateErrorWhenSaveFails() {
        TechnologyCommand command = new TechnologyCommand("Java", "Lenguaje de programación");

        doNothing().when(domainTechnologyValidator).validateUserCommand(command);
        when(technologyValidator.validateTechnologyUniqueness("Java")).thenReturn(Mono.empty());
        when(technologyPersistencePort.save(org.mockito.ArgumentMatchers.any(Technology.class)))
                .thenReturn(Mono.error(new RuntimeException("error guardando tecnología")));

        StepVerifier.create(technologyRegisterUseCase.create(command))
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().equals("error guardando tecnología"))
                .verify();
    }
}
