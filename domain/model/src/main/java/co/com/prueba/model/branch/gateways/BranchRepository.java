package co.com.prueba.model.branch.gateways;

import co.com.prueba.model.branch.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepository {
    Mono<Branch> save(Branch branch);
    Mono<Branch> findById(Long id);
    Mono<Branch> findByFranchiseId(Long franchiseId);
    Flux<Branch> findAllByFranchiseId(Long franchiseId);
}
