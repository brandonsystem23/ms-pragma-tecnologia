package com.pragma.tecnologia_service.infrastructure.out.mysql.repository;

import com.pragma.tecnologia_service.infrastructure.out.mysql.entity.TechnologyEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITechnologyRepository extends ReactiveCrudRepository<TechnologyEntity, Long> {

    Mono<Boolean> existsByNameAndStatusTrue(String name);

    @Query("""
        SELECT id
        FROM technology
        WHERE id IN (:ids)
          AND status = true
        """)
    Flux<Long> findExistingIds(List<Long> ids);

    @Query("""
        SELECT id, name, description, status
        FROM technology
        WHERE id IN (:ids)
          AND status = true
        """)
    Flux<TechnologyEntity> findActiveByIdIn(List<Long> ids);

    @Modifying
    @Query("""
        UPDATE technology
        SET status = false
        WHERE id IN (:ids)
          AND status = true
        """)
    Mono<Integer> disableByIds(List<Long> ids);
}
