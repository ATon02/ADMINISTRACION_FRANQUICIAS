package co.com.prueba.usecase.franchise;

import co.com.prueba.model.franchise.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchiseUseCase {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> updateName(Long id, String name);
}
