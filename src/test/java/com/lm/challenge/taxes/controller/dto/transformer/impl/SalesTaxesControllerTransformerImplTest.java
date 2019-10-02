package com.lm.challenge.taxes.controller.dto.transformer.impl;

import com.lm.challenge.taxes.controller.dto.base.BasketBaseRDTO;
import com.lm.challenge.taxes.controller.dto.base.ProductBaseRDTO;
import com.lm.challenge.taxes.controller.dto.base.type.ProductTypeRDTO;
import com.lm.challenge.taxes.controller.dto.input.BasketRQDTO;
import com.lm.challenge.taxes.controller.dto.output.BasketRSDTO;
import com.lm.challenge.taxes.controller.dto.transformer.impl.mapper.SalesTaxesControllerMapper;
import com.lm.challenge.taxes.service.dto.base.BasketBaseDTO;
import com.lm.challenge.taxes.service.dto.base.ProductBaseDTO;
import com.lm.challenge.taxes.service.dto.base.type.ProductTypeDTO;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class SalesTaxesControllerTransformerImplTest {

    @Spy
    private SalesTaxesControllerMapper mapper = SalesTaxesControllerMapper.INSTANCE;

    @InjectMocks
    private SalesTaxesControllerTransformerImpl transformer;

    @Test
    public void shouldTransformToIDTO() {

        //given
        ProductBaseRDTO productBaseRDTO = ProductBaseRDTO.builder()
                .imported(false)
                .name("name")
                .price(new BigDecimal("1.0"))
                .type(ProductTypeRDTO.FOOD)
                .quantity(1)
                .build();
        BasketRQDTO basketRQDTO = new BasketRQDTO();
        basketRQDTO.setProducts(Collections.singletonList(productBaseRDTO));

        //when
        BasketIDTO basketIDTO = transformer.toIDTO(basketRQDTO);

        //then
        assertBases(basketRQDTO, basketIDTO);
    }

    @Test
    public void shouldTransformToRSDTO() {
        //given
        ProductBaseDTO productBaseRDTO = ProductBaseDTO.builder()
                .imported(false)
                .name("name")
                .price(new BigDecimal("1.0"))
                .type(ProductTypeDTO.FOOD)
                .quantity(1)
                .build();
        BasketODTO basketODTO = new BasketODTO();
        basketODTO.setProducts(Collections.singletonList(productBaseRDTO));
        basketODTO.setTaxes(new BigDecimal("1.0"));
        basketODTO.setTotalPrice(new BigDecimal("2.0"));

        //when
        BasketRSDTO basketRSDTO = transformer.toRSDTO(basketODTO);
        assertEquals(basketODTO.getTaxes(), basketRSDTO.getTaxes());
        assertEquals(basketODTO.getTotalPrice(), basketRSDTO.getTotalPrice());
        assertBases(basketRSDTO, basketODTO);

    }

    private void assertBases(BasketBaseRDTO basketRQDTO, BasketBaseDTO basketIDTO) {
        assertEquals(basketRQDTO.getProducts().size(), basketIDTO.getProducts().size());
        for (int i = 0; basketIDTO.getProducts().size() <= i; i++){
            assertEquals(basketRQDTO.getProducts().get(i).getName(), basketIDTO.getProducts().get(i).getName());
            assertEquals(basketRQDTO.getProducts().get(i).getImported(), basketIDTO.getProducts().get(i).getImported());
            assertEquals(basketRQDTO.getProducts().get(i).getPrice(), basketIDTO.getProducts().get(i).getPrice());
            assertEquals(basketRQDTO.getProducts().get(i).getQuantity(), basketIDTO.getProducts().get(i).getQuantity());
            assertEquals(basketRQDTO.getProducts().get(i).getType().name(), basketIDTO.getProducts().get(i).getType().name());
        }
    }
}