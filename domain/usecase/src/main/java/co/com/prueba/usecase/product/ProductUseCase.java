package co.com.prueba.usecase.product;

import co.com.prueba.model.branch.gateways.BranchRepository;
import co.com.prueba.model.branchproduct.BranchProduct;
import co.com.prueba.model.branchproduct.gateways.BranchProductRepository;
import co.com.prueba.model.franchise.gateways.FranchiseRepository;
import co.com.prueba.model.product.Product;
import co.com.prueba.model.product.gateways.ProductRepository;
import co.com.prueba.usecase.enums.ErrorMessages;
import co.com.prueba.usecase.exceptions.BusinessException;
import co.com.prueba.usecase.exceptions.TechnicalException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase implements IProductUseCase {

    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    private final FranchiseRepository franchiseRepository;
    private final BranchProductRepository branchProductRepository;

    @Override
    public Mono<Product> save(Product product) {
        return this.branchRepository.findById(product.getBranchId())
                .flatMap(branchExist -> this.productRepository.save(product)
                        .switchIfEmpty(Mono.error(new TechnicalException(ErrorMessages.PRODUCT_NOT_CREATED.getMessage()))))
                .switchIfEmpty(
                        Mono.error(new BusinessException(ErrorMessages.BRANCH_NOT_FOUND.getFormattedMessage(product.getBranchId()))));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return this.productRepository.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessages.PRODUCT_NOT_FOUND.getFormattedMessage(id))))
                .flatMap(product -> this.productRepository.delete(id));
    }

    @Override
    public Flux<BranchProduct> getProductMaxStock(Long franchiseId) {
        return this.franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessages.FRANCHISE_NOT_FOUND.getFormattedMessage(franchiseId))))
                .flatMapMany(franchise -> getProductMaxStockByFranchiseId(franchiseId));
    }

    @Override
    public Mono<Product> updateName(Long id, String name) {
        return this.productRepository.findById(id)
                .flatMap(product -> {
                    product.setName(name);
                    return this.productRepository.save(product)
                            .switchIfEmpty(Mono.error(new TechnicalException(ErrorMessages.PRODUCT_NOT_UPDATED.getMessage())));
                })
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessages.PRODUCT_NOT_FOUND.getFormattedMessage(id))));
    }

    private Flux<BranchProduct> getProductMaxStockByFranchiseId(Long franchiseId) {
        return branchProductRepository.findProductsWithMaxStockByFranchiseId(franchiseId)
                .switchIfEmpty(Flux.error(new BusinessException(
                        ErrorMessages.PRODUCTS_NOT_FOUND_FOR_FRANCHISE.getFormattedMessage(franchiseId))));
    }

    @Override
    public Mono<Product> updateStock(Long id, Long stock) {
        return this.productRepository.findById(id)
                .flatMap(product -> {
                    product.setStock(stock);
                    return this.productRepository.save(product)
                            .switchIfEmpty(Mono.error(new TechnicalException(ErrorMessages.PRODUCT_NOT_UPDATED.getMessage())));
                })
                .switchIfEmpty(Mono.error(new BusinessException(ErrorMessages.PRODUCT_NOT_FOUND.getFormattedMessage(id))));
    }
}
