package com.gopi.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DiscountServiceTest {

    private final DiscountService service = new DiscountService();

    @ParameterizedTest(name = "total {0} with code {1} -> {2}")
    @CsvSource({
            "1000.0, SAVE10, 900.0",
            "1000.0, SAVE20, 800.0",
            "1000.0, save10, 900.0"
    })
    void validCodesApplyDiscount(double total, String code, double expected) {
        assertEquals(expected, service.applyDiscount(total, code), 0.001);
    }

    @Test
    void noCodeMeansNoDiscount() {
        assertEquals(1000.0, service.applyDiscount(1000.0, null), 0.001);
        assertEquals(1000.0, service.applyDiscount(1000.0, "  "), 0.001);
    }

    @Test
    void unknownCodeThrowsCustomException() {
        InvalidDiscountException ex =
                assertThrows(InvalidDiscountException.class, () -> service.applyDiscount(1000.0, "FREE100"));
        assertEquals("Invalid discount code: FREE100", ex.getMessage());
    }

    @Test
    void negativeTotalThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.applyDiscount(-1, "SAVE10"));
    }
}
