package co.com.prueba.r2dbc.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import co.com.prueba.r2dbc.dto.BranchProductDTO;
import co.com.prueba.r2dbc.entity.ProductEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReactiveRepository extends ReactiveCrudRepository<ProductEntity, Long>, ReactiveQueryByExampleExecutor<ProductEntity> {
    Mono<ProductEntity> findByName(String name);
    Flux<ProductEntity> findByBranchId(Long branchId);
    
    @Query("SELECT * FROM products WHERE branch_id = :branchId ORDER BY stock DESC LIMIT 1")
    Mono<ProductEntity> findProductWithMaxStockByBranchId(Long branchId);
    
    @Query("SELECT b.name as branch_name, p.id as product_id, p.name as product_name, p.stock as product_stock " +
           "FROM products p " +
           "INNER JOIN branches b ON p.branch_id = b.id " +
           "WHERE b.franchise_id = :franchiseId " +
           "AND p.id IN (" +
           "  SELECT p2.id FROM products p2 " +
           "  WHERE p2.branch_id = p.branch_id " +
           "  ORDER BY p2.stock DESC LIMIT 1" +
           ")")
    Flux<BranchProductDTO> findProductsWithMaxStockByFranchiseId(Long franchiseId);
}
