package co.com.prueba.usecase.branch;

import co.com.prueba.model.branch.Branch;
import co.com.prueba.model.branch.gateways.BranchRepository;
import co.com.prueba.model.franchise.gateways.FranchiseRepository;
import co.com.prueba.usecase.exceptions.NotFoundException;
import co.com.prueba.usecase.exceptions.NotValidFieldException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase implements IBranchUseCase{
    private final BranchRepository branchRepository;
    private final FranchiseRepository franchiseRepository;

    @Override
    public Mono<Branch> save(Branch branch) {
        if (branch.getName() == null || branch.getName().isBlank()) {
            return Mono.error(new NotValidFieldException("Branch name cannot be blank"));
        }
        if (branch.getFranchiseId()==null || branch.getFranchiseId()<=0) {
            return Mono.error(new NotValidFieldException("Branch franchise id cannot be 0"));
        }
        return this.franchiseRepository.findById(branch.getFranchiseId())
            .flatMap(franchiseExist -> 
                this.branchRepository.save(branch)
                    .switchIfEmpty(Mono.error(new Exception("Branch not created")))
            )
            .switchIfEmpty(Mono.error(new NotFoundException("Franchise with id "+ branch.getFranchiseId() +" not found")));
    }


    @Override
    public Mono<Branch> updateName(Long id, String name) {
        if (id == null || id <= 0) {
            return Mono.error(new NotValidFieldException("Branch Id cannot be 0"));
        }
        if (name == null || name.isBlank()) {
            return Mono.error(new NotValidFieldException("Branch name cannot be blank"));
        }
    
        return this.branchRepository.findById(id)
            .flatMap(branch -> {
                branch.setName(name);
                return this.branchRepository.save(branch)
                    .switchIfEmpty(Mono.error(new Exception("Branch not updated")));
            })
            .switchIfEmpty(Mono.error(new NotFoundException("Branch with id " + id + " not found")));
    }

}
