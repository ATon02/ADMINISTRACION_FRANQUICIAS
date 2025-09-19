package com.management.franchises.management.franchises.models.dtos.response;

import com.management.franchises.management.franchises.models.Branch;

public class DTOBranchResponse  {

    private Long id;
    private String name;
    private Long franchiseId;

    public DTOBranchResponse() {
    }

    public DTOBranchResponse(Branch branch) {
        this.id = branch.getId();
        this.name = branch.getName();
        this.franchiseId = branch.getFranchiseId();
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
    public Long getFranchiseId() {
        return franchiseId;
    }
    public void setFranchiseId(Long franchiseId) {
        this.franchiseId = franchiseId;
    }

    
}
