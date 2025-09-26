package co.com.prueba.api.mapper;

import org.mapstruct.Mapper;

import co.com.prueba.api.dtos.request.DTOProduct;
import co.com.prueba.api.dtos.response.DTOProductResponse;
import co.com.prueba.model.product.Product;

@Mapper(componentModel = "spring")
public interface RequestProductDTOMapper {

    DTOProductResponse toResponse(Product product);
    
    Product toModel(DTOProduct dtoProduct);

}
