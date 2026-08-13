package com.raushan.strategy;

import com.raushan.model.User;

import java.util.List;
import java.util.Map;

public interface SplitStrategy {
    Map<User, Long> computeShares(long amount, List<User> participants, List<Long> values);
}
