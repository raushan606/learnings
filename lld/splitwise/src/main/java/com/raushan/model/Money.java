package com.raushan.model;

public final class Money {
    private Money() {}
    public static String fmt(long cents) {                       // \u20b9 is the ₹ rupee sign
        return String.format("\u20b9%d.%02d", cents / 100, Math.abs(cents % 100));
    }
}
