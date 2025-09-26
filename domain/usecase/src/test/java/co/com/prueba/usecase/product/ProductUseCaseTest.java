package co.com.prueba.usecase.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import co.com.prueba.model.branchproduct.BranchProduct;
import co.com.prueba.model.branchproduct.gateways.BranchProductRepository;
import co.com.prueba.model.franchise.Franchise;
import co.com.prueba.model.franchise.gateways.FranchiseRepository;
import co.com.prueba.model.product.Product;
import co.com.prueba.model.product.gateways.ProductRepository;
import co.com.prueba.usecase.exceptions.BusinessException;
import co.com.prueba.usecase.exceptions.TechnicalException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ProductUseCaseTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private FranchiseRepository franchiseRepository;

    @Mock
    private BranchProductRepository branchProductRepository;

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
    void save_ShouldThrowBusinessException_WhenBranchNotFound() {
        Product newProduct = Product.builder()
                .name("Big Mac")
                .stock(100L)
                .branchId(999L)
                .build();

        when(branchRepository.findById(999L)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.save(newProduct))
                .expectError(BusinessException.class)
                .verify();

        verify(branchRepository).findById(999L);
        verify(productRepository, never()).save(any());
    }

    @Test
    void save_ShouldThrowTechnicalException_WhenSaveFails() {
        Product newProduct = Product.builder()
                .name("Big Mac")
                .stock(100L)
                .branchId(1L)
                .build();

        when(branchRepository.findById(1L)).thenReturn(Mono.just(branch));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.save(newProduct))
                .expectError(TechnicalException.class)
                .verify();

        verify(branchRepository).findById(1L);
        verify(productRepository).save(newProduct);
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
    void delete_ShouldThrowBusinessException_WhenProductNotFound() {
        when(productRepository.findById(999L)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.delete(999L))
                .expectError(BusinessException.class)
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
    void updateName_ShouldThrowBusinessException_WhenProductNotFound() {
        when(productRepository.findById(999L)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.updateName(999L, "Quarter Pounder"))
                .expectError(BusinessException.class)
                .verify();

        verify(productRepository).findById(999L);
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateName_ShouldThrowTechnicalException_WhenUpdateFails() {
        when(productRepository.findById(1L)).thenReturn(Mono.just(product));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.updateName(1L, "Quarter Pounder"))
                .expectError(TechnicalException.class)
                .verify();

        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
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
    void updateStock_ShouldThrowBusinessException_WhenProductNotFound() {
        when(productRepository.findById(999L)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.updateStock(999L, 200L))
                .expectError(BusinessException.class)
                .verify();

        verify(productRepository).findById(999L);
        verify(productRepository, never()).save(any());
    }

    @Test
    void updateStock_ShouldThrowTechnicalException_WhenUpdateFails() {
        when(productRepository.findById(1L)).thenReturn(Mono.just(product));
        when(productRepository.save(any(Product.class))).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.updateStock(1L, 200L))
                .expectError(TechnicalException.class)
                .verify();

        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getProductMaxStock_ShouldReturnProductsMaxStock_WhenValidFranchiseId() {
        BranchProduct branchProduct1 = BranchProduct.builder()
                .branchName("Sucursal Centro")
                .productId(1L)
                .productName("Big Mac")
                .productStock(100L)
                .build();
        
        BranchProduct branchProduct2 = BranchProduct.builder()
                .branchName("Sucursal Norte")
                .productId(2L)
                .productName("Quarter Pounder")
                .productStock(150L)
                .build();

        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(franchise));
        when(branchProductRepository.findProductsWithMaxStockByFranchiseId(1L))
                .thenReturn(Flux.just(branchProduct1, branchProduct2));

        StepVerifier.create(productUseCase.getProductMaxStock(1L))
                .expectNext(branchProduct1)
                .expectNext(branchProduct2)
                .verifyComplete();

        verify(franchiseRepository).findById(1L);
        verify(branchProductRepository).findProductsWithMaxStockByFranchiseId(1L);
    }

    @Test
    void getProductMaxStock_ShouldThrowBusinessException_WhenFranchiseNotFound() {
        when(franchiseRepository.findById(999L)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.getProductMaxStock(999L))
                .expectError(BusinessException.class)
                .verify();

        verify(franchiseRepository).findById(999L);
        verify(branchProductRepository, never()).findProductsWithMaxStockByFranchiseId(anyLong());
    }

    @Test
    void getProductMaxStock_ShouldThrowBusinessException_WhenNoProductsFound() {
        when(franchiseRepository.findById(1L)).thenReturn(Mono.just(franchise));
        when(branchProductRepository.findProductsWithMaxStockByFranchiseId(1L))
                .thenReturn(Flux.empty());

        StepVerifier.create(productUseCase.getProductMaxStock(1L))
                .expectError(BusinessException.class)
                .verify();

        verify(franchiseRepository).findById(1L);
        verify(branchProductRepository).findProductsWithMaxStockByFranchiseId(1L);
    }
}
