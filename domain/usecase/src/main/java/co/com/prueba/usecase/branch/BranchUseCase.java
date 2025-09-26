package co.com.prueba.usecase.branch;

import co.com.prueba.model.branch.Branch;
import co.com.prueba.model.branch.gateways.BranchRepository;
import co.com.prueba.model.franchise.gateways.FranchiseRepository;
import co.com.prueba.usecase.enums.ErrorMessages;
import co.com.prueba.usecase.exceptions.BusinessException;
import co.com.prueba.usecase.exceptions.TechnicalException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase implements IBranchUseCase{
    private final BranchRepository branchRepository;
    private final FranchiseRepository franchiseRepository;

    @Override
    public Mono<Branch> save(Branch branch) {
        return this.franchiseRepository.findById(branch.getFranchiseId())
            .flatMap(franchiseExist -> 
                this.branchRepository.save(branch)
                    .switchIfEmpty(Mono.error(new TechnicalException(ErrorMessages.BRANCH_NOT_CREATED.getMessage())))
            )
            .switchIfEmpty(Mono.error(new BusinessException(ErrorMessages.FRANCHISE_NOT_FOUND.getFormattedMessage(branch.getFranchiseId()))));
    }


    @Override
    public Mono<Branch> updateName(Long id, String name) {
        return this.branchRepository.findById(id)
            .flatMap(branch -> {
                branch.setName(name);
                return this.branchRepository.save(branch)
                    .switchIfEmpty(Mono.error(new TechnicalException(ErrorMessages.BRANCH_NOT_UPDATED.getMessage())));
            })
            .switchIfEmpty(Mono.error(new BusinessException(ErrorMessages.BRANCH_NOT_FOUND.getFormattedMessage(id))));
    }

}
