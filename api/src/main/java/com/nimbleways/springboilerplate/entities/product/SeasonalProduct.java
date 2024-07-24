package com.nimbleways.springboilerplate.entities.product;

import com.nimbleways.springboilerplate.enums.ProductType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("SEASONAL")
public class SeasonalProduct extends Product {
    @Column(name = "season_start_date")
    private LocalDate seasonStartDate;

    @Column(name = "season_end_date")
    private LocalDate seasonEndDate;

    public SeasonalProduct(Long id, Integer leadTime, Integer available, ProductType type, String name, LocalDate seasonStartDate, LocalDate seasonEndDate) {
        super(id, leadTime, available, type, name);
        this.seasonStartDate = seasonStartDate;
        this.seasonEndDate = seasonEndDate;
    }

}
