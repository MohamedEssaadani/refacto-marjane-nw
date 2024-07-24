package com.nimbleways.springboilerplate.entities.product;

import com.nimbleways.springboilerplate.enums.ProductType;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("NORMAL")
public class NormalProduct extends Product {
    public NormalProduct(Long id, Integer leadTime, Integer available, ProductType type, String name) {
        super(id, leadTime, available, type, name);
    }

    public NormalProduct() {
    }
}
