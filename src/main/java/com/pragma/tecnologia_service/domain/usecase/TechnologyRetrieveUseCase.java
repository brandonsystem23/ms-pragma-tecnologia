package com.pragma.tecnologia_service.domain.usecase;

import com.pragma.tecnologia_service.domain.api.ITechnologyRetrieveServicePort;

import com.pragma.tecnologia_service.domain.model.Technology;
import com.pragma.tecnologia_service.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class TechnologyRetrieveUseCase implements ITechnologyRetrieveServicePort {

    private final ITechnologyPersistencePort iTechnologyPersistencePort;

    @Override
    public Flux<Technology> retrieve() {
        return iTechnologyPersistencePort.list();
    }
}
