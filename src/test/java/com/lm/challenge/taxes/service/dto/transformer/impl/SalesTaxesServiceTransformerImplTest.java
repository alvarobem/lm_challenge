package com.lm.challenge.taxes.service.dto.transformer.impl;

import com.lm.challenge.taxes.persistence.model.ProductType;
import com.lm.challenge.taxes.service.dto.base.ProductBaseDTO;
import com.lm.challenge.taxes.service.dto.base.type.ProductTypeDTO;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;
import com.lm.challenge.taxes.service.dto.transformer.impl.mapper.SalesTaxesServiceMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SalesTaxesServiceTransformerImplTest {

    @Spy
    private SalesTaxesServiceMapper mapper = SalesTaxesServiceMapper.INSTANCE;

    private SalesTaxesServiceTransformerImpl transformer;

    @Before
    public void init (){
        transformer = new SalesTaxesServiceTransformerImpl(mapper);
    }

    @Test
    public void shouldTransformToODTO() {
        //given
        ProductBaseDTO productBaseDTO = ProductBaseDTO.builder()
                .imported(true)
                .price(new BigDecimal("2.0"))
                .type(ProductTypeDTO.BOOK)
                .quantity(2)
                .name("name")
                .build();

        BigDecimal taxes = new BigDecimal("3.0");
        BigDecimal totalPrice = new BigDecimal("5.0");

        BasketIDTO basketIDTO = new BasketIDTO();
        basketIDTO.setProducts(Arrays.asList(productBaseDTO));
        //when
        BasketODTO basketODTO = transformer.toODTO(basketIDTO, taxes, totalPrice);

        //then
        assertEquals(taxes, basketODTO.getTaxes());
        assertEquals(totalPrice, basketODTO.getTotalPrice());
        assertEquals(basketIDTO.getProducts().size(), basketODTO.getProducts().size());

        basketODTO.getProducts().forEach(product -> assertProductBase(productBaseDTO, product));
        assertEquals(taxes, basketODTO.getTaxes());

    }

    @Test
    public void shouldTransformToProductType() {
        //given
        ProductTypeDTO productTypeDTO = ProductTypeDTO.BOOK;

        //when
        ProductType productType = transformer.toProductType(productTypeDTO);

        //then
        assertEquals(ProductType.BOOK, productType);
    }

    private void assertProductBase(ProductBaseDTO input, ProductBaseDTO output) {
        assertEquals(input.getPrice(), output.getPrice());
        assertEquals(input.getImported(), output.getImported());
        assertEquals(input.getName(), output.getName());
        assertEquals(input.getQuantity(), output.getQuantity());
        assertEquals(input.getType(), output.getType());
    }
}