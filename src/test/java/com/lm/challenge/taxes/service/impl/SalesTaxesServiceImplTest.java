package com.lm.challenge.taxes.service.impl;


import com.lm.challenge.taxes.persistence.model.ProductType;
import com.lm.challenge.taxes.persistence.model.ProductTypeExcludeMO;
import com.lm.challenge.taxes.persistence.repository.ProductTypeExcludeRepository;
import com.lm.challenge.taxes.service.dto.base.ProductBaseDTO;
import com.lm.challenge.taxes.service.dto.base.type.ProductTypeDTO;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;
import com.lm.challenge.taxes.service.dto.transformer.impl.SalesTaxesServiceTransformerImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class SalesTaxesServiceImplTest {

    @InjectMocks
    private SalesTaxesServiceImpl service;

    @Mock
    private ProductTypeExcludeRepository repository;

    @Mock
    private SalesTaxesServiceTransformerImpl transformer;


    @Test
    public void shouldCalculateTaxesWhenBasketHasOneProduct() {
        //given
        InOrder inOrder = inOrder(repository, transformer);
        BigDecimal expectedTaxes = new BigDecimal("1.0");
        BigDecimal expectedPrice = new BigDecimal("11.0");
        ProductTypeDTO productTypeDTO = ProductTypeDTO.FOOD;
        ProductType productType = ProductType.FOOD;
        ProductBaseDTO productBaseDTO = ProductBaseDTO.builder()
                .imported(false)
                .name("name")
                .price(new BigDecimal("10.0"))
                .quantity(1)
                .type(productTypeDTO)
                .build();
        BasketIDTO basketIDTO = new BasketIDTO();
        basketIDTO.setProducts(Collections.singletonList(productBaseDTO));
        BasketODTO basketODTO = mock(BasketODTO.class);
        when(transformer.toODTO(basketIDTO, expectedTaxes, expectedPrice)).thenReturn(basketODTO);
        when(transformer.toProductType(productTypeDTO)).thenReturn(productType);
        when(repository.findByType(ProductType.FOOD)).thenReturn(Optional.empty());

        //when
        BasketODTO response = service.calculateTaxes(basketIDTO);

        //then
        inOrder.verify(transformer).toProductType(productTypeDTO);
        inOrder.verify(repository).findByType(productType);
        inOrder.verify(transformer).toODTO(basketIDTO, expectedTaxes, expectedPrice);
        verifyNoMoreInteractions(transformer);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldCalculateTaxesWhenBasketHasOneImportedProduct() {
        //given
        InOrder inOrder = inOrder(repository, transformer);
        BigDecimal expectedTaxes = new BigDecimal("1.5");
        BigDecimal expectedPrice = new BigDecimal("11.5");
        ProductTypeDTO productTypeDTO = ProductTypeDTO.FOOD;
        ProductType productType = ProductType.FOOD;
        ProductBaseDTO productBaseDTO = ProductBaseDTO.builder()
                .imported(true)
                .name("name")
                .price(new BigDecimal("10.0"))
                .quantity(1)
                .type(productTypeDTO)
                .build();
        BasketIDTO basketIDTO = new BasketIDTO();
        basketIDTO.setProducts(Collections.singletonList(productBaseDTO));
        BasketODTO basketODTO = mock(BasketODTO.class);
        when(transformer.toODTO(basketIDTO, expectedTaxes, expectedPrice)).thenReturn(basketODTO);
        when(transformer.toProductType(productTypeDTO)).thenReturn(productType);
        when(repository.findByType(ProductType.FOOD)).thenReturn(Optional.empty());

        //when
        BasketODTO response = service.calculateTaxes(basketIDTO);

        //then
        inOrder.verify(transformer).toProductType(productTypeDTO);
        inOrder.verify(repository).findByType(productType);
        inOrder.verify(transformer).toODTO(basketIDTO, expectedTaxes, expectedPrice);
        verifyNoMoreInteractions(transformer);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldReturnEmptyBasketWhenBasketIDTOIsEmpty() {
        //given
        BasketIDTO basketIDTO = new BasketIDTO();
        basketIDTO.setProducts(Collections.emptyList());

        //when
        BasketODTO response = service.calculateTaxes(basketIDTO);

        //then
        verify(transformer).toODTO(basketIDTO, BigDecimal.ZERO, BigDecimal.ZERO);
        verifyZeroInteractions(repository);
        verifyNoMoreInteractions(transformer);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenBasketIDTOIsNull() {
        //when
        service.calculateTaxes(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenProductsInBasketIDTOIsNull() {
        //given
        BasketIDTO basketIDTO = new BasketIDTO();
        //when
        service.calculateTaxes(basketIDTO);
    }
}