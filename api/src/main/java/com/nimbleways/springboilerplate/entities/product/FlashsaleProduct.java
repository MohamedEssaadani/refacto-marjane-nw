package com.nimbleways.springboilerplate.entities.product;

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
@DiscriminatorValue("FLASHSALE")
public class FlashsaleProduct extends Product {
    // flash sale start & end dates
    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
    // max quantity to sell
    @Column(name = "quantityToSell")
    private Integer quantityToSell;

}
