package co.com.prueba.usecase.branch;

import co.com.prueba.model.branch.Branch;
import reactor.core.publisher.Mono;

public interface IBranchUseCase{
    Mono<Branch> save(Branch branch);
    Mono<Branch> updateName(Long id, String name);

}
