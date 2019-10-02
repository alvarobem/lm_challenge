package com.lm.challenge.taxes.service.dto.transformer;

import com.lm.challenge.taxes.persistence.model.ProductType;
import com.lm.challenge.taxes.service.dto.base.type.ProductTypeDTO;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;

import java.math.BigDecimal;

public interface SalesTaxesServiceTransformer {

    BasketODTO toODTO (BasketIDTO basketIDTO, BigDecimal taxes, BigDecimal total);

    ProductType toProductType (ProductTypeDTO productTypeDTO);
}
