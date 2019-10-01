package com.lm.challenge.taxes.service.dto.output;

import com.lm.challenge.taxes.service.dto.base.BasketBaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BasketODTO extends BasketBaseDTO {

    private Double taxes;

    private Double totalPrice;
}
