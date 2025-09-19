package com.management.franchises.management.franchises.respositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.management.franchises.management.franchises.models.Branch;

import reactor.core.publisher.Flux;

public interface BranchRepository extends ReactiveCrudRepository<Branch, Long> {
    
    Flux<Branch> findByFranchiseId(Long franchiseId);

}
