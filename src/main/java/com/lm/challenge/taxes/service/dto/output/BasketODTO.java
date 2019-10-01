package com.lm.challenge.taxes.service.dto.output;

import com.lm.challenge.taxes.service.dto.base.BasketBaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class BasketODTO extends BasketBaseDTO {

    private BigDecimal taxes;

    private BigDecimal totalPrice;
}
