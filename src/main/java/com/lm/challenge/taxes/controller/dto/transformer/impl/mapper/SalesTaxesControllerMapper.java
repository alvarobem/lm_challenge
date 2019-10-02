package com.lm.challenge.taxes.controller.dto.transformer.impl.mapper;

import com.lm.challenge.taxes.controller.dto.input.BasketRQDTO;
import com.lm.challenge.taxes.controller.dto.output.BasketRSDTO;
import com.lm.challenge.taxes.controller.dto.transformer.impl.SalesTaxesControllerTransformerImpl;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SalesTaxesControllerMapper {

    SalesTaxesControllerMapper INSTANCE = Mappers.getMapper(SalesTaxesControllerMapper.class);

    BasketIDTO toIDTO(BasketRQDTO basketRQDTO);

    BasketRSDTO toRSDTO(BasketODTO basketODTO);
}
