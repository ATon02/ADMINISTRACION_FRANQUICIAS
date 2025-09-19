package com.management.franchises.management.franchises.utils;

import org.mapstruct.Mapper;

import com.management.franchises.management.franchises.models.Branch;
import com.management.franchises.management.franchises.models.dtos.request.DTOBranch;
import com.management.franchises.management.franchises.models.dtos.response.DTOBranchResponse;


@Mapper(componentModel = "spring")
public interface BranchMapper {

    DTOBranchResponse toDto(Branch Branch);
    Branch toEntity(DTOBranch dto);

}
