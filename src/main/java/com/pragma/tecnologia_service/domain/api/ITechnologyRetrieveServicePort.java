package com.pragma.tecnologia_service.domain.api;

import com.pragma.tecnologia_service.domain.model.Technology;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ITechnologyRetrieveServicePort {

    Flux<Technology> retrieveByIds(List<Long> ids);
}
