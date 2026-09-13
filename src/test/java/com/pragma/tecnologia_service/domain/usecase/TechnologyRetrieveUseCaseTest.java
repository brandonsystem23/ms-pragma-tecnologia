package com.pragma.tecnologia_service.domain.usecase;

import com.pragma.tecnologia_service.domain.model.Technology;
import com.pragma.tecnologia_service.domain.spi.ITechnologyPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnologyRetrieveUseCaseTest {

    @Mock
    private ITechnologyPersistencePort technologyPersistencePort;

    @InjectMocks
    private TechnologyRetrieveUseCase technologyRetrieveUseCase;


    @Test
    void shouldRetrieveExistingIdsSuccessfully() {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<Long> existingIds = List.of(1L, 3L);

        when(technologyPersistencePort.findExistingIds(ids))
                .thenReturn(Flux.fromIterable(existingIds));

        StepVerifier.create(technologyRetrieveUseCase.retrieveExistingIds(ids))
                .expectNext(1L)
                .expectNext(3L)
                .verifyComplete();
    }

    @Test
    void shouldPropagateErrorWhenRetrieveExistingIdsFails() {
        List<Long> ids = List.of(1L, 2L, 3L);

        when(technologyPersistencePort.findExistingIds(ids))
                .thenReturn(Flux.error(
                        new RuntimeException("error consultando IDs de tecnologías")
                ));

        StepVerifier.create(technologyRetrieveUseCase.retrieveExistingIds(ids))
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().equals("error consultando IDs de tecnologías"))
                .verify();
    }

    @Test
    void shouldRetrieveTechnologiesByIdsSuccessfully() {
        List<Long> ids = List.of(1L, 2L);

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

        when(technologyPersistencePort.findByIds(ids))
                .thenReturn(Flux.just(technology1, technology2));

        StepVerifier.create(technologyRetrieveUseCase.retrieveByIds(ids))
                .expectNext(technology1)
                .expectNext(technology2)
                .verifyComplete();
    }

    @Test
    void shouldPropagateErrorWhenRetrieveByIdsFails() {
        List<Long> ids = List.of(1L, 2L);

        when(technologyPersistencePort.findByIds(ids))
                .thenReturn(Flux.error(new RuntimeException("error consultando tecnologías por ids")));

        StepVerifier.create(technologyRetrieveUseCase.retrieveByIds(ids))
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().equals("error consultando tecnologías por ids"))
                .verify();
    }

}
