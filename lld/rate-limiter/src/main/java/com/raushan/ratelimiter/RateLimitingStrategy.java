package com.raushan.ratelimiter;

public interface RateLimitingStrategy {
    boolean allowRequest(String clientId);
}
