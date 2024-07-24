package com.nimbleways.springboilerplate.services.order;

import com.nimbleways.springboilerplate.dto.product.ProcessOrderResponse;
import com.nimbleways.springboilerplate.entities.Order;
import com.nimbleways.springboilerplate.entities.product.Product;
import com.nimbleways.springboilerplate.exceptions.OrderNotFoundException;
import com.nimbleways.springboilerplate.repositories.OrderRepository;
import com.nimbleways.springboilerplate.services.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Override
    public ProcessOrderResponse processOrder(Long orderId) throws OrderNotFoundException {
        // Retrieve the order by id
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        // Check if the order exists
        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException("Order not found! ");
        }

        Order order = optionalOrder.get();
        Set<Product> products = order.getItems();

        // Check if there are any products to process
        if (products == null || products.isEmpty()) {
            return new ProcessOrderResponse(order.getId());
        }

        // Process each product
        for (Product product : products) {
            try {
                productService.processProduct(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ProcessOrderResponse(order.getId());
    }
}
