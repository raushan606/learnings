package com.raushan;

import com.raushan.ratelimiter.RateLimiterService;
import com.raushan.ratelimiter.RateLimitingStrategy;
import com.raushan.ratelimiter.TOkenBucketStrategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        String userId = "user123";

        System.out.println("\n=== Token Bucket Demo ===");
        runTokenBucketDemo(userId);

        // TODO: Implement Leaky Bucket Demo
        // TODO: Implement Sliding Window Demo
        // TODO: Implement Fixed Window Demo
        // TODO: Implement Sliding Window Counter Demo
    }

    private static void runTokenBucketDemo(String userId) {
        int capacity = 5;
        RateLimitingStrategy tokenBucketStrategy = new TOkenBucketStrategy(capacity);
        RateLimiterService service = RateLimiterService.getInstance(tokenBucketStrategy);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        // Simulate 10 rapid requests
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> service.allowRequest(userId));
            try {
                Thread.sleep(300); // faster than refill rate
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        executor.shutdown();
    }
}
