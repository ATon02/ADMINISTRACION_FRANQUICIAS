package co.com.prueba.r2dbc.adapter;

import co.com.prueba.model.branch.Branch;
import co.com.prueba.model.branch.gateways.BranchRepository;
import co.com.prueba.r2dbc.entity.BranchEntity;
import co.com.prueba.r2dbc.helper.ReactiveAdapterOperations;
import co.com.prueba.r2dbc.repository.BranchReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BranchReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    Branch,
    BranchEntity,
    Long,
    BranchReactiveRepository
> implements BranchRepository {
    
    public BranchReactiveRepositoryAdapter(BranchReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Branch.class));
    }

    @Override
    public Mono<Branch> findByFranchiseId(Long franchiseId) {
        return repository.findByFranchiseId(franchiseId)
                .map(this::toEntity);
    }

    @Override
    public Flux<Branch> findAllByFranchiseId(Long franchiseId) {
        return repository.findAllByFranchiseId(franchiseId)
                .map(this::toEntity);
    }
}
