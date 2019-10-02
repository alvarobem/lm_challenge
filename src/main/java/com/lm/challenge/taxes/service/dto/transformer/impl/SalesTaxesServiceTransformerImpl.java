package com.lm.challenge.taxes.service.dto.transformer.impl;

import com.lm.challenge.taxes.persistence.model.ProductType;
import com.lm.challenge.taxes.service.dto.base.type.ProductTypeDTO;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;
import com.lm.challenge.taxes.service.dto.transformer.SalesTaxesServiceTransformer;
import com.lm.challenge.taxes.service.dto.transformer.impl.mapper.SalesTaxesServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SalesTaxesServiceTransformerImpl implements SalesTaxesServiceTransformer {

    private SalesTaxesServiceMapper mapper;

    @Autowired
    public SalesTaxesServiceTransformerImpl(SalesTaxesServiceMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public BasketODTO toODTO(BasketIDTO basketIDTO, BigDecimal taxes, BigDecimal total) {
        BasketODTO basketODTO = mapper.toODTO(basketIDTO);
        basketODTO.setTaxes(taxes);
        basketODTO.setTotalPrice(total);
        return basketODTO;
    }

    @Override
    public ProductType toProductType(ProductTypeDTO productTypeDTO) {
        return mapper.toProductType(productTypeDTO);
    }
}
