package com.lm.challenge.taxes.controller.dto.base;

import lombok.Data;

import java.util.List;

@Data
public class BasketBaseRDTO {

    List<ProductBaseRDTO> products;
}
