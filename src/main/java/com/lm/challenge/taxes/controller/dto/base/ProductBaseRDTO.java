package com.lm.challenge.taxes.controller.dto.base;

import com.lm.challenge.taxes.controller.dto.base.type.ProductTypeRDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductBaseRDTO {

    private BigDecimal price;

    private ProductTypeRDTO type;

    private Boolean imported;

    private Integer quantity;

    private String name;
}
