package com.pragma.tecnologia_service.domain.usecase;

import com.pragma.tecnologia_service.domain.api.ITechnologyDeleteServicePort;
import com.pragma.tecnologia_service.domain.exception.DomainErrorCode;
import com.pragma.tecnologia_service.domain.exception.DomainErrorMessages;
import com.pragma.tecnologia_service.domain.exception.DomainException;
import com.pragma.tecnologia_service.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class TechnologyDeleteUseCase implements ITechnologyDeleteServicePort {

    private final ITechnologyPersistencePort iTechnologyPersistencePort;
    private final TransactionalOperator transactionalOperator;

    @Override
    public Mono<Void> deleteByIds(List<Long> ids) {
        return Mono.defer(() -> {
                    if (ids == null || ids.isEmpty()) {
                        return Mono.error(new DomainException(
                                DomainErrorCode.VALIDATION_ERROR,
                                DomainErrorMessages.IDS_DELETE_REQUIRED
                        ));
                    }

                    return iTechnologyPersistencePort.deleteByIds(ids);
                })
                .as(transactionalOperator::transactional)
                .onErrorMap(throwable -> {
                    if (throwable instanceof DomainException) {
                        return throwable;
                    }

                    return new DomainException(
                            DomainErrorCode.INTERNAL_ERROR,
                            DomainErrorMessages.TECHNOLOGY_DELETE_ROLLBACK_ERROR
                    );
                });
    }
}
