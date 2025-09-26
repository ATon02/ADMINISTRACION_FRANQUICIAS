package co.com.prueba.usecase.franchise;

import co.com.prueba.model.franchise.Franchise;
import co.com.prueba.model.franchise.gateways.FranchiseRepository;
import co.com.prueba.usecase.enums.ErrorMessages;
import co.com.prueba.usecase.exceptions.BusinessException;
import co.com.prueba.usecase.exceptions.TechnicalException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
public class FranchiseUseCase implements IFranchiseUseCase {

    private final FranchiseRepository franchiseRepository;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return franchiseRepository.findByName(franchise.getName())
            .flatMap(exist -> Mono.<Franchise>error(
                new BusinessException(ErrorMessages.FRANCHISE_NAME_EXISTS.getMessage()))
            )
            .switchIfEmpty(
                Mono.defer(() -> 
                    franchiseRepository.save(franchise)
                        .switchIfEmpty(Mono.error(new TechnicalException(ErrorMessages.FRANCHISE_NOT_CREATED.getMessage())))
                )
            );    
    }

    @Override
    public Mono<Franchise> updateName(Long id, String name) {
        return franchiseRepository.findById(id)
            .switchIfEmpty(Mono.error(new BusinessException(ErrorMessages.FRANCHISE_NOT_FOUND.getFormattedMessage(id))))
            .flatMap(existingFranchise ->
                franchiseRepository.findByName(name)
                    .flatMap(franchiseByName -> {
                        if (!franchiseByName.getId().equals(id)) {
                            return Mono.error(new BusinessException(ErrorMessages.FRANCHISE_NAME_EXISTS.getMessage()));
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
            .switchIfEmpty(Mono.error(new TechnicalException(ErrorMessages.FRANCHISE_NOT_UPDATED.getMessage())));
    }

}
