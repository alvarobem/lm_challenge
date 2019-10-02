package com.lm.challenge.taxes.controller.dto.output;

import com.lm.challenge.taxes.controller.dto.base.BasketBaseRDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class BasketRSDTO extends BasketBaseRDTO {

    private BigDecimal taxes;

    private BigDecimal totalPrice;
}
