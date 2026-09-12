package com.pragma.tecnologia_service.domain.usecase;

import com.pragma.tecnologia_service.domain.api.ITechnologyRegisterServicePort;
import com.pragma.tecnologia_service.domain.builder.TechnologyBuilder;
import com.pragma.tecnologia_service.domain.model.Technology;
import com.pragma.tecnologia_service.domain.model.TechnologyCommand;
import com.pragma.tecnologia_service.domain.spi.ITechnologyPersistencePort;
import com.pragma.tecnologia_service.domain.validation.technology.DomainTechnologyValidator;
import com.pragma.tecnologia_service.domain.validation.technology.TechnologyValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyRegisterUseCase implements ITechnologyRegisterServicePort {

    private final ITechnologyPersistencePort iTechnologyPersistencePort;
    private final DomainTechnologyValidator domainTechnologyValidator;
    private final TechnologyValidator technologyValidator;


    @Override
    public Mono<Technology> create(TechnologyCommand technologyCommand) {
        return Mono.defer(() -> {
            domainTechnologyValidator.validateUserCommand(technologyCommand);

            return technologyValidator.validateTechnologyUniqueness(technologyCommand.name())
                    .then(Mono.defer(() -> {
                        Technology technology = TechnologyBuilder.buildTechnology(technologyCommand);
                        return iTechnologyPersistencePort.save(technology);
                    }));
        });
    }
}
