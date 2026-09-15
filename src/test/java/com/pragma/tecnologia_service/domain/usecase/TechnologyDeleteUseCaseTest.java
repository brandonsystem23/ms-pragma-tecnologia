package com.pragma.tecnologia_service.domain.usecase;

import com.pragma.tecnologia_service.domain.exception.DomainErrorCode;
import com.pragma.tecnologia_service.domain.exception.DomainErrorMessages;
import com.pragma.tecnologia_service.domain.exception.DomainException;
import com.pragma.tecnologia_service.domain.spi.ITechnologyPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnologyDeleteUseCaseTest {

    @Mock
    private ITechnologyPersistencePort technologyPersistencePort;

    @Mock
    private TransactionalOperator transactionalOperator;

    @InjectMocks
    private TechnologyDeleteUseCase technologyDeleteUseCase;

    @Test
    void shouldDeleteTechnologiesByIdsSuccessfully() {
        List<Long> ids = List.of(1L, 2L, 3L);

        when(technologyPersistencePort.deleteByIds(ids)).thenReturn(Mono.empty());
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StepVerifier.create(technologyDeleteUseCase.deleteByIds(ids))
                .verifyComplete();
    }

    @Test
    void shouldReturnDomainExceptionWhenDeleteFailsAndRollbackOccurs() {
        List<Long> ids = List.of(1L, 2L, 3L);

        when(technologyPersistencePort.deleteByIds(ids))
                .thenReturn(Mono.error(new RuntimeException("error técnico en base de datos")));
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StepVerifier.create(technologyDeleteUseCase.deleteByIds(ids))
                .expectErrorMatches(error ->
                        error instanceof DomainException &&
                                ((DomainException) error).getCode() == DomainErrorCode.INTERNAL_ERROR &&
                                error.getMessage().equals(DomainErrorMessages.TECHNOLOGY_DELETE_ROLLBACK_ERROR))
                .verify();
    }

    @Test
    void shouldReturnDomainExceptionWhenIdsListIsNull() {
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StepVerifier.create(technologyDeleteUseCase.deleteByIds(null))
                .expectErrorMatches(error ->
                        error instanceof DomainException &&
                                ((DomainException) error).getCode() == DomainErrorCode.VALIDATION_ERROR &&
                                error.getMessage().equals(DomainErrorMessages.IDS_DELETE_REQUIRED))
                .verify();
    }

    @Test
    void shouldReturnDomainExceptionWhenIdsListIsEmpty() {
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StepVerifier.create(technologyDeleteUseCase.deleteByIds(List.of()))
                .expectErrorMatches(error ->
                        error instanceof DomainException &&
                                ((DomainException) error).getCode() == DomainErrorCode.VALIDATION_ERROR &&
                                error.getMessage().equals(DomainErrorMessages.IDS_DELETE_REQUIRED))
                .verify();
    }

    @Test
    void shouldNotRemapExistingDomainException() {
        List<Long> ids = List.of(1L, 2L, 3L);

        DomainException domainException = new DomainException(
                DomainErrorCode.VALIDATION_ERROR,
                DomainErrorMessages.IDS_DELETE_REQUIRED
        );

        when(technologyPersistencePort.deleteByIds(ids))
                .thenReturn(Mono.error(domainException));
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StepVerifier.create(technologyDeleteUseCase.deleteByIds(ids))
                .expectErrorMatches(error ->
                        error instanceof DomainException &&
                                ((DomainException) error).getCode() == DomainErrorCode.VALIDATION_ERROR &&
                                error.getMessage().equals(DomainErrorMessages.IDS_DELETE_REQUIRED))
                .verify();
    }
}
