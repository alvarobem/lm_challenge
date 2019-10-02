package com.lm.challenge.taxes.persistence.repository;

import com.lm.challenge.taxes.persistence.model.ProductType;
import com.lm.challenge.taxes.persistence.model.ProductTypeExcludeMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductTypeExcludeRepository extends JpaRepository<ProductTypeExcludeMO, Integer> {

    Optional<ProductTypeExcludeMO> findByType(ProductType type);
}
