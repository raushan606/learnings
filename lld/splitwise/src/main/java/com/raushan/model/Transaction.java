package com.raushan.model;

public record Transaction(User from, User to, long cents) {
    @Override
    public String toString() {
        return from + " pays " + to + " " + Money.fmt(cents);
    }
}
