package com.lm.challenge.taxes;

import com.lm.challenge.taxes.controller.SalesTaxesController;
import com.lm.challenge.taxes.controller.dto.base.ProductBaseRDTO;
import com.lm.challenge.taxes.controller.dto.base.type.ProductTypeRDTO;
import com.lm.challenge.taxes.controller.dto.input.BasketRQDTO;
import com.lm.challenge.taxes.controller.dto.output.BasketRSDTO;
import com.lm.challenge.taxes.persistence.model.ProductType;
import com.lm.challenge.taxes.persistence.model.ProductTypeExcludeMO;
import com.lm.challenge.taxes.persistence.repository.ProductTypeExcludeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalesTaxesIntegrationTest {

    @Autowired
    private ProductTypeExcludeRepository repository;

    @Autowired
    private SalesTaxesController controller;

    @Before
    public void init() {
        ProductTypeExcludeMO books = ProductTypeExcludeMO.builder()
                .type(ProductType.BOOK)
                .id(1)
                .build();

        ProductTypeExcludeMO medical = ProductTypeExcludeMO.builder()
                .type(ProductType.MEDICAL)
                .id(2)
                .build();


        ProductTypeExcludeMO food = ProductTypeExcludeMO.builder()
                .type(ProductType.FOOD)
                .id(3)
                .build();

        repository.save(books);
        repository.save(food);
        repository.save(medical);
    }

    @Test
    public void shouldCalculateTaxesToInput2() {
        //given
        HashMap<String, Double> prices = new HashMap<>();
        prices.put("imported box of chocolates", 10.5);
        prices.put("imported bottle of perfume", 54.65);

        ProductBaseRDTO chocolates = ProductBaseRDTO.builder()
                .imported(true)
                .price(new BigDecimal("10.00"))
                .quantity(1)
                .type(ProductTypeRDTO.FOOD)
                .name("imported box of chocolates")
                .build();

        ProductBaseRDTO perfume = ProductBaseRDTO.builder()
                .imported(true)
                .price(new BigDecimal("47.50"))
                .type(ProductTypeRDTO.OTHERS)
                .quantity(1)
                .name("imported bottle of perfume")
                .build();

        BasketRQDTO basketRQDTO = new BasketRQDTO();
        basketRQDTO.setProducts(Arrays.asList(chocolates, perfume));

        //when
        ResponseEntity<BasketRSDTO> response = controller.calculateTaxes(basketRQDTO);

        //then

        BasketRSDTO basketRSDTO = response.getBody();
        assertEquals(7.65, basketRSDTO.getTaxes().doubleValue());
        assertEquals(65.15, basketRSDTO.getTotalPrice().doubleValue());
        basketRSDTO.getProducts().forEach(productBaseDTO -> assertProductPrices(productBaseDTO, prices));
    }

    @Test
    public void shouldCalculateTaxesTOInput1() {
        //given
        HashMap<String, Double> prices = new HashMap<>();
        prices.put("book", 12.49);
        prices.put("music CD", 16.49);
        prices.put("chocolate bar", 0.85);
        ProductBaseRDTO book = ProductBaseRDTO.builder()
                .imported(false)
                .price(new BigDecimal("12.49"))
                .quantity(1)
                .type(ProductTypeRDTO.BOOK)
                .name("book")
                .build();

        ProductBaseRDTO musicCD = ProductBaseRDTO.builder()
                .imported(false)
                .price(new BigDecimal("14.99"))
                .type(ProductTypeRDTO.OTHERS)
                .quantity(1)
                .name("music CD")
                .build();

        ProductBaseRDTO chocolateBar = ProductBaseRDTO.builder()
                .imported(false)
                .price(new BigDecimal("0.85"))
                .quantity(1)
                .type(ProductTypeRDTO.FOOD)
                .name("chocolate bar")
                .build();

        BasketRQDTO basketRQDTO = new BasketRQDTO();
        basketRQDTO.setProducts(Arrays.asList(book, musicCD, chocolateBar));


        //when
        ResponseEntity<BasketRSDTO> responseEntity = controller.calculateTaxes(basketRQDTO);

        //then
        BasketRSDTO basketRSDTO = responseEntity.getBody();
        assertEquals(1.50, basketRSDTO.getTaxes().doubleValue());
        assertEquals(29.83, basketRSDTO.getTotalPrice().doubleValue());
        basketRSDTO.getProducts().forEach(productBaseDTO -> assertProductPrices(productBaseDTO, prices));
    }

    @Test
    public void shouldCalculateTaxesTOInput3() {
        //given
        HashMap<String, Double> prices = new HashMap<>();
        prices.put("imported bottle of perfume", 32.19);
        prices.put("bottle of perfume", 20.89);
        prices.put("packet of headache pills", 9.75);
        prices.put("imported box of chocolates", 11.85);
        ProductBaseRDTO chocolates = ProductBaseRDTO.builder()
                .imported(true)
                .price(new BigDecimal("11.25"))
                .quantity(1)
                .type(ProductTypeRDTO.FOOD)
                .name("imported box of chocolates")
                .build();

        ProductBaseRDTO importedPerfume = ProductBaseRDTO.builder()
                .imported(true)
                .price(new BigDecimal("27.99"))
                .type(ProductTypeRDTO.OTHERS)
                .quantity(1)
                .name("imported bottle of perfume")
                .build();

        ProductBaseRDTO perfume = ProductBaseRDTO.builder()
                .imported(false)
                .price(new BigDecimal("18.99"))
                .type(ProductTypeRDTO.OTHERS)
                .quantity(1)
                .name("bottle of perfume")
                .build();

        ProductBaseRDTO headachePills = ProductBaseRDTO.builder()
                .imported(false)
                .price(new BigDecimal("9.75"))
                .type(ProductTypeRDTO.MEDICAL)
                .quantity(1)
                .name("packet of headache pills")
                .build();


        BasketRQDTO  basketIDTO = new BasketRQDTO();
        basketIDTO.setProducts(Arrays.asList(chocolates, perfume, importedPerfume, headachePills));

        //when
        ResponseEntity<BasketRSDTO> responseEntity = controller.calculateTaxes(basketIDTO);

        //then

        BasketRSDTO basketRSDTO = responseEntity.getBody();
        assertEquals(6.70, basketRSDTO.getTaxes().doubleValue());
        assertEquals(74.68, basketRSDTO.getTotalPrice().doubleValue());
        basketRSDTO.getProducts().forEach(productBaseDTO -> assertProductPrices(productBaseDTO, prices));
    }


    private void assertProductPrices(ProductBaseRDTO productBaseDTO, HashMap<String, Double> prices) {

        assertEquals(prices.get(productBaseDTO.getName()), productBaseDTO.getPrice().doubleValue());
    }

}
