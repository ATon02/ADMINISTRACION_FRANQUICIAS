package com.management.franchises.management.franchises.models.dtos.request;

import com.management.franchises.management.franchises.models.Franchise;

public class DTOFranchise {
    private String name;
    
    public DTOFranchise() {
    }

    public DTOFranchise(Franchise franchise) {
        this.name = franchise.getName();
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
}
