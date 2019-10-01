package com.lm.challenge.taxes.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductTypeExcludeMO {

    @Id
    private Integer id;

    private ProductType type;
}
