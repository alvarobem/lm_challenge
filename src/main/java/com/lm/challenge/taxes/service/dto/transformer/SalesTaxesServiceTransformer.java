package com.lm.challenge.taxes.service.dto.transformer;

import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;

import java.math.BigDecimal;

public interface SalesTaxesServiceTransformer {

    BasketODTO toODTO (BasketIDTO basketIDTO, BigDecimal taxes, BigDecimal total);
}
