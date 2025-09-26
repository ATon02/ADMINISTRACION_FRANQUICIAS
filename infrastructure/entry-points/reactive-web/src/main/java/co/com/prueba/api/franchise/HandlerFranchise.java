package co.com.prueba.api.franchise;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.prueba.api.dtos.request.DTOFranchise;
import co.com.prueba.api.exceptions.NotValidFieldExceptionGlobal;
import co.com.prueba.api.mapper.RequestFranchiseDTOMapper;
import co.com.prueba.usecase.enums.ErrorMessages;
import co.com.prueba.usecase.franchise.FranchiseUseCase;
import reactor.core.publisher.Mono;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;



@Component
@RequiredArgsConstructor
public class HandlerFranchise {
    private final FranchiseUseCase useCase;
    private final RequestFranchiseDTOMapper requestFranchiseDTOMapper;
    private final Validator validator;
    
    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(DTOFranchise.class)
            .switchIfEmpty(Mono.error(new NotValidFieldExceptionGlobal(ErrorMessages.BODY_CANNOT_BE_NULL.getMessage())))
            .doOnNext(this::validateDTO)
            .map(requestFranchiseDTOMapper::toModel)
            .flatMap(useCase::save)
            .map(requestFranchiseDTOMapper::toResponse)
            .flatMap(requestStatus -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestStatus));
    }

    public Mono<ServerResponse> updateName(ServerRequest serverRequest) {
        Long franchiseId = Long.parseLong(serverRequest.pathVariable("id"));
        String name = serverRequest.queryParam("name")
                .map(String::trim)
                .filter(n -> !n.isEmpty())
                .orElseThrow(() -> new NotValidFieldExceptionGlobal(ErrorMessages.NAME_REQUIRED.getMessage()));

        return useCase.updateName(franchiseId, name)
                .map(requestFranchiseDTOMapper::toResponse)  
                .flatMap(updated -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updated));
    }

    private void validateDTO(DTOFranchise dto) {
        Set<ConstraintViolation<DTOFranchise>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
