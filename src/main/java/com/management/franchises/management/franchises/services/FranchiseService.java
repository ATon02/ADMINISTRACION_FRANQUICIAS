package com.management.franchises.management.franchises.services;

import com.management.franchises.management.franchises.models.dtos.request.DTOFranchise;
import com.management.franchises.management.franchises.models.dtos.response.DTOFranchiseResponse;

import reactor.core.publisher.Mono;

public interface FranchiseService {
    
    Mono<DTOFranchiseResponse> create(DTOFranchise franchise);
    Mono<DTOFranchiseResponse> updateName(Long id, String name);
}
