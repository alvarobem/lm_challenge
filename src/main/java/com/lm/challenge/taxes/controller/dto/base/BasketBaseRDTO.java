package com.lm.challenge.taxes.controller.dto.base;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BasketBaseRDTO {

    @NotNull
    List<ProductBaseRDTO> products;
}
