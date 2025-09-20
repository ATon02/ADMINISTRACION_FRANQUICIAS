package co.com.prueba.api.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.prueba.api.dtos.request.DTOProduct;
import co.com.prueba.api.exceptions.NotValidFieldException;
import co.com.prueba.api.mapper.RequestProductDTOMapper;
import co.com.prueba.usecase.product.ProductUseCase;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HandlerProduct {
    private final ProductUseCase useCase;
    private final RequestProductDTOMapper requestProductDTOMapper;

    public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(DTOProduct.class)
            .switchIfEmpty(Mono.error(new NotValidFieldException("The body cannot be null")))
            .map(requestProductDTOMapper::toModel)
            .flatMap(useCase::save)
            .map(requestProductDTOMapper::toResponse)
            .flatMap(response -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(response));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest serverRequest) {
        Long productId = Long.parseLong(serverRequest.pathVariable("id"));
        
        return useCase.delete(productId)
                .then(ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("Product deleted successfully"));
    }

    public Mono<ServerResponse> getProductMaxStock(ServerRequest serverRequest) {
        Long franchiseId = Long.parseLong(serverRequest.pathVariable("franchiseId"));
        
        return useCase.getProductMaxStock(franchiseId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> updateName(ServerRequest serverRequest) {
        Long productId = Long.parseLong(serverRequest.pathVariable("id"));
        String name = serverRequest.queryParam("name")
                .map(String::trim)
                .filter(n -> !n.isEmpty())
                .orElseThrow(() -> new NotValidFieldException("name is required"));

        return useCase.updateName(productId, name)
                .map(requestProductDTOMapper::toResponse)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> updateStock(ServerRequest serverRequest) {
        Long productId = Long.parseLong(serverRequest.pathVariable("id"));
        Long stock = serverRequest.queryParam("stock")
                .map(this::parseLongOrThrow)
                .orElseThrow(() -> new NotValidFieldException("stock is required"));

        return useCase.updateStock(productId, stock)
                .map(requestProductDTOMapper::toResponse)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    private Long parseLongOrThrow(String value) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            throw new NotValidFieldException("stock must be a valid number");
        }
    }
}
