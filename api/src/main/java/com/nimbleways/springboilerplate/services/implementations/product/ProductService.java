package com.nimbleways.springboilerplate.services.implementations.product;

import java.time.LocalDate;

import com.nimbleways.springboilerplate.entities.product.*;
import com.nimbleways.springboilerplate.services.implementations.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimbleways.springboilerplate.repositories.ProductRepository;


@Service
public class ProductService implements IProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    NotificationService notificationService;

    public void notifyDelay(int leadTime, Product p) {
        p.setLeadTime(leadTime);
        productRepository.save(p);
        notificationService.sendDelayNotification(leadTime, p.getName());
    }

    @Override
    public void handleSeasonalProduct(SeasonalProduct product) {
        LocalDate now = LocalDate.now();
        LocalDate seasonStartDate = product.getSeasonStartDate();
        LocalDate seasonEndDate = product.getSeasonEndDate();

        // Update product availability
        if (now.isAfter(seasonStartDate) && now.isBefore(seasonEndDate)) {
            if (product.getAvailable() > 0) {
                product.setAvailable(product.getAvailable() - 1);
            }
        } else if (now.plusDays(product.getLeadTime()).isAfter(seasonEndDate)) {
            product.setAvailable(0);
        }

        // notification out of stock or delay of product
        boolean isOutOfStock = now.plusDays(product.getLeadTime()).isAfter(seasonEndDate);
        boolean isBeforeSeasonStart = now.isBefore(seasonStartDate);

        if (isOutOfStock || isBeforeSeasonStart) {
            notificationService.sendOutOfStockNotification(product.getName());
        } else {
            notifyDelay(product.getLeadTime(), product);
        }

        productRepository.save(product);
    }

    @Override
    public void handleExpirableProduct(ExpirableProduct product) {
        if (product.getAvailable() > 0 && product.getExpiryDate().isAfter(LocalDate.now())) {
            product.setAvailable(product.getAvailable() - 1);
        } else {
            notificationService.sendExpirationNotification(product.getName(), product.getExpiryDate());
            product.setAvailable(0);
        }

        productRepository.save(product);
    }


    @Override
    public void handleFlashsaleProduct(FlashsaleProduct product) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = product.getStartDate();
        LocalDate endDate = product.getEndDate();

        // check for product in period & is still available
        boolean isInFlashSalePeriod = now.isAfter(startDate) && now.isBefore(endDate);
        boolean isOutOfStock = now.isAfter(endDate) || product.getQuantityToSell() == 0;

        if (isInFlashSalePeriod && product.getAvailable() > 0 && product.getQuantityToSell() > 0) {
            product.setAvailable(product.getAvailable() - 1);
            product.setQuantityToSell(product.getQuantityToSell() - 1);
        } else if (isOutOfStock) {
            product.setAvailable(0);
        }

        // manage notifications
        if (now.isAfter(endDate)) {
            notificationService.sendExpirationNotification(product.getName(), endDate);
        } else if (product.getQuantityToSell() == 0) {
            notificationService.sendOutOfStockNotification(product.getName());
        }

        // save changes
        productRepository.save(product);
    }


    @Override
    public void processProduct(Product product) {
        switch (product.getType()) {
            case NORMAL:
                if (product instanceof NormalProduct) {
                    handleNormalProduct((NormalProduct) product);
                } else {
                    throw new IllegalArgumentException("Unexpected product type: " + product.getClass().getName());
                }
                break;
            case SEASONAL:
                if (product instanceof SeasonalProduct) {
                    handleSeasonalProduct((SeasonalProduct) product);
                } else {
                    throw new IllegalArgumentException("Unexpected product type: " + product.getClass().getName());
                }
                break;
            case EXPIRABLE:
                if (product instanceof ExpirableProduct) {
                    handleExpirableProduct((ExpirableProduct) product);
                } else {
                    throw new IllegalArgumentException("Unexpected product type: " + product.getClass().getName());
                }
                break;
            case FLASHSALE:
                if (product instanceof FlashsaleProduct) {
                    handleFlashsaleProduct((FlashsaleProduct) product);
                } else {
                    throw new IllegalArgumentException("Unexpected product type: " + product.getClass().getName());
                }
                break;
            default:
                throw new IllegalArgumentException("Unexpected product type: " + product.getType());
        }
    }

    @Override
    public void handleNormalProduct(NormalProduct product) {
        if (product.getAvailable() > 0) {
            product.setAvailable(product.getAvailable() - 1);
        } else {
            int leadTime = product.getLeadTime();
            if (leadTime > 0) {
                notifyDelay(leadTime, product);
            }
        }
        productRepository.save(product);
    }
}
