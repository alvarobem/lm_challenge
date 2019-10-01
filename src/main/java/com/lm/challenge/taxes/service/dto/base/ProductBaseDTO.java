package com.lm.challenge.taxes.service.dto.base;

import com.lm.challenge.taxes.service.dto.base.type.ProductTypeDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductBaseDTO {

    private Double price;

    private ProductTypeDTO type;

    private Boolean imported;

    private Integer quantity;

    private String name;
}
