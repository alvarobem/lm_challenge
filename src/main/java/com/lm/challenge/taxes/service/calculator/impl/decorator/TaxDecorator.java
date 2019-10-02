package com.lm.challenge.taxes.service.calculator.impl.decorator;

import com.lm.challenge.taxes.service.calculator.TaxCalculator;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxDecorator implements TaxCalculator {

    protected BigDecimal BASE_TAX = new BigDecimal("0.1");
    protected BigDecimal IMPORTED_TAX = new BigDecimal("0.05");

    private TaxCalculator taxCalculator;

    public TaxDecorator (TaxCalculator taxCalculator){
        this.taxCalculator = taxCalculator;
    }

    @Override
    public BigDecimal getTaxes() {
        return null;
    }
}
