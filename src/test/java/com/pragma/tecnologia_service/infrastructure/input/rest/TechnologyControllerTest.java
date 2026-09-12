package com.pragma.tecnologia_service.infrastructure.input.rest;

import com.pragma.tecnologia_service.application.dto.request.TechnologyRequest;
import com.pragma.tecnologia_service.application.dto.response.TechnologyResponse;
import com.pragma.tecnologia_service.application.handler.ITechnologyHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnologyControllerTest {

    @Mock
    private ITechnologyHandler technologyHandler;

    @InjectMocks
    private TechnologyController technologyController;

    @Test
    void shouldCreateTechnologySuccessfully() {
        TechnologyRequest request = new TechnologyRequest("Java", "Lenguaje de programación");

        TechnologyResponse response = TechnologyResponse.builder()
                .id(1L)
                .name("Java")
                .description("Lenguaje de programación")
                .build();

        when(technologyHandler.create(request)).thenReturn(Mono.just(response));

        StepVerifier.create(technologyController.createTechnology(request))
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    void shouldPropagateErrorWhenCreateFails() {
        TechnologyRequest request = new TechnologyRequest("Java", "Lenguaje de programación");

        when(technologyHandler.create(request))
                .thenReturn(Mono.error(new RuntimeException("error creando tecnología")));

        StepVerifier.create(technologyController.createTechnology(request))
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().equals("error creando tecnología"))
                .verify();
    }

    @Test
    void shouldRetrieveTechnologiesSuccessfully() {
        TechnologyResponse response1 = TechnologyResponse.builder()
                .id(1L)
                .name("Java")
                .description("Lenguaje de programación")
                .build();

        TechnologyResponse response2 = TechnologyResponse.builder()
                .id(2L)
                .name("Spring")
                .description("Framework Java")
                .build();

        when(technologyHandler.list()).thenReturn(Flux.just(response1, response2));

        StepVerifier.create(technologyController.retrieveTechnology())
                .expectNext(response1)
                .expectNext(response2)
                .verifyComplete();
    }

    @Test
    void shouldPropagateErrorWhenRetrieveFails() {
        when(technologyHandler.list())
                .thenReturn(Flux.error(new RuntimeException("error listando tecnologías")));

        StepVerifier.create(technologyController.retrieveTechnology())
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().equals("error listando tecnologías"))
                .verify();
    }
}
