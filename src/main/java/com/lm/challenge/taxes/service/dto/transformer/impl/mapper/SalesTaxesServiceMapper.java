package com.lm.challenge.taxes.service.dto.transformer.impl.mapper;

import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalesTaxesServiceMapper {


    BasketODTO toODTO (BasketIDTO basketIDTO);
}
