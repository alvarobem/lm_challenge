package com.lm.challenge.taxes.service.calculator.impl.decorator;

import com.lm.challenge.taxes.service.calculator.TaxCalculator;
import com.lm.challenge.taxes.service.calculator.impl.TaxCalculatorImpl;

import java.math.BigDecimal;

public class ImportedTaxDecorator extends TaxDecorator {

    public ImportedTaxDecorator(TaxCalculator taxCalculator) {
        super(taxCalculator);
    }

    @Override
    public BigDecimal getTaxes() {
        return getTaxCalculator().getTaxes().add(IMPORTED_TAX);
    }
}
