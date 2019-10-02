package com.lm.challenge.taxes.controller.dto.transformer.impl;

import com.lm.challenge.taxes.controller.dto.input.BasketRQDTO;
import com.lm.challenge.taxes.controller.dto.output.BasketRSDTO;
import com.lm.challenge.taxes.controller.dto.transformer.SalesTaxesControllerTransformer;
import com.lm.challenge.taxes.controller.dto.transformer.impl.mapper.SalesTaxesControllerMapper;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SalesTaxesControllerTransformerImpl implements SalesTaxesControllerTransformer {

    private SalesTaxesControllerMapper mapper;

    @Autowired
    public SalesTaxesControllerTransformerImpl (SalesTaxesControllerMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public BasketIDTO toIDTO(BasketRQDTO basketRQDTO) {
        return mapper.toIDTO(basketRQDTO);
    }

    @Override
    public BasketRSDTO toRSDTO(BasketODTO basketODTO) {
        return mapper.toRSDTO(basketODTO);
    }
}
