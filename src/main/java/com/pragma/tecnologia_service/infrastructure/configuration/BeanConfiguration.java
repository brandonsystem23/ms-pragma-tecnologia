package com.pragma.tecnologia_service.infrastructure.configuration;

import com.pragma.tecnologia_service.domain.api.ITechnologyDeleteServicePort;
import com.pragma.tecnologia_service.domain.api.ITechnologyExistsByIdsServicePort;
import com.pragma.tecnologia_service.domain.api.ITechnologyRegisterServicePort;
import com.pragma.tecnologia_service.domain.api.ITechnologyRetrieveServicePort;
import com.pragma.tecnologia_service.domain.spi.ITechnologyPersistencePort;
import com.pragma.tecnologia_service.domain.usecase.TechnologyDeleteUseCase;
import com.pragma.tecnologia_service.domain.usecase.TechnologyRegisterUseCase;
import com.pragma.tecnologia_service.domain.usecase.TechnologyRetrieveUseCase;
import com.pragma.tecnologia_service.domain.validation.technology.DomainTechnologyValidator;
import com.pragma.tecnologia_service.domain.validation.technology.TechnologyValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
public class BeanConfiguration {

    @Bean
    public DomainTechnologyValidator domainTechnologyValidator() {
        return new DomainTechnologyValidator();
    }

    @Bean
    public TechnologyValidator technologyValidator(ITechnologyPersistencePort iTechnologyPersistencePort) {
        return new TechnologyValidator(iTechnologyPersistencePort);
    }

    @Bean
    public ITechnologyRegisterServicePort technologyRegisterUseCase(
            ITechnologyPersistencePort iTechnologyPersistencePort,
            DomainTechnologyValidator domainTechnologyValidator,
            TechnologyValidator technologyValidator
    ) {
        return new TechnologyRegisterUseCase(
                iTechnologyPersistencePort,
                domainTechnologyValidator,
                technologyValidator
        );
    }

    @Bean(name = "technologyRetrieveServicePort")
    public ITechnologyRetrieveServicePort technologyRetrieveServicePort(
            ITechnologyPersistencePort iTechnologyPersistencePort
    ) {
        return new TechnologyRetrieveUseCase(iTechnologyPersistencePort);
    }

    @Bean(name = "technologyExistsByIdsServicePort")
    public ITechnologyExistsByIdsServicePort technologyExistsByIdsServicePort(
            ITechnologyPersistencePort iTechnologyPersistencePort
    ) {
        return new TechnologyRetrieveUseCase(iTechnologyPersistencePort);
    }

    @Bean
    public ITechnologyDeleteServicePort technologyDeleteServicePort(
            ITechnologyPersistencePort iTechnologyPersistencePort,
            TransactionalOperator transactionalOperator
    ) {
        return new TechnologyDeleteUseCase(
                iTechnologyPersistencePort,
                transactionalOperator
        );
    }
}
