package co.com.prueba.model.franchise.gateways;

import co.com.prueba.model.franchise.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> findByName(String name);
    Mono<Franchise> findById(Long id);
}
