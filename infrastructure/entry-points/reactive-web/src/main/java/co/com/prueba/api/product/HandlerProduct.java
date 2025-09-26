package co.com.prueba.api.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.prueba.api.dtos.request.DTOProduct;
import co.com.prueba.api.exceptions.NotValidFieldExceptionGlobal;
import co.com.prueba.api.mapper.RequestProductDTOMapper;
import co.com.prueba.usecase.enums.ErrorMessages;
import co.com.prueba.usecase.product.ProductUseCase;
import reactor.core.publisher.Mono;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;



@Component
@RequiredArgsConstructor
public class HandlerProduct {
    private final ProductUseCase useCase;
    private final RequestProductDTOMapper requestProductDTOMapper;
    private final Validator validator;

    public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(DTOProduct.class)
            .switchIfEmpty(Mono.error(new NotValidFieldExceptionGlobal(ErrorMessages.BODY_CANNOT_BE_NULL.getMessage())))
            .doOnNext(this::validateDTO)
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
                        .bodyValue(ErrorMessages.PRODUCT_DELETED_SUCCESS.getMessage()));
    }

    public Mono<ServerResponse> getProductMaxStock(ServerRequest serverRequest) {
        Long franchiseId = Long.parseLong(serverRequest.pathVariable("franchiseId"));
        
        return useCase.getProductMaxStock(franchiseId)
                .collectList()
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> updateName(ServerRequest serverRequest) {
        Long productId = Long.parseLong(serverRequest.pathVariable("id"));
        String name = serverRequest.queryParam("name")
                .map(String::trim)
                .filter(n -> !n.isEmpty())
                .orElseThrow(() -> new NotValidFieldExceptionGlobal(ErrorMessages.NAME_REQUIRED.getMessage()));

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
                .orElseThrow(() -> new NotValidFieldExceptionGlobal(ErrorMessages.STOCK_REQUIRED.getMessage()));

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
            throw new NotValidFieldExceptionGlobal(ErrorMessages.STOCK_INVALID_NUMBER.getMessage());
        }
    }

    private void validateDTO(DTOProduct dto) {
        Set<ConstraintViolation<DTOProduct>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
