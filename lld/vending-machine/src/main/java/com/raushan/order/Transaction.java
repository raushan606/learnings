package com.raushan.order;

public record Transaction(int id, int amount, int returnChange, TransactionState transactionState) {
}
