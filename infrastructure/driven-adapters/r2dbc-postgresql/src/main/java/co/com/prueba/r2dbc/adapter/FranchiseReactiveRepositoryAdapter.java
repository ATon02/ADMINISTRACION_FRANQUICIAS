package co.com.prueba.r2dbc.adapter;

import co.com.prueba.model.franchise.Franchise;
import co.com.prueba.model.franchise.gateways.FranchiseRepository;
import co.com.prueba.r2dbc.entity.FranchiseEntity;
import co.com.prueba.r2dbc.helper.ReactiveAdapterOperations;
import co.com.prueba.r2dbc.repository.FranchiseReactiveRepository;
import reactor.core.publisher.Mono;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FranchiseReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    Franchise,
    FranchiseEntity,
    Long,
    FranchiseReactiveRepository
> implements FranchiseRepository {
    public FranchiseReactiveRepositoryAdapter(FranchiseReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Franchise.class/* change for domain model */));
    }


    @Override
    public Mono<Franchise> findByName(String name) {
        return repository.findByName(name).map(this::toEntity);
    }

}
