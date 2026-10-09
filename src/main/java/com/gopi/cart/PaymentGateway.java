package com.gopi.cart;

/** In a real app this would call a payment provider. Here it is just an interface to mock in tests. */
public interface PaymentGateway {

    boolean charge(double amount);
}
