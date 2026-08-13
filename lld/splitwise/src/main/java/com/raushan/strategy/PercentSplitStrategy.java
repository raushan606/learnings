package com.raushan.strategy;


import com.raushan.model.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PercentSplitStrategy implements SplitStrategy {
    public Map<User, Long> computeShares(long amount, List<User> participants, List<Long> values) {
        if (values == null || values.size() != participants.size())
            throw new IllegalArgumentException("PERCENT split needs one percent per participant");
        long pctSum = values.stream().mapToLong(Long::longValue).sum();
        if (pctSum != 100)
            throw new IllegalArgumentException("Percentages must add up to 100, got " + pctSum);
        Map<User, Long> shares = new LinkedHashMap<>();
        long assigned = 0;
        for (int i = 0; i < participants.size(); i++) {
            long share = amount * values.get(i) / 100;
            shares.put(participants.get(i), share);
            assigned += share;
        }
        long leftover = amount - assigned;                // spare cents from integer division
        if (leftover != 0) shares.merge(participants.get(0), leftover, Long::sum);
        return shares;
    }
}

