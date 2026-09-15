package com.pragma.tecnologia_service.application.handler;

import com.pragma.tecnologia_service.application.dto.request.TechnologyRequest;
import com.pragma.tecnologia_service.application.dto.response.TechnologyExistsByIdsResponse;
import com.pragma.tecnologia_service.application.dto.response.TechnologyResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITechnologyHandler {

    Mono<TechnologyResponse> create(TechnologyRequest request);

    Mono<TechnologyExistsByIdsResponse> existsByIds(List<Long> ids);

    Flux<TechnologyResponse> findByIds(List<Long> ids);

    Mono<Void> deleteByIds(List<Long> ids);
}
