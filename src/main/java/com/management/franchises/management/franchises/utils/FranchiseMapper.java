package com.management.franchises.management.franchises.utils;

import org.mapstruct.Mapper;

import com.management.franchises.management.franchises.models.Franchise;
import com.management.franchises.management.franchises.models.dtos.request.DTOFranchise;
import com.management.franchises.management.franchises.models.dtos.response.DTOFranchiseResponse;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

    DTOFranchiseResponse toDto(Franchise franchise);
    Franchise toEntity(DTOFranchise dto);

}
