package co.com.prueba.usecase.product;

import co.com.prueba.model.branchproduct.BranchProduct;
import co.com.prueba.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductUseCase {
    Mono<Product> save(Product product);
    Mono<Void> delete(Long id);
    Mono<Product> updateName(Long id, String name);
    Flux<BranchProduct> getProductMaxStock(Long branchId);
    Mono<Product> updateStock(Long id, Long stock);


}
