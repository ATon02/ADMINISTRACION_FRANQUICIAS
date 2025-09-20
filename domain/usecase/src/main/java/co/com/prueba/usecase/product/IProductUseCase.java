package co.com.prueba.usecase.product;

import java.util.List;

import co.com.prueba.model.product.Product;
import co.com.prueba.usecase.dtos.DTOBranchProduct;
import reactor.core.publisher.Mono;

public interface IProductUseCase {
    Mono<Product> save(Product product);
    Mono<Void> delete(Long id);
    Mono<Product> updateName(Long id, String name);
    Mono<List<DTOBranchProduct>> getProductMaxStock(Long branchId);
    Mono<Product> updateStock(Long id, Long stock);


}
