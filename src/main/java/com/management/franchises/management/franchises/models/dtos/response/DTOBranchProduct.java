package com.management.franchises.management.franchises.models.dtos.response;

public class DTOBranchProduct {

    private String branchName;
    private DTOProductResponse product;

    public DTOBranchProduct(String branchName, DTOProductResponse product) {
        this.branchName = branchName;
        this.product = product;
    }

    public String getBranchName() {
        return branchName;
    }

    public DTOProductResponse getProduct() {
        return product;
    }
    
}
