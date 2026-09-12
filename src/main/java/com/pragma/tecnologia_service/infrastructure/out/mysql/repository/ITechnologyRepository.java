package com.pragma.tecnologia_service.infrastructure.out.mysql.repository;

import com.pragma.tecnologia_service.infrastructure.out.mysql.entity.TechnologyEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ITechnologyRepository extends ReactiveCrudRepository<TechnologyEntity, Long> {

    Mono<Boolean> existsByName(String name);


}
