package com.management.franchises.management.franchises.services;

import com.management.franchises.management.franchises.models.dtos.request.DTOBranch;
import com.management.franchises.management.franchises.models.dtos.response.DTOBranchResponse;

import reactor.core.publisher.Mono;

public interface BranchService {
    Mono<DTOBranchResponse> create(DTOBranch branch);
    Mono<DTOBranchResponse> updateName(Long id, String name);

}
