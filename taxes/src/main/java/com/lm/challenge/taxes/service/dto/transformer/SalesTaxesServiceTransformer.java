package com.lm.challenge.taxes.service.dto.transformer;

import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;

public interface SalesTaxesServiceTransformer {

    BasketODTO toODTO (BasketIDTO basketIDTO, Double taxes, Double total);
}
