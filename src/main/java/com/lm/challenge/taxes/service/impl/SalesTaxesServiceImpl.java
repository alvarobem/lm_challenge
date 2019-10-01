package com.lm.challenge.taxes.service.impl;

import com.lm.challenge.taxes.persistence.model.ProductTypeExcludeMO;
import com.lm.challenge.taxes.persistence.repository.ProductTypeExcludeRepository;
import com.lm.challenge.taxes.service.SalesTaxesService;
import com.lm.challenge.taxes.service.dto.base.ProductBaseDTO;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;
import com.lm.challenge.taxes.service.dto.transformer.SalesTaxesServiceTransformer;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

public class SalesTaxesServiceImpl implements SalesTaxesService {

    private ProductTypeExcludeRepository repository;

    private SalesTaxesServiceTransformer transformer;

    private static final BigDecimal BASE_TAX = new BigDecimal("0.1");
    private static final BigDecimal IMPORTED_TAX = new BigDecimal("0.05");


    @Autowired
    public SalesTaxesServiceImpl (ProductTypeExcludeRepository repository,
                                  SalesTaxesServiceTransformer transformer){
        this.repository = repository;
        this.transformer = transformer;
    }

    @Override
    public BasketODTO calculateTaxes(BasketIDTO basketIDTO) {

        BigDecimal totalTaxes = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ProductBaseDTO product : basketIDTO.getProducts()){
            BigDecimal tax = calculateQuantityTaxes(product);
            BigDecimal productTaxes = calculateProductTaxes(product, tax);
            totalTaxes = totalTaxes.add(productTaxes);
            BigDecimal roundedPrice = (product.getPrice().add(productTaxes));
            product.setPrice(roundedPrice);
            totalPrice = totalPrice.add(product.getPrice());
        }
        return transformer.toODTO(basketIDTO, totalTaxes, totalPrice);
    }

    private BigDecimal calculateQuantityTaxes (ProductBaseDTO product){
        Optional<ProductTypeExcludeMO> excluded =  repository.findByType(product.getType().name());
        BigDecimal tax = BigDecimal.ZERO;
        if(!excluded.isPresent()){
            tax = tax.add(BASE_TAX);
        }
        if (product.getImported()){
            tax = tax.add(IMPORTED_TAX);
        }
        return tax;
    }

    private BigDecimal roundTaxes(BigDecimal taxes) {
        Double a = Math.ceil(taxes.doubleValue() * 20);
        a = a /20;
        return new BigDecimal(a.toString());
    }

    private BigDecimal calculateProductTaxes(ProductBaseDTO product, BigDecimal tax) {
        BigDecimal taxAmount =  (product.getPrice().multiply(tax)).multiply(new BigDecimal(product.getQuantity()));
        return roundTaxes(taxAmount);
    }
}
