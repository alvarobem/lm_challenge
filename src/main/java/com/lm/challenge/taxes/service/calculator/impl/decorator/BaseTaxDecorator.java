package com.lm.challenge.taxes.service.calculator.impl.decorator;

import com.lm.challenge.taxes.service.calculator.TaxCalculator;

import java.math.BigDecimal;

public class BaseTaxDecorator extends TaxDecorator {

    public BaseTaxDecorator(TaxCalculator taxCalculator) {
        super(taxCalculator);
    }

    @Override
    public BigDecimal getTaxes() {
        return getTaxCalculator().getTaxes().add(BASE_TAX);
    }
}
