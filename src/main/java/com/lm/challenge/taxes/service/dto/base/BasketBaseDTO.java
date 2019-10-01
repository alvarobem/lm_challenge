package com.lm.challenge.taxes.service.dto.base;

import lombok.Data;

import java.util.List;

@Data
public class BasketBaseDTO {

    List<ProductBaseDTO> products;
}
