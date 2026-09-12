package com.pragma.tecnologia_service.domain.api;

import reactor.core.publisher.Flux;

import java.util.List;

public interface ITechnologyExistsByIdsServicePort {

    Flux<Long> retrieveExistingIds(List<Long> ids);
}
