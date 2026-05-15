package com.raushan.strategy;

public class CoinPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Paying " + amount + " using coins.");
        // Logic to process coin payment
    }
}
