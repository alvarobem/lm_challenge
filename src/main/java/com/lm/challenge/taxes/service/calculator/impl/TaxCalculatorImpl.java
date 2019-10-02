package com.lm.challenge.taxes.service.calculator.impl;

import com.lm.challenge.taxes.service.calculator.TaxCalculator;

import java.math.BigDecimal;

public class TaxCalculatorImpl implements TaxCalculator {



    @Override
    public BigDecimal getTaxes() {
        return BigDecimal.ZERO;
    }
}
