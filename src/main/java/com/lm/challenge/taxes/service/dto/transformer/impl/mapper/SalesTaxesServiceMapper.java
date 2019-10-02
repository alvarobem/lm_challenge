package com.lm.challenge.taxes.service.dto.transformer.impl.mapper;

import com.lm.challenge.taxes.persistence.model.ProductType;
import com.lm.challenge.taxes.service.dto.base.type.ProductTypeDTO;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SalesTaxesServiceMapper {

    SalesTaxesServiceMapper INSTANCE = Mappers.getMapper(SalesTaxesServiceMapper.class);

    BasketODTO toODTO (BasketIDTO basketIDTO);

    ProductType toProductType(ProductTypeDTO productTypeDTO);
}
