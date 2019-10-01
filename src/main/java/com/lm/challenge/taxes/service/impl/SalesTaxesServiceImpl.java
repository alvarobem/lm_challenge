package com.lm.challenge.taxes.service.impl;

import com.lm.challenge.taxes.persistence.model.ProductTypeExcludeMO;
import com.lm.challenge.taxes.persistence.repository.ProductTypeExcludeRepository;
import com.lm.challenge.taxes.service.SalesTaxesService;
import com.lm.challenge.taxes.service.dto.base.ProductBaseDTO;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;
import com.lm.challenge.taxes.service.dto.transformer.SalesTaxesServiceTransformer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class SalesTaxesServiceImpl implements SalesTaxesService {

    private ProductTypeExcludeRepository repository;

    private SalesTaxesServiceTransformer transformer;

    private static final Double BASE_TAX = 0.10;
    private static final Double IMPORTED_TAX = 0.05;
    private static final Integer TWO_DIGITS = 100;


    @Autowired
    public SalesTaxesServiceImpl (ProductTypeExcludeRepository repository,
                                  SalesTaxesServiceTransformer transformer){
        this.repository = repository;
        this.transformer = transformer;
    }

    @Override
    public BasketODTO calculateTaxes(BasketIDTO basketIDTO) {

        Double totalTaxes = 0.0;
        Double totalPrice = 0.0;
        for (ProductBaseDTO product : basketIDTO.getProducts()){
            Optional<ProductTypeExcludeMO> excluded =  repository.findByType(product.getType().name());
            Double tax = BASE_TAX;
            if(!excluded.isPresent()){
                tax = product.getImported() ? BASE_TAX + IMPORTED_TAX : BASE_TAX;
                double productTaxes = calculateProductTaxes(product, tax);
                productTaxes = roundTaxes (productTaxes);
                totalTaxes += productTaxes;
                double roundedPrice = Math.round((product.getPrice() + productTaxes)*TWO_DIGITS);
                product.setPrice(roundedPrice/TWO_DIGITS);
            } else {
                if (product.getImported()){
                    totalTaxes += calculateProductTaxes(product, IMPORTED_TAX);
                    totalTaxes = roundTaxes (totalTaxes);
                    double roundedPrice = Math.round((product.getPrice() + totalTaxes)*TWO_DIGITS);
                    product.setPrice(roundedPrice/TWO_DIGITS);
                }
            }

            totalPrice += product.getPrice();
        }
        //We need truncate to 2 decimals
        totalPrice = Math.floor(totalPrice * TWO_DIGITS)/TWO_DIGITS;
        return transformer.toODTO(basketIDTO, totalTaxes, totalPrice);
    }

    private Double roundTaxes(Double taxes) {
        double a = Math.ceil(taxes * 20);
        return a / 20;

    }

    private Double calculateProductTaxes(ProductBaseDTO product, Double tax) {
        return (product.getPrice() * tax) * product.getQuantity();
    }
}
