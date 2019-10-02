package com.lm.challenge.taxes.controller;

import com.lm.challenge.taxes.controller.dto.input.BasketRQDTO;
import com.lm.challenge.taxes.controller.dto.output.BasketRSDTO;
import com.lm.challenge.taxes.controller.dto.transformer.SalesTaxesControllerTransformer;
import com.lm.challenge.taxes.service.SalesTaxesService;
import com.lm.challenge.taxes.service.dto.input.BasketIDTO;
import com.lm.challenge.taxes.service.dto.output.BasketODTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Slf4j
public class SalesTaxesController {

    private static final String TAXES_PATH = "/taxes";

    private SalesTaxesControllerTransformer transformer;

    private SalesTaxesService service;

    @Autowired
    public SalesTaxesController (SalesTaxesControllerTransformer transformer,
                                 SalesTaxesService service){
        this.transformer = transformer;
        this.service = service;
    }

    @PostMapping(TAXES_PATH)
    public ResponseEntity<BasketRSDTO> calculateTaxes(@RequestBody BasketRQDTO basketRQDTO){
        log.debug("INIT calculate taxes with request: {}", basketRQDTO);
        BasketIDTO basketIDTO = transformer.toIDTO(basketRQDTO);
        BasketODTO basketODTO = service.calculateTaxes(basketIDTO);
        BasketRSDTO basketRSDTO = transformer.toRSDTO(basketODTO);
        log.debug("END calculate taxes to request: {}", basketRQDTO);
        return ResponseEntity.ok(basketRSDTO);
    }

}
