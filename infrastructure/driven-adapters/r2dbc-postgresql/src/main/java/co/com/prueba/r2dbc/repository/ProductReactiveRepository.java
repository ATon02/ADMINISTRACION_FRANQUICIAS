package co.com.prueba.r2dbc.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import co.com.prueba.r2dbc.entity.ProductEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReactiveRepository extends ReactiveCrudRepository<ProductEntity, Long>, ReactiveQueryByExampleExecutor<ProductEntity> {
    Mono<ProductEntity> findByName(String name);
    Flux<ProductEntity> findByBranchId(Long branchId);
    
    @Query("SELECT * FROM products WHERE branch_id = :branchId ORDER BY stock DESC LIMIT 1")
    Mono<ProductEntity> findProductWithMaxStockByBranchId(Long branchId);
}
