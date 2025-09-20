package co.com.prueba.r2dbc.adapter;

import co.com.prueba.model.product.Product;
import co.com.prueba.model.product.gateways.ProductRepository;
import co.com.prueba.r2dbc.entity.ProductEntity;
import co.com.prueba.r2dbc.helper.ReactiveAdapterOperations;
import co.com.prueba.r2dbc.repository.ProductReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    Product,
    ProductEntity,
    Long,
    ProductReactiveRepository
> implements ProductRepository {
    
    public ProductReactiveRepositoryAdapter(ProductReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }

    @Override
    public Flux<Product> findByBranchId(Long branchId) {
        return repository.findByBranchId(branchId)
                .map(this::toEntity);
    }

    @Override
    public Mono<Product> findProductWithMaxStockByBranchId(Long branchId) {
        return repository.findProductWithMaxStockByBranchId(branchId)
                .map(this::toEntity);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }
}
