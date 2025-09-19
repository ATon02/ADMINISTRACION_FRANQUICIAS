package com.management.franchises.management.franchises.models.dtos.response;

import com.management.franchises.management.franchises.models.Product;

public class DTOProductResponse {

    private Long id;
    private String name;
    private Long stock;
    private Long branchId;

    public DTOProductResponse() {
    }

    public DTOProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.stock = product.getStock();
        this.branchId = product.getBranchId();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getStock() {
        return stock;
    }
    public void setStock(Long stock) {
        this.stock = stock;
    }
    public Long getBranchId() {
        return branchId;
    }
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    
}
