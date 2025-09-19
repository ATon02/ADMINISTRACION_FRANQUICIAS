package com.management.franchises.management.franchises.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.franchises.management.franchises.exceptions.NotFoundException;
import com.management.franchises.management.franchises.exceptions.NotValidFieldException;
import com.management.franchises.management.franchises.models.Franchise;
import com.management.franchises.management.franchises.models.dtos.request.DTOFranchise;
import com.management.franchises.management.franchises.models.dtos.response.DTOFranchiseResponse;
import com.management.franchises.management.franchises.respositories.FranchiseRepository;
import com.management.franchises.management.franchises.services.FranchiseService;
import com.management.franchises.management.franchises.utils.FranchiseMapper;

import reactor.core.publisher.Mono;

@Service
public class FranchiseServiceImpl implements FranchiseService {

    @Autowired
    private  FranchiseRepository franchiseRepository;
    @Autowired
    private  FranchiseMapper franchiseMapper;


    @Override
    public Mono<DTOFranchiseResponse> create(DTOFranchise franchise) {
        if (franchise.getName().isBlank()) {
            return Mono.error(new NotValidFieldException("Franchise name cannot be blank"));
        }

        return franchiseRepository.findByName(franchise.getName())
            .flatMap(exist -> Mono.<DTOFranchiseResponse>error(
                new NotValidFieldException("Franchise name already exists"))
            )
            .switchIfEmpty(
                Mono.defer(() -> 
                    franchiseRepository.save(franchiseMapper.toEntity(franchise))
                        .map(franchiseMapper::toDto)
                        .switchIfEmpty(Mono.error(new Exception("Franchise not created")))
                )
            );    
    }

    @Override
    public Mono<DTOFranchiseResponse> updateName(Long id, String name) {
        if (id == null || id <= 0) {
            return Mono.error(new NotValidFieldException("Franchise ID cannot be 0 or negative"));
        }
        if (name == null || name.isBlank()) {
            return Mono.error(new NotValidFieldException("Franchise name cannot be blank"));
        }

        return franchiseRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("Franchise with id " + id + " not found")))
            .flatMap(existingFranchise ->
                franchiseRepository.findByName(name)
                    .flatMap(franchiseByName -> {
                        if (!franchiseByName.getId().equals(id)) {
                            return Mono.error(new NotValidFieldException("Franchise name already exists"));
                        }
                        existingFranchise.setName(name);
                        return updateMethod(existingFranchise);
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        existingFranchise.setName(name);
                        return updateMethod(existingFranchise);
                    }))
            )
            .onErrorMap(Exception.class, ex -> new Exception(ex.getMessage()));
    }

    private Mono<DTOFranchiseResponse> updateMethod(Franchise franchise) {
        return franchiseRepository.save(franchise)
            .map(franchiseMapper::toDto)
            .switchIfEmpty(Mono.error(new Exception("Franchise not updated")));
    }
}

