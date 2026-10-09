package com.gopi.cart;

public class InvalidDiscountException extends RuntimeException {

    public InvalidDiscountException(String code) {
        super("Invalid discount code: " + code);
    }
}
