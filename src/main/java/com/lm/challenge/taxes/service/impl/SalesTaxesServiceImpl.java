package com.lm.challenge.taxes.service.impl;

import com.lm.challenge.taxes.persistence.model.ProductType;
import com.lm.challenge.taxes.persistence.model.ProductTypeExcludeMO;
import com.lm.challenge.taxes.persistence.repository.ProductTypeExcludeRepository;
import com.lm.challenge.taxes.service.SalesTaxesService;
import com.lm.challenge.taxes.service.calculator.TaxCalculator;
import com.lm.challenge.taxes.service.calculator.impl.TaxCalculatorImpl;
import com.lm.challenge.taxes.service.calculator.impl.decorator.BaseTaxDecorator;
import com.lm.challenge.taxes.service.calculator.impl.decorator.ImportedTaxDecorator;
import com.lm.challenge.taxes.service.dto.base.ProductBaseDTO;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;
import com.lm.challenge.taxes.service.dto.transformer.SalesTaxesServiceTransformer;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class SalesTaxesServiceImpl implements SalesTaxesService {

    private ProductTypeExcludeRepository repository;

    private SalesTaxesServiceTransformer transformer;

    @Autowired
    public SalesTaxesServiceImpl (ProductTypeExcludeRepository repository,
                                  SalesTaxesServiceTransformer transformer){
        this.repository = repository;
        this.transformer = transformer;
    }

    @Override
    public BasketODTO calculateTaxes(@Valid BasketIDTO basketIDTO) {

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
        ProductType productType = transformer.toProductType(product.getType());
        Optional<ProductTypeExcludeMO> excluded =  repository.findByType(productType);
        TaxCalculator taxCalculator = new TaxCalculatorImpl();

        if(!excluded.isPresent()){
            taxCalculator = new BaseTaxDecorator(taxCalculator);
        }
        if (product.getImported()){
            taxCalculator = new ImportedTaxDecorator(taxCalculator);
        }
        return taxCalculator.getTaxes();
    }

    private BigDecimal roundTaxes(BigDecimal taxes) {
        Double roundedTaxes = Math.ceil(taxes.doubleValue() * 20) / 20;
        return new BigDecimal(roundedTaxes.toString());
    }

    private BigDecimal calculateProductTaxes(ProductBaseDTO product, BigDecimal tax) {
        BigDecimal taxAmount =  (product.getPrice().multiply(tax)).multiply(new BigDecimal(product.getQuantity()));
        return roundTaxes(taxAmount);
    }
}
