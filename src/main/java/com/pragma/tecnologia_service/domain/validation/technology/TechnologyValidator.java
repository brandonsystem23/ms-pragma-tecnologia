package com.pragma.tecnologia_service.domain.validation.technology;

import com.pragma.tecnologia_service.domain.exception.DomainErrorCode;
import com.pragma.tecnologia_service.domain.exception.DomainErrorMessages;
import com.pragma.tecnologia_service.domain.exception.DomainException;
import com.pragma.tecnologia_service.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyValidator {

    private final ITechnologyPersistencePort iTechnologyPersistencePort;

    public Mono<Void> validateTechnologyUniqueness(String name) {
        return iTechnologyPersistencePort.existsByName(name)
                .flatMap(technologyAlreadyExists ->
                        Boolean.TRUE.equals(technologyAlreadyExists)
                                ? Mono.error(new DomainException(
                                DomainErrorCode.DUPLICATE_NAME,
                                DomainErrorMessages.DUPLICATE_NAME
                        )) : Mono.empty()
                );
    }


}
