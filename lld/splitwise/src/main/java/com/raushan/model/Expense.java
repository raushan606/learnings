package com.raushan.model;

import java.util.Map;

public record Expense(String description, SplitType type, long amountCents,
                      User paidBy, Map<User, Long> shares) {
}