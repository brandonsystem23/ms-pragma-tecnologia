package com.pragma.tecnologia_service.application.handler.impl;

import com.pragma.tecnologia_service.application.dto.request.TechnologyRequest;
import com.pragma.tecnologia_service.application.dto.response.TechnologyExistsByIdsResponse;
import com.pragma.tecnologia_service.application.dto.response.TechnologyResponse;
import com.pragma.tecnologia_service.application.mapper.TechnologyDtoMapper;
import com.pragma.tecnologia_service.domain.api.ITechnologyExistsByIdsServicePort;
import com.pragma.tecnologia_service.domain.api.ITechnologyRegisterServicePort;
import com.pragma.tecnologia_service.domain.api.ITechnologyRetrieveServicePort;
import com.pragma.tecnologia_service.domain.model.Technology;
import com.pragma.tecnologia_service.domain.model.TechnologyCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnologyHandlerTest {

    @Mock
    private ITechnologyRegisterServicePort technologyRegisterServicePort;

    @Mock
    private ITechnologyRetrieveServicePort technologyRetrieveServicePort;

    @Mock
    private ITechnologyExistsByIdsServicePort iTechnologyExistsByIdsServicePort;

    @Mock
    private TechnologyDtoMapper technologyDtoMapper;

    @InjectMocks
    private TechnologyHandler technologyHandler;

    @Test
    void shouldCreateTechnologyAndMapResponse() {
        TechnologyRequest request = new TechnologyRequest("Java", "Lenguaje de programación");
        TechnologyCommand command = new TechnologyCommand("Java", "Lenguaje de programación");
        Technology technology = Technology.builder()
                .id(1L)
                .name("Java")
                .description("Lenguaje de programación")
                .build();

        TechnologyResponse response = TechnologyResponse.builder()
                .id(1L)
                .name("Java")
                .description("Lenguaje de programación")
                .build();

        when(technologyDtoMapper.toCommand(request)).thenReturn(command);
        when(technologyRegisterServicePort.create(command)).thenReturn(Mono.just(technology));
        when(technologyDtoMapper.toResponse(technology)).thenReturn(response);

        StepVerifier.create(technologyHandler.create(request))
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    void shouldPropagateErrorWhenCreateFails() {
        TechnologyRequest request = new TechnologyRequest("Java", "Lenguaje de programación");
        TechnologyCommand command = new TechnologyCommand("Java", "Lenguaje de programación");

        when(technologyDtoMapper.toCommand(request)).thenReturn(command);
        when(technologyRegisterServicePort.create(command))
                .thenReturn(Mono.error(new RuntimeException("error creando tecnología")));

        StepVerifier.create(technologyHandler.create(request))
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().equals("error creando tecnología"))
                .verify();
    }

    @Test
    void shouldListTechnologiesAndMapResponse() {
        Technology technology1 = Technology.builder()
                .id(1L)
                .name("Java")
                .description("Lenguaje de programación")
                .build();

        Technology technology2 = Technology.builder()
                .id(2L)
                .name("Spring")
                .description("Framework Java")
                .build();

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

        when(technologyRetrieveServicePort.retrieve()).thenReturn(Flux.just(technology1, technology2));
        when(technologyDtoMapper.toResponse(technology1)).thenReturn(response1);
        when(technologyDtoMapper.toResponse(technology2)).thenReturn(response2);

        StepVerifier.create(technologyHandler.list())
                .expectNext(response1)
                .expectNext(response2)
                .verifyComplete();
    }

    @Test
    void shouldPropagateErrorWhenListFails() {
        when(technologyRetrieveServicePort.retrieve())
                .thenReturn(Flux.error(new RuntimeException("error listando tecnologías")));

        StepVerifier.create(technologyHandler.list())
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().equals("error listando tecnologías"))
                .verify();
    }

    @Test
    void shouldReturnExistingTechnologyIds() {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<Long> existingIds = List.of(1L, 3L);

        TechnologyExistsByIdsResponse response = TechnologyExistsByIdsResponse.builder()
                .existingIds(existingIds)
                .build();

        when(iTechnologyExistsByIdsServicePort.retrieveExistingIds(ids))
                .thenReturn(Flux.fromIterable(existingIds));

        StepVerifier.create(technologyHandler.existsByIds(ids))
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    void shouldPropagateErrorWhenExistsByIdsFails() {
        List<Long> ids = List.of(1L, 2L, 3L);

        when(iTechnologyExistsByIdsServicePort.retrieveExistingIds(ids))
                .thenReturn(Flux.error(
                        new RuntimeException("error buscando tecnologías existentes")
                ));

        StepVerifier.create(technologyHandler.existsByIds(ids))
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().equals("error buscando tecnologías existentes")
                )
                .verify();
    }
}
