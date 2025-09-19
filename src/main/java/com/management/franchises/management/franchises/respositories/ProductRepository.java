package com.management.franchises.management.franchises.respositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.management.franchises.management.franchises.models.Product;

import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    
    Flux<Product> findByBranchId(Long brachId);
}
