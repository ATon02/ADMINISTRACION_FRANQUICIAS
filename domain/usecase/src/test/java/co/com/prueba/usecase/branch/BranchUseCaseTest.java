package co.com.prueba.usecase.branch;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.prueba.model.branch.Branch;
import co.com.prueba.model.branch.gateways.BranchRepository;
import co.com.prueba.model.franchise.Franchise;
import co.com.prueba.model.franchise.gateways.FranchiseRepository;
import co.com.prueba.usecase.exceptions.NotFoundException;
import co.com.prueba.usecase.exceptions.NotValidFieldException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class BranchUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private BranchUseCase branchUseCase;

    private Branch validBranch;
    private Franchise validFranchise;

    @BeforeEach
    void setUp() {
        validBranch = Branch.builder()
                .id(1L)
                .name("Main Branch")
                .franchiseId(1L)
                .build();

        validFranchise = Franchise.builder()
                .id(1L)
                .name("Test Franchise")
                .build();
    }

    @Test
    void save_WithValidBranch_ShouldSaveBranch() {
        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(validFranchise));
        when(branchRepository.save(validBranch)).thenReturn(Mono.just(validBranch));

        StepVerifier.create(branchUseCase.save(validBranch))
                .expectNext(validBranch)
                .verifyComplete();

        verify(franchiseRepository).findById(1L);
        verify(branchRepository).save(validBranch);
    }

    @Test
    void save_WithBlankName_ShouldReturnError() {
        Branch branchWithBlankName = Branch.builder()
                .name("")
                .franchiseId(1L)
                .build();

        StepVerifier.create(branchUseCase.save(branchWithBlankName))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Branch name cannot be blank"))
                .verify();

        verify(franchiseRepository, never()).findById(anyLong());
        verify(branchRepository, never()).save(any());
    }

    @Test
    void save_WithNullName_ShouldReturnError() {
        Branch branchWithNullName = Branch.builder()
                .name(null)
                .franchiseId(1L)
                .build();

        StepVerifier.create(branchUseCase.save(branchWithNullName))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Branch name cannot be blank"))
                .verify();

        verify(franchiseRepository, never()).findById(anyLong());
        verify(branchRepository, never()).save(any());
    }

    @Test
    void save_WithNullFranchiseId_ShouldReturnError() {
        Branch branchWithNullFranchiseId = Branch.builder()
                .name("Valid Name")
                .franchiseId(null)
                .build();

        StepVerifier.create(branchUseCase.save(branchWithNullFranchiseId))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Branch franchise id cannot be 0"))
                .verify();

        verify(franchiseRepository, never()).findById(anyLong());
        verify(branchRepository, never()).save(any());
    }

    @Test
    void save_WithZeroFranchiseId_ShouldReturnError() {
        Branch branchWithZeroFranchiseId = Branch.builder()
                .name("Valid Name")
                .franchiseId(0L)
                .build();

        StepVerifier.create(branchUseCase.save(branchWithZeroFranchiseId))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Branch franchise id cannot be 0"))
                .verify();

        verify(franchiseRepository, never()).findById(anyLong());
        verify(branchRepository, never()).save(any());
    }

    @Test
    void save_WithNegativeFranchiseId_ShouldReturnError() {
        Branch branchWithNegativeFranchiseId = Branch.builder()
                .name("Valid Name")
                .franchiseId(-1L)
                .build();

        StepVerifier.create(branchUseCase.save(branchWithNegativeFranchiseId))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Branch franchise id cannot be 0"))
                .verify();

        verify(franchiseRepository, never()).findById(anyLong());
        verify(branchRepository, never()).save(any());
    }

    @Test
    void save_WithNonExistentFranchise_ShouldReturnError() {
        when(franchiseRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.save(validBranch))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotFoundException && 
                    throwable.getMessage().equals("Franchise with id 1 not found"))
                .verify();

        verify(franchiseRepository).findById(1L);
        verify(branchRepository, never()).save(any());
    }

    @Test
    void save_WhenBranchSaveFails_ShouldReturnError() {
        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(validFranchise));
        when(branchRepository.save(validBranch)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.save(validBranch))
                .expectErrorMatches(throwable -> 
                    throwable instanceof Exception && 
                    throwable.getMessage().equals("Branch not created"))
                .verify();

        verify(franchiseRepository).findById(1L);
        verify(branchRepository).save(validBranch);
    }

    @Test
    void updateName_WithValidData_ShouldUpdateBranch() {
        String newName = "Updated Branch Name";
        Branch existingBranch = Branch.builder()
                .id(1L)
                .name("Old Name")
                .franchiseId(1L)
                .build();

        Branch updatedBranch = Branch.builder()
                .id(1L)
                .name(newName)
                .franchiseId(1L)
                .build();

        when(branchRepository.findById(1L)).thenReturn(Mono.just(existingBranch));
        when(branchRepository.save(any(Branch.class))).thenReturn(Mono.just(updatedBranch));

        StepVerifier.create(branchUseCase.updateName(1L, newName))
                .expectNext(updatedBranch)
                .verifyComplete();

        verify(branchRepository).findById(1L);
        verify(branchRepository).save(any(Branch.class));
    }

    @Test
    void updateName_WithNullId_ShouldReturnError() {
        StepVerifier.create(branchUseCase.updateName(null, "Valid Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Branch Id cannot be 0"))
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(branchRepository, never()).save(any());
    }

    @Test
    void updateName_WithZeroId_ShouldReturnError() {
        StepVerifier.create(branchUseCase.updateName(0L, "Valid Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Branch Id cannot be 0"))
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(branchRepository, never()).save(any());
    }

    @Test
    void updateName_WithNegativeId_ShouldReturnError() {
        StepVerifier.create(branchUseCase.updateName(-1L, "Valid Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Branch Id cannot be 0"))
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(branchRepository, never()).save(any());
    }

    @Test
    void updateName_WithNullName_ShouldReturnError() {
        StepVerifier.create(branchUseCase.updateName(1L, null))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Branch name cannot be blank"))
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(branchRepository, never()).save(any());
    }

    @Test
    void updateName_WithBlankName_ShouldReturnError() {
        StepVerifier.create(branchUseCase.updateName(1L, ""))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Branch name cannot be blank"))
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(branchRepository, never()).save(any());
    }

    @Test
    void updateName_WithWhitespaceOnlyName_ShouldReturnError() {
        StepVerifier.create(branchUseCase.updateName(1L, "   "))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Branch name cannot be blank"))
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(branchRepository, never()).save(any());
    }

    @Test
    void updateName_WithNonExistentBranch_ShouldReturnError() {
        when(branchRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.updateName(1L, "Valid Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotFoundException && 
                    throwable.getMessage().equals("Branch with id 1 not found"))
                .verify();

        verify(branchRepository).findById(1L);
        verify(branchRepository, never()).save(any());
    }

    @Test
    void updateName_WhenSaveFails_ShouldReturnError() {
        Branch existingBranch = Branch.builder()
                .id(1L)
                .name("Old Name")
                .franchiseId(1L)
                .build();

        when(branchRepository.findById(1L)).thenReturn(Mono.just(existingBranch));
        when(branchRepository.save(any(Branch.class))).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.updateName(1L, "New Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof Exception && 
                    throwable.getMessage().equals("Branch not updated"))
                .verify();

        verify(branchRepository).findById(1L);
        verify(branchRepository).save(any(Branch.class));
    }
}
