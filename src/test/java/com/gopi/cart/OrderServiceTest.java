package com.gopi.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private PaymentGateway paymentGateway;

    private OrderService orderService;
    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        // real DiscountService, mocked PaymentGateway
        orderService = new OrderService(paymentGateway, new DiscountService());
        cart = new ShoppingCart();
    }

    @Test
    void successfulCheckoutChargesDiscountedAmount() {
        cart.addProduct(new Product("Keyboard", 1000.00), 1);
        when(paymentGateway.charge(900.00)).thenReturn(true);

        String message = orderService.checkout(cart, "SAVE10");

        assertEquals("Order placed. Amount charged: 900.00", message);
        verify(paymentGateway).charge(900.00);
    }

    @Test
    void emptyCartIsRejectedBeforeAnyPayment() {
        assertThrows(IllegalStateException.class, () -> orderService.checkout(cart, null));
        verify(paymentGateway, never()).charge(anyDouble());
    }

    @Test
    void failedPaymentThrows() {
        cart.addProduct(new Product("Mouse", 500.00), 2);
        when(paymentGateway.charge(1000.00)).thenReturn(false);

        IllegalStateException ex =
                assertThrows(IllegalStateException.class, () -> orderService.checkout(cart, null));
        assertEquals("Payment failed", ex.getMessage());
    }
}
