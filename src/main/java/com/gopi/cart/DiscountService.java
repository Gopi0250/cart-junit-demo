package com.gopi.cart;

public class DiscountService {

    /** Returns the total after applying the discount code. */
    public double applyDiscount(double total, String code) {
        if (total < 0) {
            throw new IllegalArgumentException("Total cannot be negative");
        }
        if (code == null || code.isBlank()) {
            return total; // no code, no discount
        }
        switch (code.trim().toUpperCase()) {
            case "SAVE10":
                return total * 0.90;
            case "SAVE20":
                return total * 0.80;
            default:
                throw new InvalidDiscountException(code);
        }
    }
}
