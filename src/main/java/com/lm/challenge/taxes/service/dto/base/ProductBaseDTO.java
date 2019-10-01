package com.lm.challenge.taxes.service.dto.base;

import com.lm.challenge.taxes.service.dto.base.type.ProductTypeDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductBaseDTO {

    private BigDecimal price;

    private ProductTypeDTO type;

    private Boolean imported;

    private Integer quantity;

    private String name;
}
