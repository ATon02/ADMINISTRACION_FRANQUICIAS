package co.com.prueba.usecase.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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
import co.com.prueba.model.product.Product;
import co.com.prueba.model.product.gateways.ProductRepository;
import co.com.prueba.usecase.dtos.DTOBranchProduct;
import co.com.prueba.usecase.exceptions.NotFoundException;
import co.com.prueba.usecase.exceptions.NotValidFieldException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private ProductUseCase productUseCase;

    private Product product;
    private Branch branch;
    private Franchise franchise;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .name("Big Mac")
                .stock(100L)
                .branchId(1L)
                .build();

        branch = Branch.builder()
                .id(1L)
                .name("Sucursal Centro")
                .franchiseId(1L)
                .build();

        franchise = Franchise.builder()
                .id(1L)
                .name("McDonald's")
                .build();
    }

    @Test
    void save_ShouldCreateProduct_WhenValidProduct() {
        Product newProduct = Product.builder()
                .name("Big Mac")
                .stock(100L)
                .branchId(1L)
                .build();

        when(branchRepository.findById(1L)).thenReturn(Mono.just(branch));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(product));

        StepVerifier.create(productUseCase.save(newProduct))
                .expectNext(product)
                .verifyComplete();

        verify(branchRepository).findById(1L);
        verify(productRepository).save(newProduct);
    }

    @Test
    void save_ShouldThrowNotValidFieldException_WhenProductNameIsNull() {
        Product productWithNullName = Product.builder()
                .name(null)
                .stock(100L)
                .branchId(1L)
                .build();

        StepVerifier.create(productUseCase.save(productWithNullName))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void save_ShouldThrowNotValidFieldException_WhenProductNameIsBlank() {
        Product productWithBlankName = Product.builder()
                .name("   ")
                .stock(100L)
                .branchId(1L)
                .build();

        StepVerifier.create(productUseCase.save(productWithBlankName))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void save_ShouldThrowNotValidFieldException_WhenProductNameIsEmpty() {
        Product productWithEmptyName = Product.builder()
                .name("")
                .stock(100L)
                .branchId(1L)
                .build();

        StepVerifier.create(productUseCase.save(productWithEmptyName))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void save_ShouldThrowNotValidFieldException_WhenStockIsNull() {
        Product productWithNullStock = Product.builder()
                .name("Big Mac")
                .stock(null)
                .branchId(1L)
                .build();

        StepVerifier.create(productUseCase.save(productWithNullStock))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void save_ShouldThrowNotValidFieldException_WhenStockIsZero() {
        Product productWithZeroStock = Product.builder()
                .name("Big Mac")
                .stock(0L)
                .branchId(1L)
                .build();

        StepVerifier.create(productUseCase.save(productWithZeroStock))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void save_ShouldThrowNotValidFieldException_WhenStockIsNegative() {
        Product productWithNegativeStock = Product.builder()
                .name("Big Mac")
                .stock(-10L)
                .branchId(1L)
                .build();

        StepVerifier.create(productUseCase.save(productWithNegativeStock))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void save_ShouldThrowNotValidFieldException_WhenBranchIdIsNull() {
        Product productWithNullBranchId = Product.builder()
                .name("Big Mac")
                .stock(100L)
                .branchId(null)
                .build();

        StepVerifier.create(productUseCase.save(productWithNullBranchId))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void save_ShouldThrowNotValidFieldException_WhenBranchIdIsZero() {
        Product productWithZeroBranchId = Product.builder()
                .name("Big Mac")
                .stock(100L)
                .branchId(0L)
                .build();

        StepVerifier.create(productUseCase.save(productWithZeroBranchId))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void save_ShouldThrowNotValidFieldException_WhenBranchIdIsNegative() {
        Product productWithNegativeBranchId = Product.builder()
                .name("Big Mac")
                .stock(100L)
                .branchId(-1L)
                .build();

        StepVerifier.create(productUseCase.save(productWithNegativeBranchId))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(branchRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void save_ShouldThrowNotFoundException_WhenBranchNotFound() {
        Product newProduct = Product.builder()
                .name("Big Mac")
                .stock(100L)
                .branchId(999L)
                .build();

        when(branchRepository.findById(999L)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.save(newProduct))
                .expectError(NotFoundException.class)
                .verify();

        verify(branchRepository).findById(999L);
        verify(productRepository, never()).save(any());
    }

    @Test
    void delete_ShouldDeleteProduct_WhenProductExists() {
        when(productRepository.findById(1L)).thenReturn(Mono.just(product));
        when(productRepository.delete(1L)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.delete(1L))
                .verifyComplete();

        verify(productRepository).findById(1L);
        verify(productRepository).delete(1L);
    }

    @Test
    void delete_ShouldThrowNotValidFieldException_WhenIdIsNull() {
        StepVerifier.create(productUseCase.delete(null))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).delete(anyLong());
    }

    @Test
    void delete_ShouldThrowNotValidFieldException_WhenIdIsZero() {
        StepVerifier.create(productUseCase.delete(0L))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).delete(anyLong());
    }

    @Test
    void delete_ShouldThrowNotValidFieldException_WhenIdIsNegative() {
        StepVerifier.create(productUseCase.delete(-1L))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).delete(anyLong());
    }

    @Test
    void delete_ShouldThrowNotFoundException_WhenProductNotFound() {
        when(productRepository.findById(999L)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.delete(999L))
                .expectError(NotFoundException.class)
                .verify();

        verify(productRepository).findById(999L);
        verify(productRepository, never()).delete(anyLong());
    }

    @Test
    void updateName_ShouldUpdateProductName_WhenValidData() {
        Product updatedProduct = product.toBuilder().name("Quarter Pounder").build();
        
        when(productRepository.findById(1L)).thenReturn(Mono.just(product));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(updatedProduct));

        StepVerifier.create(productUseCase.updateName(1L, "Quarter Pounder"))
                .expectNext(updatedProduct)
                .verifyComplete();

        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateName_ShouldThrowNotValidFieldException_WhenIdIsNull() {
        StepVerifier.create(productUseCase.updateName(null, "Quarter Pounder"))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateName_ShouldThrowNotValidFieldException_WhenIdIsZero() {
        StepVerifier.create(productUseCase.updateName(0L, "Quarter Pounder"))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateName_ShouldThrowNotValidFieldException_WhenIdIsNegative() {
        StepVerifier.create(productUseCase.updateName(-1L, "Quarter Pounder"))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateName_ShouldThrowNotValidFieldException_WhenNameIsNull() {
        StepVerifier.create(productUseCase.updateName(1L, null))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateName_ShouldThrowNotValidFieldException_WhenNameIsBlank() {
        StepVerifier.create(productUseCase.updateName(1L, "   "))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateName_ShouldThrowNotValidFieldException_WhenNameIsEmpty() {
        StepVerifier.create(productUseCase.updateName(1L, ""))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateName_ShouldThrowNotFoundException_WhenProductNotFound() {
        when(productRepository.findById(999L)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.updateName(999L, "Quarter Pounder"))
                .expectError(NotFoundException.class)
                .verify();

        verify(productRepository).findById(999L);
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateStock_ShouldUpdateProductStock_WhenValidData() {
        Product updatedProduct = product.toBuilder().stock(200L).build();
        
        when(productRepository.findById(1L)).thenReturn(Mono.just(product));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(updatedProduct));

        StepVerifier.create(productUseCase.updateStock(1L, 200L))
                .expectNext(updatedProduct)
                .verifyComplete();

        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateStock_ShouldThrowNotValidFieldException_WhenIdIsNull() {
        StepVerifier.create(productUseCase.updateStock(null, 200L))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateStock_ShouldThrowNotValidFieldException_WhenIdIsZero() {
        StepVerifier.create(productUseCase.updateStock(0L, 200L))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateStock_ShouldThrowNotValidFieldException_WhenIdIsNegative() {
        StepVerifier.create(productUseCase.updateStock(-1L, 200L))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateStock_ShouldThrowNotValidFieldException_WhenStockIsNull() {
        StepVerifier.create(productUseCase.updateStock(1L, null))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateStock_ShouldThrowNotValidFieldException_WhenStockIsZero() {
        StepVerifier.create(productUseCase.updateStock(1L, 0L))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateStock_ShouldThrowNotValidFieldException_WhenStockIsNegative() {
        StepVerifier.create(productUseCase.updateStock(1L, -10L))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateStock_ShouldThrowNotFoundException_WhenProductNotFound() {
        when(productRepository.findById(999L)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.updateStock(999L, 200L))
                .expectError(NotFoundException.class)
                .verify();

        verify(productRepository).findById(999L);
        verify(productRepository, never()).save(any());
    }

    @Test
    void getProductMaxStock_ShouldReturnProductsMaxStock_WhenValidFranchiseId() {
        Branch branch1 = Branch.builder().id(1L).name("Sucursal Centro").franchiseId(1L).build();
        Branch branch2 = Branch.builder().id(2L).name("Sucursal Norte").franchiseId(1L).build();
        
        Product product1 = Product.builder().id(1L).name("Big Mac").stock(100L).branchId(1L).build();
        Product product2 = Product.builder().id(2L).name("Quarter Pounder").stock(150L).branchId(2L).build();

        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(franchise));
        when(branchRepository.findAllByFranchiseId(1L)).thenReturn(Flux.just(branch1, branch2));
        when(productRepository.findProductWithMaxStockByBranchId(1L)).thenReturn(Mono.just(product1));
        when(productRepository.findProductWithMaxStockByBranchId(2L)).thenReturn(Mono.just(product2));

        StepVerifier.create(productUseCase.getProductMaxStock(1L))
                .assertNext(result -> {
                    assertEquals(2, result.size());
                    assertEquals("Sucursal Centro", result.get(0).getBranchName());
                    assertEquals("Big Mac", result.get(0).getProduct().getName());
                    assertEquals("Sucursal Norte", result.get(1).getBranchName());
                    assertEquals("Quarter Pounder", result.get(1).getProduct().getName());
                })
                .verifyComplete();

        verify(franchiseRepository).findById(1L);
        verify(branchRepository).findAllByFranchiseId(1L);
        verify(productRepository).findProductWithMaxStockByBranchId(1L);
        verify(productRepository).findProductWithMaxStockByBranchId(2L);
    }

    @Test
    void getProductMaxStock_ShouldReturnEmptyList_WhenBranchHasNoProducts() {
        Branch branch1 = Branch.builder().id(1L).name("Sucursal Centro").franchiseId(1L).build();

        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(franchise));
        when(branchRepository.findAllByFranchiseId(1L)).thenReturn(Flux.just(branch1));
        when(productRepository.findProductWithMaxStockByBranchId(1L)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.getProductMaxStock(1L))
                .assertNext(result -> {
                    assertEquals(0, result.size());
                })
                .verifyComplete();

        verify(franchiseRepository).findById(1L);
        verify(branchRepository).findAllByFranchiseId(1L);
        verify(productRepository).findProductWithMaxStockByBranchId(1L);
    }

    @Test
    void getProductMaxStock_ShouldThrowNotValidFieldException_WhenFranchiseIdIsNull() {
        StepVerifier.create(productUseCase.getProductMaxStock(null))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(franchiseRepository, never()).findById(anyLong());
        verify(branchRepository, never()).findAllByFranchiseId(anyLong());
    }

    @Test
    void getProductMaxStock_ShouldThrowNotValidFieldException_WhenFranchiseIdIsZero() {
        StepVerifier.create(productUseCase.getProductMaxStock(0L))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(franchiseRepository, never()).findById(anyLong());
        verify(branchRepository, never()).findAllByFranchiseId(anyLong());
    }

    @Test
    void getProductMaxStock_ShouldThrowNotValidFieldException_WhenFranchiseIdIsNegative() {
        StepVerifier.create(productUseCase.getProductMaxStock(-1L))
                .expectError(NotValidFieldException.class)
                .verify();

        verify(franchiseRepository, never()).findById(anyLong());
        verify(branchRepository, never()).findAllByFranchiseId(anyLong());
    }

    @Test
    void getProductMaxStock_ShouldThrowNotFoundException_WhenFranchiseNotFound() {
        when(franchiseRepository.findById(999L)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.getProductMaxStock(999L))
                .expectError(NotFoundException.class)
                .verify();

        verify(franchiseRepository).findById(999L);
        verify(branchRepository, never()).findAllByFranchiseId(anyLong());
    }

    @Test
    void getProductMaxStock_ShouldThrowNotFoundException_WhenFranchiseHasNoBranches() {
        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(franchise));
        when(branchRepository.findAllByFranchiseId(1L)).thenReturn(Flux.empty());

        StepVerifier.create(productUseCase.getProductMaxStock(1L))
                .expectError(NotFoundException.class)
                .verify();

        verify(franchiseRepository).findById(1L);
        verify(branchRepository).findAllByFranchiseId(1L);
    }
}
