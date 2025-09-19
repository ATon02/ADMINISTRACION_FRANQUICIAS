package com.management.franchises.management.franchises.models.dtos.response;

import com.management.franchises.management.franchises.models.Franchise;

public class DTOFranchiseResponse  {
    private Long id;
    private String name;
    
    public DTOFranchiseResponse() {
    }

    public DTOFranchiseResponse(Franchise franchise) {
        this.id = franchise.getId();
        this.name = franchise.getName();
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

    
}
