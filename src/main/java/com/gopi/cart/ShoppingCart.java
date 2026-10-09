package com.gopi.cart;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCart {

    // product name -> cart item (keeps insertion order)
    private final Map<String, CartItem> items = new LinkedHashMap<>();

    public void addProduct(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }
        CartItem existing = items.get(product.getName());
        if (existing != null) {
            existing.addQuantity(quantity);
        } else {
            items.put(product.getName(), new CartItem(product, quantity));
        }
    }

    public void removeProduct(String productName) {
        if (!items.containsKey(productName)) {
            throw new IllegalArgumentException("Product not in cart: " + productName);
        }
        items.remove(productName);
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getSubtotal();
        }
        return total;
    }

    public int getTotalQuantity() {
        int count = 0;
        for (CartItem item : items.values()) {
            count += item.getQuantity();
        }
        return count;
    }

    public int getDistinctItemCount() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
