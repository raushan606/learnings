package com.raushan.strategy;

import com.raushan.model.Money;
import com.raushan.model.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExactSplitStrategy implements SplitStrategy {
    @Override
    public Map<User, Long> computeShares(long amount, List<User> participants, List<Long> values) {
        if (values == null || values.size() != participants.size())
            throw new IllegalArgumentException("EXACT split needs one amount per participant");
        long sum = values.stream().mapToLong(Long::longValue).sum();
        if (sum != amount)
            throw new IllegalArgumentException("Exact amounts " + Money.fmt(sum)
                    + " must sum to the total " + Money.fmt(amount));
        Map<User, Long> shares = new LinkedHashMap<>();
        for (int i = 0; i < participants.size(); i++)
            shares.put(participants.get(i), values.get(i));
        return shares;
    }
}
