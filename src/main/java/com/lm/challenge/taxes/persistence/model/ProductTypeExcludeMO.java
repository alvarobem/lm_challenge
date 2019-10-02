package com.lm.challenge.taxes.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeExcludeMO {

    @Id
    private Integer id;

    private ProductType type;
}
