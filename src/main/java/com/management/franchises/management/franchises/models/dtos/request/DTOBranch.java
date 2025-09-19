package com.management.franchises.management.franchises.models.dtos.request;

import com.management.franchises.management.franchises.models.Branch;

public class DTOBranch {

    private String name;
    private Long franchiseId;

    public DTOBranch() {
    }

    public DTOBranch(Branch branch) {
        this.name = branch.getName();
        this.franchiseId = branch.getFranchiseId();
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
