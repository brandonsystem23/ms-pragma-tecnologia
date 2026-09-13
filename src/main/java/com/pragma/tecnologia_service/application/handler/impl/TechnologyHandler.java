package com.pragma.tecnologia_service.application.handler.impl;

import com.pragma.tecnologia_service.application.dto.request.TechnologyRequest;
import com.pragma.tecnologia_service.application.dto.response.TechnologyExistsByIdsResponse;
import com.pragma.tecnologia_service.application.dto.response.TechnologyResponse;
import com.pragma.tecnologia_service.application.handler.ITechnologyHandler;
import com.pragma.tecnologia_service.application.mapper.TechnologyDtoMapper;
import com.pragma.tecnologia_service.domain.api.ITechnologyExistsByIdsServicePort;
import com.pragma.tecnologia_service.domain.api.ITechnologyRegisterServicePort;
import com.pragma.tecnologia_service.domain.api.ITechnologyRetrieveServicePort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TechnologyHandler implements ITechnologyHandler {

    private final ITechnologyRegisterServicePort iTechnologyRegisterServicePort;
    private final ITechnologyRetrieveServicePort iTechnologyRetrieveServicePort;
    private final ITechnologyExistsByIdsServicePort iTechnologyExistsByIdsServicePort;
    private final TechnologyDtoMapper technologyDtoMapper;

    public TechnologyHandler(
            ITechnologyRegisterServicePort iTechnologyRegisterServicePort,
            @Qualifier("technologyRetrieveServicePort")
            ITechnologyRetrieveServicePort iTechnologyRetrieveServicePort,
            @Qualifier("technologyExistsByIdsServicePort")
            ITechnologyExistsByIdsServicePort iTechnologyExistsByIdsServicePort,
            TechnologyDtoMapper technologyDtoMapper
    ) {
        this.iTechnologyRegisterServicePort = iTechnologyRegisterServicePort;
        this.iTechnologyRetrieveServicePort = iTechnologyRetrieveServicePort;
        this.iTechnologyExistsByIdsServicePort = iTechnologyExistsByIdsServicePort;
        this.technologyDtoMapper = technologyDtoMapper;
    }

    @Override
    public Mono<TechnologyResponse> create(TechnologyRequest request) {
        return iTechnologyRegisterServicePort.create(technologyDtoMapper.toCommand(request))
                .map(technologyDtoMapper::toResponse);
    }

    @Override
    public Mono<TechnologyExistsByIdsResponse> existsByIds(List<Long> ids) {
        return iTechnologyExistsByIdsServicePort.retrieveExistingIds(ids)
                .collectList()
                .map(existingIds -> TechnologyExistsByIdsResponse.builder()
                        .existingIds(existingIds)
                        .build());
    }

    @Override
    public Flux<TechnologyResponse> findByIds(List<Long> ids) {
        return iTechnologyRetrieveServicePort.retrieveByIds(ids)
                .map(technologyDtoMapper::toResponse);
    }
}
