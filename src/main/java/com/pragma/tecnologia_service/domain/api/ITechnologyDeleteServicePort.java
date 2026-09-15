package com.pragma.tecnologia_service.domain.api;

import reactor.core.publisher.Mono;

import java.util.List;

public interface ITechnologyDeleteServicePort {

    Mono<Void> deleteByIds(List<Long> ids);
}
