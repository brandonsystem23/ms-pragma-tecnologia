package com.pragma.tecnologia_service.domain.spi;

import com.pragma.tecnologia_service.domain.model.Technology;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITechnologyPersistencePort {

    Mono<Technology> save(Technology technology);

    Mono<Boolean> existsByName(String name);

    Flux<Technology> list();
}
