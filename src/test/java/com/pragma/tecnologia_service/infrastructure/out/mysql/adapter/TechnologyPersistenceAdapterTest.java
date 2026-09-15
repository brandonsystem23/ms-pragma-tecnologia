package com.pragma.tecnologia_service.infrastructure.out.mysql.adapter;

import com.pragma.tecnologia_service.domain.model.Technology;
import com.pragma.tecnologia_service.infrastructure.out.mysql.entity.TechnologyEntity;
import com.pragma.tecnologia_service.infrastructure.out.mysql.mapper.TechnologyEntityMapper;
import com.pragma.tecnologia_service.infrastructure.out.mysql.repository.ITechnologyRepository;
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
class TechnologyPersistenceAdapterTest {

    @Mock
    private ITechnologyRepository technologyRepository;

    @Mock
    private TechnologyEntityMapper technologyEntityMapper;

    @InjectMocks
    private TechnologyPersistenceAdapter technologyPersistenceAdapter;

    @Test
    void shouldSaveTechnologySuccessfully() {
        Technology technology = Technology.builder()
                .name("Java")
                .description("Lenguaje de programación")
                .status(true)
                .build();

        TechnologyEntity entity = TechnologyEntity.builder()
                .name("Java")
                .description("Lenguaje de programación")
                .status(true)
                .build();

        TechnologyEntity savedEntity = TechnologyEntity.builder()
                .id(1L)
                .name("Java")
                .description("Lenguaje de programación")
                .status(true)
                .build();

        Technology savedTechnology = Technology.builder()
                .id(1L)
                .name("Java")
                .description("Lenguaje de programación")
                .status(true)
                .build();

        when(technologyEntityMapper.toEntity(technology)).thenReturn(entity);
        when(technologyRepository.save(entity)).thenReturn(Mono.just(savedEntity));
        when(technologyEntityMapper.toDomain(savedEntity)).thenReturn(savedTechnology);

        StepVerifier.create(technologyPersistenceAdapter.save(technology))
                .expectNext(savedTechnology)
                .verifyComplete();
    }

    @Test
    void shouldCheckIfTechnologyExistsByName() {
        when(technologyRepository.existsByNameAndStatusTrue("Java")).thenReturn(Mono.just(true));

        StepVerifier.create(technologyPersistenceAdapter.existsByName("Java"))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void shouldFindExistingTechnologyIdsSuccessfully() {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<Long> existingIds = List.of(1L, 3L);

        when(technologyRepository.findExistingIds(ids))
                .thenReturn(Flux.fromIterable(existingIds));

        StepVerifier.create(technologyPersistenceAdapter.findExistingIds(ids))
                .expectNext(1L)
                .expectNext(3L)
                .verifyComplete();
    }

    @Test
    void shouldFindActiveTechnologiesByIdsSuccessfully() {
        List<Long> ids = List.of(1L, 2L);

        TechnologyEntity entity1 = TechnologyEntity.builder()
                .id(1L)
                .name("Java")
                .description("Lenguaje de programación")
                .status(true)
                .build();

        TechnologyEntity entity2 = TechnologyEntity.builder()
                .id(2L)
                .name("Spring")
                .description("Framework Java")
                .status(true)
                .build();

        Technology technology1 = Technology.builder()
                .id(1L)
                .name("Java")
                .description("Lenguaje de programación")
                .status(true)
                .build();

        Technology technology2 = Technology.builder()
                .id(2L)
                .name("Spring")
                .description("Framework Java")
                .status(true)
                .build();

        when(technologyRepository.findActiveByIdIn(ids)).thenReturn(Flux.just(entity1, entity2));
        when(technologyEntityMapper.toDomain(entity1)).thenReturn(technology1);
        when(technologyEntityMapper.toDomain(entity2)).thenReturn(technology2);

        StepVerifier.create(technologyPersistenceAdapter.findByIds(ids))
                .expectNext(technology1)
                .expectNext(technology2)
                .verifyComplete();
    }

    @Test
    void shouldDisableTechnologiesByIdsSuccessfully() {
        List<Long> ids = List.of(1L, 2L, 3L);

        when(technologyRepository.disableByIds(ids)).thenReturn(Mono.just(3));

        StepVerifier.create(technologyPersistenceAdapter.deleteByIds(ids))
                .verifyComplete();
    }

    @Test
    void shouldPropagateErrorWhenDisableByIdsFails() {
        List<Long> ids = List.of(1L, 2L, 3L);

        when(technologyRepository.disableByIds(ids))
                .thenReturn(Mono.error(new RuntimeException("error desactivando tecnologías en repositorio")));

        StepVerifier.create(technologyPersistenceAdapter.deleteByIds(ids))
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().equals("error desactivando tecnologías en repositorio"))
                .verify();
    }
}
