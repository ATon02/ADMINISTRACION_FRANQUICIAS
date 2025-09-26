package co.com.prueba.usecase.branch;

import static org.mockito.ArgumentMatchers.any;
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
import co.com.prueba.usecase.exceptions.BusinessException;
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
    void save_WithNullFranchiseId_ShouldReturnError() {
        Branch branchWithNullFranchiseId = Branch.builder()
                .name("Valid Name")
                .franchiseId(null)
                .build();

        when(franchiseRepository.findById(null)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.save(branchWithNullFranchiseId))
                .expectErrorMatches(throwable -> 
                    throwable instanceof BusinessException && 
                    throwable.getMessage().equals("Franchise with id null not found"))
                .verify();

        verify(franchiseRepository).findById(null);
        verify(branchRepository, never()).save(any());
    }

    @Test
    void save_WithZeroFranchiseId_ShouldReturnError() {
        Branch branchWithZeroFranchiseId = Branch.builder()
                .name("Valid Name")
                .franchiseId(0L)
                .build();

        when(franchiseRepository.findById(0L)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.save(branchWithZeroFranchiseId))
                .expectErrorMatches(throwable -> 
                    throwable instanceof BusinessException && 
                    throwable.getMessage().equals("Franchise with id 0 not found"))
                .verify();

        verify(franchiseRepository).findById(0L);
        verify(branchRepository, never()).save(any());
    }

    @Test
    void save_WithNegativeFranchiseId_ShouldReturnError() {
        Branch branchWithNegativeFranchiseId = Branch.builder()
                .name("Valid Name")
                .franchiseId(-1L)
                .build();

        when(franchiseRepository.findById(-1L)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.save(branchWithNegativeFranchiseId))
                .expectErrorMatches(throwable -> 
                    throwable instanceof BusinessException && 
                    throwable.getMessage().equals("Franchise with id -1 not found"))
                .verify();

        verify(franchiseRepository).findById(-1L);
        verify(branchRepository, never()).save(any());
    }

    @Test
    void save_WithNonExistentFranchise_ShouldReturnError() {
        when(franchiseRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.save(validBranch))
                .expectErrorMatches(throwable -> 
                    throwable instanceof BusinessException && 
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
        when(branchRepository.findById(null)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.updateName(null, "Valid Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof BusinessException && 
                    throwable.getMessage().equals("Branch with id null not found"))
                .verify();

        verify(branchRepository).findById(null);
        verify(branchRepository, never()).save(any());
    }

    @Test
    void updateName_WithZeroId_ShouldReturnError() {
        when(branchRepository.findById(0L)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.updateName(0L, "Valid Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof BusinessException && 
                    throwable.getMessage().equals("Branch with id 0 not found"))
                .verify();

        verify(branchRepository).findById(0L);
        verify(branchRepository, never()).save(any());
    }

    @Test
    void updateName_WithNegativeId_ShouldReturnError() {
        when(branchRepository.findById(-1L)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.updateName(-1L, "Valid Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof BusinessException && 
                    throwable.getMessage().equals("Branch with id -1 not found"))
                .verify();

        verify(branchRepository).findById(-1L);
        verify(branchRepository, never()).save(any());
    }

    @Test
    void updateName_WithNullName_ShouldThrowError() {
        when(branchRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.updateName(1L, null))
                .expectErrorMatches(throwable -> 
                    throwable instanceof BusinessException && 
                    throwable.getMessage().equals("Branch with id 1 not found"))
                .verify();

        verify(branchRepository).findById(1L);
        verify(branchRepository, never()).save(any());
    }

    @Test
    void updateName_WithBlankName_ShouldThrowError() {
        when(branchRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.updateName(1L, ""))
                .expectErrorMatches(throwable -> 
                    throwable instanceof BusinessException && 
                    throwable.getMessage().equals("Branch with id 1 not found"))
                .verify();

        verify(branchRepository).findById(1L);
        verify(branchRepository, never()).save(any());
    }

    @Test
    void updateName_WithWhitespaceOnlyName_ShouldUpdateSuccessfully() {
        when(branchRepository.findById(1L)).thenReturn(Mono.just(validBranch));
        when(branchRepository.save(any(Branch.class))).thenReturn(Mono.just(validBranch));

        StepVerifier.create(branchUseCase.updateName(1L, "   "))
                .expectNext(validBranch)
                .verifyComplete();

        verify(branchRepository).findById(1L);
        verify(branchRepository).save(any(Branch.class));
    }

    @Test
    void updateName_WithNonExistentBranch_ShouldReturnError() {
        when(branchRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.updateName(1L, "Valid Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof BusinessException && 
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
