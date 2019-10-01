package com.lm.challenge.taxes.service;

import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;

public interface SalesTaxesService {

    public BasketODTO calculateTaxes(BasketIDTO basketIDTO);
}
