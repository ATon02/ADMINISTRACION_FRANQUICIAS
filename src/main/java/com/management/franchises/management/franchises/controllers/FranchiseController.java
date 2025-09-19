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

import com.management.franchises.management.franchises.models.dtos.request.DTOFranchise;
import com.management.franchises.management.franchises.models.dtos.response.DTOFranchiseResponse;
import com.management.franchises.management.franchises.models.dtos.response.ResponseData;
import com.management.franchises.management.franchises.services.FranchiseService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {
    
    @Autowired
    private FranchiseService franchiseService;


    @PostMapping
    public Mono<ResponseData<DTOFranchiseResponse>> createFranchise(@RequestBody DTOFranchise franchise) {
        return this.franchiseService.create(franchise).map(dto -> new ResponseData<>(HttpStatus.OK, dto));
    }
    @PatchMapping("/update-name/{id}")
    public Mono<ResponseData<DTOFranchiseResponse>> updateName(@PathVariable Long id, @RequestParam String name) {
        return this.franchiseService.updateName(id,name).map(dto -> new ResponseData<>(HttpStatus.OK, dto));
    }


}
