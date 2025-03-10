import org.example.Order;
import org.example.OrderRepository;
import org.example.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository);
    }

    @Test
    void processOrder_Succes() {
        Order order = new Order(1, "Laptop", 2, 150000);
        when(orderRepository.saveOrder(order)).thenReturn(1);
        String result = orderService.processOrder(order);
        assertEquals("Order processed successfully with ID: 1", result);
        verify(orderRepository, times(1)).saveOrder(order);
    }

    @Test
    void processOrder_Failure() {
        Order order = new Order(1, "Laptop", 2, 1500.00);
        when(orderRepository.saveOrder(order)).thenThrow(new RuntimeException("Database error"));
        String result = orderService.processOrder(order);
        assertEquals("Order processing failed", result);
        verify(orderRepository, times(1)).saveOrder(order);
    }

    @Test
    void calculateTotal_Success() {
        Order order = new Order(1, "Laptop", 3, 100.0); // id и productName добавлены
        double total = orderService.calculateTotal(order);
        assertEquals(300.0, total);
    }

    @Test
    void calculateTotal_OrderNotFound() {
        Order order = null;
        double total = orderService.calculateTotal(order);
        assertEquals(0.0, total);
    }

    @Test
    void calculateTotal_ZeroQuantityOrPrice() {
        // Дано: заказ с quantity = 0 или unitPrice = 0.0
        Order orderWithZeroQuantity = new Order(1, "Laptop", 0, 100.0);
        Order orderWithZeroPrice = new Order(2, "Laptop", 3, 0.0);
        Order orderWithBothZero = new Order(3, "Laptop", 0, 0.0);

        double totalForZeroQuantity = orderService.calculateTotal(orderWithZeroQuantity);
        double totalForZeroPrice = orderService.calculateTotal(orderWithZeroPrice);
        double totalForBothZero = orderService.calculateTotal(orderWithBothZero);

        assertEquals(0.0, totalForZeroQuantity);
        assertEquals(0.0, totalForZeroPrice);
        assertEquals(0.0, totalForBothZero);
    }
}
