package co.com.prueba.usecase.product;

import java.util.Comparator;
import java.util.List;

import co.com.prueba.model.branch.gateways.BranchRepository;
import co.com.prueba.model.franchise.gateways.FranchiseRepository;
import co.com.prueba.model.product.Product;
import co.com.prueba.model.product.gateways.ProductRepository;
import co.com.prueba.usecase.dtos.DTOBranchProduct;
import co.com.prueba.usecase.dtos.DTOProductResponse;
import co.com.prueba.usecase.exceptions.NotFoundException;
import co.com.prueba.usecase.exceptions.NotValidFieldException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase implements IProductUseCase {

    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    private final FranchiseRepository franchiseRepository;

    @Override
    public Mono<Product> save(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            return Mono.error(new NotValidFieldException("Product name cannot be blank"));
        }
        if (product.getStock() == null || product.getStock() <= 0) {
            return Mono.error(new NotValidFieldException("Stock cannot be 0"));
        }
        if (product.getBranchId() == null || product.getBranchId() <= 0) {
            return Mono.error(new NotValidFieldException("Branch id cannot be 0"));
        }
        return this.branchRepository.findById(product.getBranchId())
                .flatMap(branchExist -> this.productRepository.save(product)
                        .switchIfEmpty(Mono.error(new Exception("Product not created"))))
                .switchIfEmpty(
                        Mono.error(new NotFoundException("Branch with id " + product.getBranchId() + " not found")));
    }

    @Override
    public Mono<Void> delete(Long id) {
        if (id == null || id <= 0) {
            return Mono.error(new NotValidFieldException("Product id cannot be 0"));
        }
        return this.productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product with id " + id + " not found")))
                .flatMap(product -> this.productRepository.delete(id));
    }

    @Override
    public Mono<List<DTOBranchProduct>> getProductMaxStock(Long franchiseId) {
        if (franchiseId == null || franchiseId <= 0) {
            return Mono.error(new NotValidFieldException("Franchise id cannot be 0"));
        }
        return this.franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise with id " + franchiseId + " not found")))
                .flatMap(franchise -> getProductMaxStockByFranchiseId(franchiseId));
    }

    @Override
    public Mono<Product> updateName(Long id, String name) {
        if (id == null || id <= 0) {
            return Mono.error(new NotValidFieldException("Product ID cannot be 0"));
        }
        if (name == null || name.isBlank()) {
            return Mono.error(new NotValidFieldException("Product name cannot be blank"));
        }

        return this.productRepository.findById(id)
                .flatMap(product -> {
                    product.setName(name);
                    return this.productRepository.save(product)
                            .switchIfEmpty(Mono.error(new Exception("Product not updated")));
                })
                .switchIfEmpty(Mono.error(new NotFoundException("Product with id " + id + " not found")));
    }

    private Mono<List<DTOBranchProduct>> getProductMaxStockByFranchiseId(Long franchiseId) {
        return branchRepository.findAllByFranchiseId(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Not found branches for franchise with id " + franchiseId)))
                .flatMap(branch -> 
                    productRepository.findProductWithMaxStockByBranchId(branch.getId())
                            .map(maxStockProduct -> new DTOBranchProduct(
                                    branch.getName(), 
                                    toProductResponse(maxStockProduct)))
                            .switchIfEmpty(Mono.empty()) 
                )
                .collectList();
    }

    private DTOProductResponse toProductResponse(Product product) {
        return DTOProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .build();
    }

    @Override
    public Mono<Product> updateStock(Long id, Long stock) {
        if (id == null || id <= 0) {
            return Mono.error(new NotValidFieldException("Product ID cannot be 0"));
        }
        if (stock == null || stock <= 0) {
            return Mono.error(new NotValidFieldException("Stock cannot be 0"));
        }

        return this.productRepository.findById(id)
                .flatMap(product -> {
                    product.setStock(stock);
                    return this.productRepository.save(product)
                            .switchIfEmpty(Mono.error(new Exception("Product not updated")));
                })
                .switchIfEmpty(Mono.error(new NotFoundException("Product with id " + id + " not found")));
    }
}
