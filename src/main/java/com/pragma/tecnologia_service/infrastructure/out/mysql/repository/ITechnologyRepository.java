package com.pragma.tecnologia_service.infrastructure.out.mysql.repository;

import com.pragma.tecnologia_service.infrastructure.out.mysql.entity.TechnologyEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITechnologyRepository extends ReactiveCrudRepository<TechnologyEntity, Long> {

    Mono<Boolean> existsByName(String name);

    @Query("""
        SELECT id
        FROM technology
        WHERE id IN (:ids)
        """)
    Flux<Long> findExistingIds(List<Long> ids);

}
