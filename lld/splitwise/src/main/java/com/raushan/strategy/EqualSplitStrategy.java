package com.raushan.strategy;

import com.raushan.model.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EqualSplitStrategy implements SplitStrategy {
    public Map<User, Long> computeShares(long amount, List<User> participants, List<Long> values) {
        int n = participants.size();
        long base = amount / n;
        long remainder = amount - base * n;               // leftover cents (0..n-1)
        Map<User, Long> shares = new LinkedHashMap<>();
        for (int i = 0; i < n; i++)
            shares.put(participants.get(i), base + (i < remainder ? 1 : 0));
        return shares;
    }
}
