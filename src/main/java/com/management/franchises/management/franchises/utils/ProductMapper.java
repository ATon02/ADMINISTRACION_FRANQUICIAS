package com.management.franchises.management.franchises.utils;

import org.mapstruct.Mapper;

import com.management.franchises.management.franchises.models.Product;
import com.management.franchises.management.franchises.models.dtos.request.DTOProduct;
import com.management.franchises.management.franchises.models.dtos.response.DTOProductResponse;



@Mapper(componentModel = "spring")
public interface ProductMapper {

    DTOProductResponse toDto(Product product);
    Product toEntity(DTOProduct dto);

}
