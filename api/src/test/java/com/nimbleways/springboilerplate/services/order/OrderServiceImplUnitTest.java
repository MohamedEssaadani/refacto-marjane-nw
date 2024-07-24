package com.nimbleways.springboilerplate.services.order;

class OrderServiceImplUnitTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderServiceImpl orderService;

    public OrderServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void processOrder_ShouldProcessProducts_WhenOrderExistsWithProducts() throws OrderNotFoundException {
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
        assertEquals(orderId, response.getOrderId());
    }
}
