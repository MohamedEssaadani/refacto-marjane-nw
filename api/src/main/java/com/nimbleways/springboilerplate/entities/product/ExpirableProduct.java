package com.nimbleways.springboilerplate.entities.product;

import com.nimbleways.springboilerplate.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("EXPIRABLE")
public class ExpirableProduct extends Product {
    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    public ExpirableProduct(Long id, Integer leadTime, Integer available, ProductType type, String name, LocalDate expiryDate) {
        super(id, leadTime, available, type, name);
        this.expiryDate = expiryDate;
    }

    public ExpirableProduct() {
    }
}
