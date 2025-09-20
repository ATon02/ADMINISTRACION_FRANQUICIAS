package co.com.prueba.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.com.prueba.api.dtos.request.DTOProduct;
import co.com.prueba.model.product.Product;
import co.com.prueba.usecase.dtos.DTOProductResponse;

@Mapper(componentModel = "spring")
public interface RequestProductDTOMapper {

    DTOProductResponse toResponse(Product product);
    
    Product toModel(DTOProduct dtoProduct);

}
