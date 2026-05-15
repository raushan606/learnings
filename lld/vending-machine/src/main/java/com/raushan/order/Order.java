package com.raushan.order;

public class Order {
    private final int orderId;
    private final int itemId;
    private final int quantity;
    private final int totalAmount;
    private Transaction transaction;

    public Order(int orderId, int itemId, int quantity, int totalAmount) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.transaction = createTransaction();
    }

    private Transaction createTransaction() {
        return new Transaction(orderId, totalAmount, 0, TransactionState.PENDING);
    }

    public void returnChangeAmount(int returnChange) {
        this.transaction = new Transaction(orderId, totalAmount, returnChange, TransactionState.SUCCESS);
    }
}
