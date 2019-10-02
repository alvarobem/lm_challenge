package com.lm.challenge.taxes.service.dto.base;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BasketBaseDTO {

    @NotNull
    List<ProductBaseDTO> products;
}
