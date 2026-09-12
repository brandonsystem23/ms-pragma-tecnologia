package com.pragma.tecnologia_service.infrastructure.out.mysql.adapter;

import com.pragma.tecnologia_service.domain.model.Technology;
import com.pragma.tecnologia_service.domain.spi.ITechnologyPersistencePort;
import com.pragma.tecnologia_service.infrastructure.out.mysql.entity.TechnologyEntity;
import com.pragma.tecnologia_service.infrastructure.out.mysql.mapper.TechnologyEntityMapper;
import com.pragma.tecnologia_service.infrastructure.out.mysql.repository.ITechnologyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class TechnologyPersistenceAdapter implements ITechnologyPersistencePort {

    private final ITechnologyRepository iTechnologyRepository;
    private final TechnologyEntityMapper technologyEntityMapper;


    @Override
    public Mono<Technology> save(Technology technology) {
        TechnologyEntity technologyEntity = technologyEntityMapper.toEntity(technology);
        return iTechnologyRepository.save(technologyEntity)
                .map(technologyEntityMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return iTechnologyRepository.existsByName(name);
    }

    @Override
    public Flux<Technology> list() {
        return iTechnologyRepository.findAll()
                .map(technologyEntityMapper::toDomain);
    }
}
