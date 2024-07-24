package com.nimbleways.springboilerplate.services.order;

import com.nimbleways.springboilerplate.dto.product.ProcessOrderResponse;
import com.nimbleways.springboilerplate.entities.Order;
import com.nimbleways.springboilerplate.entities.product.Product;
import com.nimbleways.springboilerplate.exceptions.OrderNotFoundException;
import com.nimbleways.springboilerplate.repositories.OrderRepository;
import com.nimbleways.springboilerplate.services.product.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceImplUnitTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderServiceImpl orderService;

    public OrderServiceImplUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldProcessProducts() throws OrderNotFoundException {
        // Arrange
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        Product product = mock(Product.class);
        Set<Product> products = new HashSet<>();
        products.add(product);
        order.setItems(products);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        ProcessOrderResponse response = orderService.processOrder(orderId);

        // Assert
        verify(orderRepository).findById(orderId);
        verify(productService).processProduct(product);
        assertEquals(orderId, response.id());
    }
}
