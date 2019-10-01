package com.lm.challenge.taxes.controller.dto.transformer;

import com.lm.challenge.taxes.controller.dto.input.BasketRQDTO;
import com.lm.challenge.taxes.controller.dto.output.BasketRSDTO;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;

public interface SalesTaxesControllerTransformer {


    BasketIDTO toIDTO(BasketRQDTO basketRQDTO);

    BasketRSDTO toRSDTO(BasketODTO basketODTO);
}
