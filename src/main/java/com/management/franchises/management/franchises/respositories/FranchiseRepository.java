package com.management.franchises.management.franchises.respositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.management.franchises.management.franchises.models.Franchise;

import reactor.core.publisher.Mono;

public interface FranchiseRepository extends ReactiveCrudRepository<Franchise, Long> {
    Mono<Franchise> findByName(String name);
}
