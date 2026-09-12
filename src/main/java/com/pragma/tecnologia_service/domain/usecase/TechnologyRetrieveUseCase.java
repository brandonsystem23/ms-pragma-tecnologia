package com.pragma.tecnologia_service.domain.usecase;

import com.pragma.tecnologia_service.domain.api.ITechnologyExistsByIdsServicePort;
import com.pragma.tecnologia_service.domain.api.ITechnologyRetrieveServicePort;

import com.pragma.tecnologia_service.domain.model.Technology;
import com.pragma.tecnologia_service.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.List;

@RequiredArgsConstructor
public class TechnologyRetrieveUseCase implements ITechnologyRetrieveServicePort, ITechnologyExistsByIdsServicePort {

    private final ITechnologyPersistencePort iTechnologyPersistencePort;

    @Override
    public Flux<Technology> retrieve() {
        return iTechnologyPersistencePort.list();
    }

    @Override
    public Flux<Long> retrieveExistingIds(List<Long> ids) {
        return iTechnologyPersistencePort.findExistingIds(ids);
    }
}
