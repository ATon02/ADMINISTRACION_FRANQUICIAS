package co.com.prueba.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.com.prueba.api.dtos.request.DTOFranchise;
import co.com.prueba.api.dtos.response.DTOFranchiseResponse;
import co.com.prueba.model.franchise.Franchise;

@Mapper(componentModel = "spring")
public interface RequestFranchiseDTOMapper {

    DTOFranchiseResponse toResponse(Franchise franchise);
    
    @Mapping(target = "id", ignore = true)
    Franchise toModel(DTOFranchise dtoFranchise);

}
