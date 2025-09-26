package co.com.prueba.model.branchproduct.gateways;

import co.com.prueba.model.branchproduct.BranchProduct;
import reactor.core.publisher.Flux;

public interface BranchProductRepository {
    Flux<BranchProduct> findProductsWithMaxStockByFranchiseId(Long franchiseId);
}
