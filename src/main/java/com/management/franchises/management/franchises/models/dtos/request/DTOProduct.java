package com.management.franchises.management.franchises.models.dtos.request;

import com.management.franchises.management.franchises.models.Product;

public class DTOProduct {

    private String name;
    private Long stock;
    private Long branchId;

    public DTOProduct() {
    }

    public DTOProduct(Product product) {
        this.name = product.getName();
        this.stock = product.getStock();
        this.branchId = product.getBranchId();
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
