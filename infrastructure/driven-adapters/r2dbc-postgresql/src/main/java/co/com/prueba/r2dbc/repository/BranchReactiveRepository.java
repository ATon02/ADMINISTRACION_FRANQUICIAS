package co.com.prueba.r2dbc.repository;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import co.com.prueba.r2dbc.entity.BranchEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface BranchReactiveRepository extends ReactiveCrudRepository<BranchEntity, Long>, ReactiveQueryByExampleExecutor<BranchEntity> {
    Mono<BranchEntity> findByName(String name);
    Mono<BranchEntity> findByFranchiseId(Long franchiseId);
    Flux<BranchEntity> findAllByFranchiseId(Long franchiseId);
}
