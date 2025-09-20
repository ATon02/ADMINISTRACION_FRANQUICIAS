package co.com.prueba.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.com.prueba.api.dtos.request.DTOBranch;
import co.com.prueba.api.dtos.response.DTOBranchResponse;
import co.com.prueba.model.branch.Branch;

@Mapper(componentModel = "spring")
public interface RequestBranchDTOMapper {

    DTOBranchResponse toResponse(Branch branch);
    Branch toModel(DTOBranch dtoBranch);

}
