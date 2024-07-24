package com.nimbleways.springboilerplate.services.product;

import com.nimbleways.springboilerplate.entities.product.*;

public interface IProductService {
    void notifyDelay(int leadTime, Product product);

    void handleSeasonalProduct(SeasonalProduct product);

    void handleExpirableProduct(ExpirableProduct product);
    void handleFlashsaleProduct(FlashsaleProduct product);

    void processProduct(Product product);

    void handleNormalProduct(NormalProduct product);
}
