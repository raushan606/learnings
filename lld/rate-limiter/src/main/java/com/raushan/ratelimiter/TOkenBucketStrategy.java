package com.raushan.ratelimiter;

import java.util.concurrent.ConcurrentHashMap;

public class TOkenBucketStrategy implements RateLimitingStrategy {

    private final int capacity;
    ConcurrentHashMap<String, TokenBucketState> clientBuckets = new ConcurrentHashMap<>();

    public TOkenBucketStrategy(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean allowRequest(String clientId) {
        TokenBucketState bucketState = clientBuckets.computeIfAbsent(clientId, k -> new TokenBucketState(capacity, 1));
        bucketState.refillToken();
        synchronized (bucketState) {
            if (bucketState.tokens > 0) {
                bucketState.tokens--;
                return true;
            } else {
                return false;
            }
        }
    }

    private static class TokenBucketState {
        int tokens;
        long lastRefillTimestamp;
        final long refillRatePerSecond;
        final int capacity;

        TokenBucketState(int capacity, long refillRatePerSecond) {
            this.tokens = capacity;
            this.lastRefillTimestamp = System.currentTimeMillis();
            this.refillRatePerSecond = refillRatePerSecond;
            this.capacity = capacity;
        }

        public void refillToken() {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastRefillTimestamp;
            int tokensToAdd = (int) ((elapsedTime / 1000.0) * refillRatePerSecond);
            if (tokensToAdd > 0) {
                tokens = Math.min(capacity, tokens + tokensToAdd);
                lastRefillTimestamp = currentTime;
            }
        }
    }

}
