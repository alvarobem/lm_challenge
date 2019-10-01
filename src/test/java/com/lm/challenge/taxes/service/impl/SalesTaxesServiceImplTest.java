package com.lm.challenge.taxes.service.impl;


import com.lm.challenge.taxes.persistence.model.ProductTypeExcludeMO;
import com.lm.challenge.taxes.persistence.repository.ProductTypeExcludeRepository;
import com.lm.challenge.taxes.service.dto.base.ProductBaseDTO;
import com.lm.challenge.taxes.service.dto.base.type.ProductTypeDTO;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;
import com.lm.challenge.taxes.service.dto.transformer.impl.SalesTaxesServiceTransformerImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;


import static junit.framework.TestCase.assertEquals;
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
    public void shouldCalculateTaxesTOInput1() {
        //given
        ArgumentCaptor<BasketIDTO> basketIDTOArgumentCaptor = ArgumentCaptor.forClass(BasketIDTO.class);
        ArgumentCaptor<BigDecimal> taxesCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        ArgumentCaptor<BigDecimal> totalAmountCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        InOrder inOrder = inOrder(repository, transformer);
        HashMap<String, Double> prices = new HashMap<>();
        prices.put("book", 12.49);
        prices.put("music CD", 16.49);
        prices.put("chocolate bar", 0.85);
        ProductBaseDTO book = ProductBaseDTO.builder()
                .imported(false)
                .price(new BigDecimal("12.49"))
                .quantity(1)
                .type(ProductTypeDTO.BOOK)
                .name("book")
                .build();

        ProductBaseDTO musicCD = ProductBaseDTO.builder()
                .imported(false)
                .price(new BigDecimal("14.99"))
                .type(ProductTypeDTO.OTHERS)
                .quantity(1)
                .name("music CD")
                .build();

        ProductBaseDTO chocolateBar = ProductBaseDTO.builder()
                .imported(false)
                .price(new BigDecimal("0.85"))
                .quantity(1)
                .type(ProductTypeDTO.FOOD)
                .name("chocolate bar")
                .build();
        ProductTypeExcludeMO productTypeExcludeMO = mock(ProductTypeExcludeMO.class);

        BasketIDTO  basketIDTO = new BasketIDTO();
        basketIDTO.setProducts(Arrays.asList(book, musicCD, chocolateBar));
        when(repository.findByType(ProductTypeDTO.BOOK.name())).thenReturn(Optional.of(productTypeExcludeMO));
        when(repository.findByType(ProductTypeDTO.OTHERS.name())).thenReturn(Optional.empty());
        when(repository.findByType(ProductTypeDTO.FOOD.name())).thenReturn(Optional.of(productTypeExcludeMO));



        //when
        BasketODTO basketODTO = service.calculateTaxes(basketIDTO);

        //then
        inOrder.verify(repository).findByType(ProductTypeDTO.BOOK.name());
        inOrder.verify(repository).findByType(ProductTypeDTO.OTHERS.name());
        inOrder.verify(repository).findByType(ProductTypeDTO.FOOD.name());
        inOrder.verify(transformer).toODTO(basketIDTOArgumentCaptor.capture(), taxesCaptor.capture(), totalAmountCaptor.capture());
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(transformer);

        assertEquals(1.50, taxesCaptor.getValue().doubleValue());
        assertEquals(29.83, totalAmountCaptor.getValue().doubleValue());
        basketIDTOArgumentCaptor.getValue().getProducts().forEach(productBaseDTO -> assertProductPrices(productBaseDTO, prices));
    }

    private void assertProductPrices(ProductBaseDTO productBaseDTO, HashMap <String, Double> prices) {

        assertEquals(prices.get(productBaseDTO.getName()),productBaseDTO.getPrice().doubleValue());
    }

    @Test
    public void shouldCalculateTaxesTOInput2() {
        //given
        HashMap<String, Double> prices = new HashMap<>();
        prices.put("imported box of chocolates", 10.5);
        prices.put("imported bottle of perfume", 54.65);
        ArgumentCaptor<BasketIDTO> basketIDTOArgumentCaptor = ArgumentCaptor.forClass(BasketIDTO.class);
        ArgumentCaptor<BigDecimal> taxesCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        ArgumentCaptor<BigDecimal> totalAmountCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        InOrder inOrder = inOrder(repository, transformer);
        ProductBaseDTO chocolates = ProductBaseDTO.builder()
                .imported(true)
                .price(new BigDecimal("10.00"))
                .quantity(1)
                .type(ProductTypeDTO.FOOD)
                .name("imported box of chocolates")
                .build();

        ProductBaseDTO perfume = ProductBaseDTO.builder()
                .imported(true)
                .price(new BigDecimal("47.50"))
                .type(ProductTypeDTO.OTHERS)
                .quantity(1)
                .name("imported bottle of perfume")
                .build();

        ProductTypeExcludeMO productTypeExcludeMO = mock(ProductTypeExcludeMO.class);

        BasketIDTO  basketIDTO = new BasketIDTO();
        basketIDTO.setProducts(Arrays.asList(chocolates, perfume));
        when(repository.findByType(ProductTypeDTO.OTHERS.name())).thenReturn(Optional.empty());
        when(repository.findByType(ProductTypeDTO.FOOD.name())).thenReturn(Optional.of(productTypeExcludeMO));



        //when
        BasketODTO basketODTO = service.calculateTaxes(basketIDTO);

        //then
        inOrder.verify(repository).findByType(ProductTypeDTO.FOOD.name());
        inOrder.verify(repository).findByType(ProductTypeDTO.OTHERS.name());
        inOrder.verify(transformer).toODTO(basketIDTOArgumentCaptor.capture(), taxesCaptor.capture(), totalAmountCaptor.capture());
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(transformer);

        assertEquals(7.65, taxesCaptor.getValue().doubleValue());
        assertEquals(65.15, totalAmountCaptor.getValue().doubleValue());
        basketIDTOArgumentCaptor.getValue().getProducts().forEach(productBaseDTO -> assertProductPrices(productBaseDTO, prices));
    }

    @Test
    public void shouldCalculateTaxesTOInput3() {
        //given
        HashMap<String, Double> prices = new HashMap<>();
        prices.put("imported bottle of perfume", 32.19);
        prices.put("bottle of perfume", 20.89);
        prices.put("packet of headache pills", 9.75);
        prices.put("imported box of chocolates", 11.85);
        ArgumentCaptor<BasketIDTO> basketIDTOArgumentCaptor = ArgumentCaptor.forClass(BasketIDTO.class);
        ArgumentCaptor<BigDecimal> taxesCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        ArgumentCaptor<BigDecimal> totalAmountCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        InOrder inOrder = inOrder(repository, transformer);
        ProductBaseDTO chocolates = ProductBaseDTO.builder()
                .imported(true)
                .price(new BigDecimal("11.25"))
                .quantity(1)
                .type(ProductTypeDTO.FOOD)
                .name("imported box of chocolates")
                .build();

        ProductBaseDTO importedPerfume = ProductBaseDTO.builder()
                .imported(true)
                .price(new BigDecimal("27.99"))
                .type(ProductTypeDTO.OTHERS)
                .quantity(1)
                .name("imported bottle of perfume")
                .build();

        ProductBaseDTO perfume = ProductBaseDTO.builder()
                .imported(false)
                .price(new BigDecimal("18.99"))
                .type(ProductTypeDTO.OTHERS)
                .quantity(1)
                .name("bottle of perfume")
                .build();

        ProductBaseDTO headachePills = ProductBaseDTO.builder()
                .imported(false)
                .price(new BigDecimal("9.75"))
                .type(ProductTypeDTO.MEDICAL)
                .quantity(1)
                .name("packet of headache pills")
                .build();

        ProductTypeExcludeMO productTypeExcludeMO = mock(ProductTypeExcludeMO.class);

        BasketIDTO  basketIDTO = new BasketIDTO();
        basketIDTO.setProducts(Arrays.asList(chocolates, perfume, importedPerfume, headachePills));
        when(repository.findByType(ProductTypeDTO.OTHERS.name())).thenReturn(Optional.empty());
        when(repository.findByType(ProductTypeDTO.FOOD.name())).thenReturn(Optional.of(productTypeExcludeMO));
        when(repository.findByType(ProductTypeDTO.MEDICAL.name())).thenReturn(Optional.of(productTypeExcludeMO));



        //when
        BasketODTO basketODTO = service.calculateTaxes(basketIDTO);

        //then
        inOrder.verify(repository).findByType(ProductTypeDTO.FOOD.name());
        inOrder.verify(repository, times(2)).findByType(ProductTypeDTO.OTHERS.name());
        inOrder.verify(repository).findByType(ProductTypeDTO.MEDICAL.name());
        inOrder.verify(transformer).toODTO(basketIDTOArgumentCaptor.capture(), taxesCaptor.capture(), totalAmountCaptor.capture());
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(transformer);

        assertEquals(6.70, taxesCaptor.getValue().doubleValue());
        assertEquals(74.68, totalAmountCaptor.getValue().doubleValue());
        basketIDTOArgumentCaptor.getValue().getProducts().forEach(productBaseDTO -> assertProductPrices(productBaseDTO, prices));
    }
}