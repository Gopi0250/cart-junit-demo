package com.gopi.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShoppingCartTest {

    private ShoppingCart cart;
    private Product keyboard;
    private Product mouse;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
        keyboard = new Product("Keyboard", 1500.00);
        mouse = new Product("Mouse", 500.00);
    }

    @Test
    @DisplayName("New cart is empty and totals zero")
    void newCartIsEmpty() {
        assertTrue(cart.isEmpty());
        assertEquals(0.0, cart.getTotal(), 0.001);
    }

    @Test
    @DisplayName("Adding products updates the total")
    void addProductsUpdatesTotal() {
        cart.addProduct(keyboard, 1);
        cart.addProduct(mouse, 2);

        assertEquals(2500.00, cart.getTotal(), 0.001);
        assertEquals(3, cart.getTotalQuantity());
        assertEquals(2, cart.getDistinctItemCount());
    }

    @Test
    @DisplayName("Adding the same product twice merges the quantity")
    void sameProductMergesQuantity() {
        cart.addProduct(mouse, 1);
        cart.addProduct(mouse, 3);

        assertEquals(1, cart.getDistinctItemCount());
        assertEquals(4, cart.getTotalQuantity());
        assertEquals(2000.00, cart.getTotal(), 0.001);
    }

    @Test
    @DisplayName("Zero or negative quantity is rejected")
    void invalidQuantityThrows() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> cart.addProduct(mouse, 0));
        assertEquals("Quantity must be at least 1", ex.getMessage());
        assertThrows(IllegalArgumentException.class, () -> cart.addProduct(mouse, -2));
    }

    @Test
    @DisplayName("Removing a product lowers the total")
    void removeProduct() {
        cart.addProduct(keyboard, 1);
        cart.addProduct(mouse, 1);

        cart.removeProduct("Keyboard");

        assertEquals(500.00, cart.getTotal(), 0.001);
        assertFalse(cart.isEmpty());
    }

    @Test
    @DisplayName("Removing a product that is not in the cart throws")
    void removeMissingProductThrows() {
        assertThrows(IllegalArgumentException.class, () -> cart.removeProduct("Laptop"));
    }

    @Test
    @DisplayName("Product with a negative price is rejected")
    void negativePriceThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Cable", -10));
    }
}
