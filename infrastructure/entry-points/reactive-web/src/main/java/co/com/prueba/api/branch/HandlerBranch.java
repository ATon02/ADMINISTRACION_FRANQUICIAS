package co.com.prueba.api.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.prueba.api.exceptions.NotValidFieldException;
import co.com.prueba.api.mapper.RequestBranchDTOMapper;
import co.com.prueba.usecase.branch.BranchUseCase;
import reactor.core.publisher.Mono;
import co.com.prueba.api.dtos.request.DTOBranch;

@Component
@RequiredArgsConstructor
public class HandlerBranch {

    private final BranchUseCase useCase;
    private final RequestBranchDTOMapper requestBranchDTOMapper;

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(DTOBranch.class)
            .switchIfEmpty(Mono.error(new NotValidFieldException("The body cannot be null")))
            .map(requestBranchDTOMapper::toModel)
            .flatMap(useCase::save)
            .map(requestBranchDTOMapper::toResponse)
            .flatMap(requestStatus -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestStatus));
    }

    public Mono<ServerResponse> updateName(ServerRequest serverRequest) {
        Long branchId = Long.parseLong(serverRequest.pathVariable("id"));
        String name = serverRequest.queryParam("name")
                .map(String::trim)
                .filter(n -> !n.isEmpty())
                .orElseThrow(() -> new NotValidFieldException("name is required"));

        return useCase.updateName(branchId, name)
                .map(requestBranchDTOMapper::toResponse)
                .flatMap(updated -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updated));
    }
}
