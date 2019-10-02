package com.lm.challenge.taxes.service.impl;


import com.lm.challenge.taxes.persistence.repository.ProductTypeExcludeRepository;
import com.lm.challenge.taxes.service.dto.transformer.impl.SalesTaxesServiceTransformerImpl;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



public class SalesTaxesServiceImplTest {

    @InjectMocks
    private SalesTaxesServiceImpl service;

    @Mock
    private ProductTypeExcludeRepository repository;

    @Mock
    private SalesTaxesServiceTransformerImpl transformer;


}