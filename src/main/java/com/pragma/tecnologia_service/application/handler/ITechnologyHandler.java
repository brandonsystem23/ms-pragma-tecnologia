package com.pragma.tecnologia_service.application.handler;

import com.pragma.tecnologia_service.application.dto.request.TechnologyRequest;
import com.pragma.tecnologia_service.application.dto.response.TechnologyResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITechnologyHandler {

    Mono<TechnologyResponse> create(TechnologyRequest request);

    Flux<TechnologyResponse> list();
}
