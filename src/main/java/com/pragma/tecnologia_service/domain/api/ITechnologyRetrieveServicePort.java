package com.pragma.tecnologia_service.domain.api;

import com.pragma.tecnologia_service.domain.model.Technology;
import reactor.core.publisher.Flux;

public interface ITechnologyRetrieveServicePort {

    Flux<Technology> retrieve();
}
