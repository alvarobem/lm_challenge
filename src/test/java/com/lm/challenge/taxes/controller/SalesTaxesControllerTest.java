package com.lm.challenge.taxes.controller;

import com.lm.challenge.taxes.controller.dto.input.BasketRQDTO;
import com.lm.challenge.taxes.controller.dto.output.BasketRSDTO;
import com.lm.challenge.taxes.controller.dto.transformer.SalesTaxesControllerTransformer;
import com.lm.challenge.taxes.service.SalesTaxesService;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class SalesTaxesControllerTest {

   @Mock
   private SalesTaxesControllerTransformer transformer;

   @Mock
   private SalesTaxesService service;

   @InjectMocks
   private SalesTaxesController controller;

    @Test
    public void shouldCalculateTaxesWhenBasketRQDTOIsValid() {
        //given
        InOrder inOrder = inOrder(transformer, service);
        BasketRQDTO basketRQDTO = mock(BasketRQDTO.class);
        BasketIDTO basketIDTO = mock(BasketIDTO.class);
        BasketODTO basketODTO = mock(BasketODTO.class);
        BasketRSDTO basketRSDTO = mock(BasketRSDTO.class);

        when(transformer.toIDTO(basketRQDTO)).thenReturn(basketIDTO);
        when(service.calculateTaxes(basketIDTO)).thenReturn(basketODTO);
        when(transformer.toRSDTO(basketODTO)).thenReturn(basketRSDTO);

        //when
        ResponseEntity<BasketRSDTO> response = controller.calculateTaxes(basketRQDTO);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(basketRSDTO, response.getBody());

        inOrder.verify(transformer).toIDTO(basketRQDTO);
        inOrder.verify(service).calculateTaxes(basketIDTO);
        inOrder.verify(transformer).toRSDTO(basketODTO);
        verifyNoMoreInteractions(transformer);
        verifyNoMoreInteractions(service);
    }

}