package com.lm.challenge.taxes.controller.dto.output;

import com.lm.challenge.taxes.controller.dto.base.BasketBaseRDTO;

import java.math.BigDecimal;

public class BasketRSDTO extends BasketBaseRDTO {

    private BigDecimal taxes;

    private BigDecimal totalPrice;
}
