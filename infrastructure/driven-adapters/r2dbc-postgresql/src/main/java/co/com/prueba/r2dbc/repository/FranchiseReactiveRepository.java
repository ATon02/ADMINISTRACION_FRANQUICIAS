package co.com.prueba.r2dbc.repository;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import co.com.prueba.r2dbc.entity.FranchiseEntity;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface FranchiseReactiveRepository extends ReactiveCrudRepository<FranchiseEntity, Long>, ReactiveQueryByExampleExecutor<FranchiseEntity> {
    Mono<FranchiseEntity> findByName(String name);
}
