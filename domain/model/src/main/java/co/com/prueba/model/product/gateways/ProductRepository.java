package co.com.prueba.model.product.gateways;

import co.com.prueba.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> save(Product product);
    Mono<Product> findById(Long id);
    Flux<Product> findByBranchId(Long branchId);
    Mono<Product> findProductWithMaxStockByBranchId(Long branchId);
    Mono<Void> delete(Long id);
}
