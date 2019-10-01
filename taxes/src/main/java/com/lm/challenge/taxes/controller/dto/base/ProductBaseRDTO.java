package com.lm.challenge.taxes.controller.dto.base;

import com.lm.challenge.taxes.controller.dto.base.type.ProductTypeRDTO;
import lombok.Data;

@Data
public class ProductBaseRDTO {

    private Double price;

    private ProductTypeRDTO type;

    private Boolean imported;

    private Integer quantity;

    private String name;
}
