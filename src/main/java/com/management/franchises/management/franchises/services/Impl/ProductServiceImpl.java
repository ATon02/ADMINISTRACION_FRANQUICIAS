package com.management.franchises.management.franchises.services.Impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.franchises.management.franchises.exceptions.NotFoundException;
import com.management.franchises.management.franchises.exceptions.NotValidFieldException;
import com.management.franchises.management.franchises.models.Product;
import com.management.franchises.management.franchises.models.dtos.request.DTOProduct;
import com.management.franchises.management.franchises.models.dtos.response.DTOBranchProduct;
import com.management.franchises.management.franchises.models.dtos.response.DTOProductResponse;
import com.management.franchises.management.franchises.respositories.BranchRepository;
import com.management.franchises.management.franchises.respositories.FranchiseRepository;
import com.management.franchises.management.franchises.respositories.ProductRepository;
import com.management.franchises.management.franchises.services.ProductService;
import com.management.franchises.management.franchises.utils.ProductMapper;

import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FranchiseRepository franchiseRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Mono<DTOProductResponse> create(DTOProduct product) {
        if (product.getName().isBlank()) {
            return Mono.error(new NotValidFieldException("Product name cannot be blank"));
        }
        if (product.getStock() <= 0) {
            return Mono.error(new NotValidFieldException("Stock cannot be 0"));
        }
        if (product.getBranchId() == null || product.getBranchId() <= 0) {
            return Mono.error(new NotValidFieldException("Branch id cannot be 0"));
        }
        return this.branchRepository.findById(product.getBranchId())
                .flatMap(branchExist -> this.productRepository.save(productMapper.toEntity(product))
                        .map(productMapper::toDto)
                        .switchIfEmpty(Mono.error(new Exception("Product not created"))))
                .switchIfEmpty(
                        Mono.error(new NotFoundException("Branch with id " + product.getBranchId() + " not found")));
    }

    @Override
    public Mono<Void> delete(Long id) {
        if (id == null || id <= 0) {
            return Mono.error(new NotValidFieldException("Branch id cannot be 0"));
        }
        return this.productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product with id " + id + " not found")))
                .flatMap(product -> this.productRepository.delete(product));
    }

    @Override
    public Mono<List<DTOBranchProduct>> getProductMaxStock(Long franchiseId) {
        if (franchiseId == null || franchiseId <= 0) {
            return Mono.error(new NotValidFieldException("Franchise id cannot be 0"));
        }
        return this.franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franchise with id " + franchiseId + " not found")))
                .flatMap(franchise -> getProductMaxStockByFranchiseId(franchiseId));
    }

    @Override
    public Mono<DTOProductResponse> updateName(Long id, String name) {
        if (id == null || id <= 0) {
            return Mono.error(new NotValidFieldException("Product ID cannot be 0"));
        }
        if (name == null || name.isBlank()) {
            return Mono.error(new NotValidFieldException("Product name cannot be blank"));
        }

        return this.productRepository.findById(id)
                .flatMap(product -> {
                    product.setName(name);
                    return this.productRepository.save(product)
                            .map(productMapper::toDto)
                            .switchIfEmpty(Mono.error(new Exception("Product not updated")));
                })
                .switchIfEmpty(Mono.error(new NotFoundException("Product with id " + id + " not found")));
    }

    private Mono<List<DTOBranchProduct>> getProductMaxStockByFranchiseId(Long franchiseId) {
        return branchRepository.findByFranchiseId(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Not found branches for franchise with id " + franchiseId)))
                .flatMap(branch -> productRepository.findByBranchId(branch.getId())
                        .collectList()
                        .flatMap(products -> {
                            Product maxStockProduct = products.stream()
                                    .max(Comparator.comparingLong(Product::getStock))
                                    .orElse(null);
                            return Mono.justOrEmpty(
                                    maxStockProduct != null
                                            ? new DTOBranchProduct(branch.getName(),
                                                    productMapper.toDto(maxStockProduct))
                                            : null);
                        }))
                .collectList();
    }

    @Override
    public Mono<DTOProductResponse> updateStock(Long id, Long stock) {
        if (id == null || id <= 0) {
            return Mono.error(new NotValidFieldException("Product ID cannot be 0"));
        }
        if (stock == null || stock<= 0) {
            return Mono.error(new NotValidFieldException("Stock cannot be 0"));
        }

        return this.productRepository.findById(id)
                .flatMap(product -> {
                    product.setStock(stock);
                    return this.productRepository.save(product)
                            .map(productMapper::toDto)
                            .switchIfEmpty(Mono.error(new Exception("Product not updated")));
                })
                .switchIfEmpty(Mono.error(new NotFoundException("Product with id " + id + " not found")));
    }

}
