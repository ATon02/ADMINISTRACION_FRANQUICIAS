package co.com.prueba.usecase.franchise;

import co.com.prueba.model.franchise.Franchise;
import co.com.prueba.model.franchise.gateways.FranchiseRepository;
import co.com.prueba.usecase.exceptions.NotFoundException;
import co.com.prueba.usecase.exceptions.NotValidFieldException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
public class FranchiseUseCase implements IFranchiseUseCase {

    private final FranchiseRepository franchiseRepository;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        if (franchise.getName() == null || franchise.getName().isBlank()) {
            return Mono.error(new NotValidFieldException("Franchise name cannot be blank"));
        }

        return franchiseRepository.findByName(franchise.getName())
            .flatMap(exist -> Mono.<Franchise>error(
                new NotValidFieldException("Franchise name already exists"))
            )
            .switchIfEmpty(
                Mono.defer(() -> 
                    franchiseRepository.save(franchise)
                        .switchIfEmpty(Mono.error(new Exception("Franchise not created")))
                )
            );    
    }

    @Override
    public Mono<Franchise> updateName(Long id, String name) {
        if (id == null || id <= 0) {
            return Mono.error(new NotValidFieldException("Franchise ID cannot be 0 or negative"));
        }
        if (name == null || name.isBlank()) {
            return Mono.error(new NotValidFieldException("Franchise name cannot be blank"));
        }

        return franchiseRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("Franchise with id " + id + " not found")))
            .flatMap(existingFranchise ->
                franchiseRepository.findByName(name)
                    .flatMap(franchiseByName -> {
                        if (!franchiseByName.getId().equals(id)) {
                            return Mono.error(new NotValidFieldException("Franchise name already exists"));
                        }
                        existingFranchise.setName(name);
                        return updateMethod(existingFranchise);
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        existingFranchise.setName(name);
                        return updateMethod(existingFranchise);
                    }))
            );
    }

    private Mono<Franchise> updateMethod(Franchise franchise) {
        return franchiseRepository.save(franchise)
            .switchIfEmpty(Mono.error(new Exception("Franchise not updated")));
    }

}
