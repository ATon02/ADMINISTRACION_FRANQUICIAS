package co.com.prueba.usecase.franchise;

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

import co.com.prueba.model.franchise.Franchise;
import co.com.prueba.model.franchise.gateways.FranchiseRepository;
import co.com.prueba.usecase.exceptions.NotFoundException;
import co.com.prueba.usecase.exceptions.NotValidFieldException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class FranchiseUseCaseTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private FranchiseUseCase franchiseUseCase;

    private Franchise validFranchise;

    @BeforeEach
    void setUp() {
        validFranchise = Franchise.builder()
                .id(1L)
                .name("Test Franchise")
                .build();
    }

    @Test
    void save_WithValidFranchise_ShouldSaveFranchise() {
        Franchise newFranchise = Franchise.builder()
                .name("New Franchise")
                .build();

        when(franchiseRepository.findByName("New Franchise")).thenReturn(Mono.empty());
        when(franchiseRepository.save(newFranchise)).thenReturn(Mono.just(newFranchise));

        StepVerifier.create(franchiseUseCase.save(newFranchise))
                .expectNext(newFranchise)
                .verifyComplete();

        verify(franchiseRepository).findByName("New Franchise");
        verify(franchiseRepository).save(newFranchise);
    }

    @Test
    void save_WithBlankName_ShouldReturnError() {
        Franchise franchiseWithBlankName = Franchise.builder()
                .name("")
                .build();

        StepVerifier.create(franchiseUseCase.save(franchiseWithBlankName))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Franchise name cannot be blank"))
                .verify();

        verify(franchiseRepository, never()).findByName(anyString());
        verify(franchiseRepository, never()).save(any());
    }

    @Test
    void save_WithNullName_ShouldReturnError() {
        Franchise franchiseWithNullName = Franchise.builder()
                .name(null)
                .build();

        StepVerifier.create(franchiseUseCase.save(franchiseWithNullName))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Franchise name cannot be blank"))
                .verify();

        verify(franchiseRepository, never()).findByName(anyString());
        verify(franchiseRepository, never()).save(any());
    }

    @Test
    void save_WithWhitespaceOnlyName_ShouldReturnError() {
        Franchise franchiseWithWhitespaceName = Franchise.builder()
                .name("   ")
                .build();

        StepVerifier.create(franchiseUseCase.save(franchiseWithWhitespaceName))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Franchise name cannot be blank"))
                .verify();

        verify(franchiseRepository, never()).findByName(anyString());
        verify(franchiseRepository, never()).save(any());
    }

    @Test
    void save_WithExistingName_ShouldReturnError() {
        Franchise existingFranchise = Franchise.builder()
                .id(2L)
                .name("Existing Franchise")
                .build();

        Franchise newFranchiseWithSameName = Franchise.builder()
                .name("Existing Franchise")
                .build();

        when(franchiseRepository.findByName("Existing Franchise"))
                .thenReturn(Mono.just(existingFranchise));

        StepVerifier.create(franchiseUseCase.save(newFranchiseWithSameName))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Franchise name already exists"))
                .verify();

        verify(franchiseRepository).findByName("Existing Franchise");
        verify(franchiseRepository, never()).save(any());
    }

    @Test
    void save_WhenSaveFails_ShouldReturnError() {
        Franchise newFranchise = Franchise.builder()
                .name("New Franchise")
                .build();

        when(franchiseRepository.findByName("New Franchise")).thenReturn(Mono.empty());
        when(franchiseRepository.save(newFranchise)).thenReturn(Mono.empty());

        StepVerifier.create(franchiseUseCase.save(newFranchise))
                .expectErrorMatches(throwable -> 
                    throwable instanceof Exception && 
                    throwable.getMessage().equals("Franchise not created"))
                .verify();

        verify(franchiseRepository).findByName("New Franchise");
        verify(franchiseRepository).save(newFranchise);
    }

    @Test
    void updateName_WithValidData_ShouldUpdateFranchise() {
        String newName = "Updated Franchise Name";
        Franchise existingFranchise = Franchise.builder()
                .id(1L)
                .name("Old Name")
                .build();

        Franchise updatedFranchise = Franchise.builder()
                .id(1L)
                .name(newName)
                .build();

        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(existingFranchise));
        when(franchiseRepository.findByName(newName)).thenReturn(Mono.empty());
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(Mono.just(updatedFranchise));

        StepVerifier.create(franchiseUseCase.updateName(1L, newName))
                .expectNext(updatedFranchise)
                .verifyComplete();

        verify(franchiseRepository).findById(1L);
        verify(franchiseRepository).findByName(newName);
        verify(franchiseRepository).save(any(Franchise.class));
    }

    @Test
    void updateName_WithSameNameForSameFranchise_ShouldUpdateFranchise() {
        String sameName = "Same Name";
        Franchise existingFranchise = Franchise.builder()
                .id(1L)
                .name("Old Name")
                .build();

        Franchise franchiseWithSameName = Franchise.builder()
                .id(1L)
                .name(sameName)
                .build();

        Franchise updatedFranchise = Franchise.builder()
                .id(1L)
                .name(sameName)
                .build();

        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(existingFranchise));
        when(franchiseRepository.findByName(sameName)).thenReturn(Mono.just(franchiseWithSameName));
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(Mono.just(updatedFranchise));

        StepVerifier.create(franchiseUseCase.updateName(1L, sameName))
                .expectNext(updatedFranchise)
                .verifyComplete();

        verify(franchiseRepository).findById(1L);
        verify(franchiseRepository).findByName(sameName);
        verify(franchiseRepository).save(any(Franchise.class));
    }

    @Test
    void updateName_WithNullId_ShouldReturnError() {
        StepVerifier.create(franchiseUseCase.updateName(null, "Valid Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Franchise ID cannot be 0 or negative"))
                .verify();

        verify(franchiseRepository, never()).findById(anyLong());
        verify(franchiseRepository, never()).findByName(anyString());
        verify(franchiseRepository, never()).save(any());
    }

    @Test
    void updateName_WithZeroId_ShouldReturnError() {
        StepVerifier.create(franchiseUseCase.updateName(0L, "Valid Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Franchise ID cannot be 0 or negative"))
                .verify();

        verify(franchiseRepository, never()).findById(anyLong());
        verify(franchiseRepository, never()).findByName(anyString());
        verify(franchiseRepository, never()).save(any());
    }

    @Test
    void updateName_WithNegativeId_ShouldReturnError() {
        StepVerifier.create(franchiseUseCase.updateName(-1L, "Valid Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Franchise ID cannot be 0 or negative"))
                .verify();

        verify(franchiseRepository, never()).findById(anyLong());
        verify(franchiseRepository, never()).findByName(anyString());
        verify(franchiseRepository, never()).save(any());
    }

    @Test
    void updateName_WithNullName_ShouldReturnError() {
        StepVerifier.create(franchiseUseCase.updateName(1L, null))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Franchise name cannot be blank"))
                .verify();

        verify(franchiseRepository, never()).findById(anyLong());
        verify(franchiseRepository, never()).findByName(anyString());
        verify(franchiseRepository, never()).save(any());
    }

    @Test
    void updateName_WithBlankName_ShouldReturnError() {
        StepVerifier.create(franchiseUseCase.updateName(1L, ""))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Franchise name cannot be blank"))
                .verify();

        verify(franchiseRepository, never()).findById(anyLong());
        verify(franchiseRepository, never()).findByName(anyString());
        verify(franchiseRepository, never()).save(any());
    }

    @Test
    void updateName_WithWhitespaceOnlyName_ShouldReturnError() {
        StepVerifier.create(franchiseUseCase.updateName(1L, "   "))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Franchise name cannot be blank"))
                .verify();

        verify(franchiseRepository, never()).findById(anyLong());
        verify(franchiseRepository, never()).findByName(anyString());
        verify(franchiseRepository, never()).save(any());
    }

    @Test
    void updateName_WithNonExistentFranchise_ShouldReturnError() {
        when(franchiseRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(franchiseUseCase.updateName(1L, "Valid Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotFoundException && 
                    throwable.getMessage().equals("Franchise with id 1 not found"))
                .verify();

        verify(franchiseRepository).findById(1L);
        verify(franchiseRepository, never()).findByName(anyString());
        verify(franchiseRepository, never()).save(any());
    }

    @Test
    void updateName_WithExistingNameForDifferentFranchise_ShouldReturnError() {
        Franchise existingFranchise = Franchise.builder()
                .id(1L)
                .name("Old Name")
                .build();

        Franchise franchiseWithSameName = Franchise.builder()
                .id(2L)  
                .name("Existing Name")
                .build();

        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(existingFranchise));
        when(franchiseRepository.findByName("Existing Name"))
                .thenReturn(Mono.just(franchiseWithSameName));

        StepVerifier.create(franchiseUseCase.updateName(1L, "Existing Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotValidFieldException && 
                    throwable.getMessage().equals("Franchise name already exists"))
                .verify();

        verify(franchiseRepository).findById(1L);
        verify(franchiseRepository).findByName("Existing Name");
        verify(franchiseRepository, never()).save(any());
    }

    @Test
    void updateName_WhenUpdateFails_ShouldReturnError() {
        Franchise existingFranchise = Franchise.builder()
                .id(1L)
                .name("Old Name")
                .build();

        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(existingFranchise));
        when(franchiseRepository.findByName("New Name")).thenReturn(Mono.empty());
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(Mono.empty());

        StepVerifier.create(franchiseUseCase.updateName(1L, "New Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof Exception && 
                    throwable.getMessage().equals("Franchise not updated"))
                .verify();

        verify(franchiseRepository).findById(1L);
        verify(franchiseRepository).findByName("New Name");
        verify(franchiseRepository).save(any(Franchise.class));
    }

    @Test
    void updateName_WhenUpdateFailsWithExistingSameName_ShouldReturnError() {
        Franchise existingFranchise = Franchise.builder()
                .id(1L)
                .name("Old Name")
                .build();

        Franchise franchiseWithSameName = Franchise.builder()
                .id(1L)
                .name("Same Name")
                .build();

        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(existingFranchise));
        when(franchiseRepository.findByName("Same Name")).thenReturn(Mono.just(franchiseWithSameName));
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(Mono.empty());

        StepVerifier.create(franchiseUseCase.updateName(1L, "Same Name"))
                .expectErrorMatches(throwable -> 
                    throwable instanceof Exception && 
                    throwable.getMessage().equals("Franchise not updated"))
                .verify();

        verify(franchiseRepository).findById(1L);
        verify(franchiseRepository).findByName("Same Name");
        verify(franchiseRepository).save(any(Franchise.class));
    }
}
