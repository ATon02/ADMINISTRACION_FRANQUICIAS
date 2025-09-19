package com.management.franchises.management.franchises.services;

import java.util.List;

import com.management.franchises.management.franchises.models.dtos.request.DTOProduct;
import com.management.franchises.management.franchises.models.dtos.response.DTOBranchProduct;
import com.management.franchises.management.franchises.models.dtos.response.DTOProductResponse;

import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<DTOProductResponse> create(DTOProduct product);
    Mono<Void> delete(Long id);
    Mono<List<DTOBranchProduct>> getProductMaxStock(Long franchiseId);
    Mono<DTOProductResponse> updateName(Long id, String name);
    Mono<DTOProductResponse> updateStock(Long id, Long stock);



}
