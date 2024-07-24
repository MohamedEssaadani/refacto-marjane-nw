package com.nimbleways.springboilerplate.services.implementations;

import com.nimbleways.springboilerplate.dto.product.ProcessOrderResponse;
import com.nimbleways.springboilerplate.exceptions.OrderNotFoundException;

public interface OrderService {
    ProcessOrderResponse processOrder(Long orderId) throws OrderNotFoundException;
}
