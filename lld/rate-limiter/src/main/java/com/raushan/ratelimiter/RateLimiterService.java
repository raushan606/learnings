package com.raushan.ratelimiter;

public class RateLimiterService {
    private static RateLimiterService instance;

    private final RateLimitingStrategy strategy;

    public static RateLimiterService getInstance(RateLimitingStrategy strategy) {
        if (instance == null) {
            return new RateLimiterService(strategy);
        }
        return instance;
    }

    public RateLimiterService(RateLimitingStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean allowRequest(String clientId) {
        if (strategy.allowRequest(clientId)) {
            System.out.println("Request allowed for client: " + clientId);
            return true;
        } else {
            System.out.println("Request denied for client: " + clientId);
            return false;
        }
    }
}
