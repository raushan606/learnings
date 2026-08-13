package com.raushan.service;

import com.raushan.model.SplitType;
import com.raushan.strategy.EqualSplitStrategy;
import com.raushan.strategy.ExactSplitStrategy;
import com.raushan.strategy.PercentSplitStrategy;
import com.raushan.strategy.SplitStrategy;

public class SplitStrategyFactory {
    public static SplitStrategy of(SplitType type) {
        return switch (type) {
            case EQUAL -> new EqualSplitStrategy();
            case EXACT -> new ExactSplitStrategy();
            case PERCENT -> new PercentSplitStrategy();
        };
    }
}
