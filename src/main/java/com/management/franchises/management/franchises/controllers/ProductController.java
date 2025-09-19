package com.management.franchises.management.franchises.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.franchises.management.franchises.models.dtos.request.DTOProduct;
import com.management.franchises.management.franchises.models.dtos.response.DTOBranchProduct;
import com.management.franchises.management.franchises.models.dtos.response.DTOProductResponse;
import com.management.franchises.management.franchises.models.dtos.response.ResponseData;
import com.management.franchises.management.franchises.models.dtos.response.ResponseDataList;
import com.management.franchises.management.franchises.services.ProductService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping
    public Mono<ResponseData<DTOProductResponse>> createProduct(@RequestBody DTOProduct product) {
        return this.productService.create(product).map(dto -> new ResponseData<>(HttpStatus.OK, dto));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseData<Void>> deleteMapping(@PathVariable Long id) {
        return this.productService.delete(id).map(dto -> new ResponseData<>(HttpStatus.OK, dto));
    }

    @GetMapping("/max-product/{franchiseId}")
    public Mono<ResponseDataList<DTOBranchProduct>> getProductMaxStock(@PathVariable Long franchiseId) {
        return this.productService.getProductMaxStock(franchiseId).map(list -> new ResponseDataList<>(HttpStatus.OK, list));
    }

    @PatchMapping("/update-name/{id}")
    public Mono<ResponseData<DTOProductResponse>> updateName(@PathVariable Long id, @RequestParam String name) {
        return this.productService.updateName(id,name).map(dto -> new ResponseData<>(HttpStatus.OK, dto));
    }

}
