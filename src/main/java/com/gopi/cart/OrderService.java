package com.gopi.cart;

public class OrderService {

    private final PaymentGateway paymentGateway;
    private final DiscountService discountService;

    public OrderService(PaymentGateway paymentGateway, DiscountService discountService) {
        this.paymentGateway = paymentGateway;
        this.discountService = discountService;
    }

    /** Charges the discounted total and returns a confirmation message. */
    public String checkout(ShoppingCart cart, String discountCode) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cannot check out an empty cart");
        }
        double finalAmount = discountService.applyDiscount(cart.getTotal(), discountCode);
        boolean paid = paymentGateway.charge(finalAmount);
        if (!paid) {
            throw new IllegalStateException("Payment failed");
        }
        return String.format("Order placed. Amount charged: %.2f", finalAmount);
    }
}
