package com.pragma.tecnologia_service.domain.api;

import com.pragma.tecnologia_service.domain.model.Technology;
import com.pragma.tecnologia_service.domain.model.TechnologyCommand;
import reactor.core.publisher.Mono;

public interface ITechnologyRegisterServicePort {

    Mono<Technology> create(TechnologyCommand technologyCommand);
}
