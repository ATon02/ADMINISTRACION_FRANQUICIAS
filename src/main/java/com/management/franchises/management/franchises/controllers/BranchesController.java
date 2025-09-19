package com.management.franchises.management.franchises.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.franchises.management.franchises.models.dtos.request.DTOBranch;
import com.management.franchises.management.franchises.models.dtos.response.DTOBranchResponse;
import com.management.franchises.management.franchises.models.dtos.response.ResponseData;
import com.management.franchises.management.franchises.services.BranchService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/branches")
public class BranchesController {
    
    @Autowired
    private BranchService branchService;


    @PostMapping
    public Mono<ResponseData<DTOBranchResponse>> createBranch(@RequestBody DTOBranch branch) {
        return this.branchService.create(branch).map(dto -> new ResponseData<>(HttpStatus.OK, dto));
    }

    @PatchMapping("/update-name/{id}")
    public Mono<ResponseData<DTOBranchResponse>> updateName(@PathVariable Long id, @RequestParam String name) {
        return this.branchService.updateName(id,name).map(dto -> new ResponseData<>(HttpStatus.OK, dto));
    }

}
