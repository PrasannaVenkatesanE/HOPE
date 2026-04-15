public class TokenBucketDemo {
    public static void main(String[] args) throws InterruptedException {
        // Create a rate limiter with 5 tokens max, refill 1 token every second.
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(5, 1, 1000);

        System.out.println("Starting token bucket demo...");
        for (int i = 1; i <= 10; i++) {
            boolean allowed = limiter.tryConsume(1);
            System.out.printf("Request %2d: %s (available=%d)%n", i, allowed ? "ALLOWED" : "REJECTED", limiter.getAvailableTokens());
            Thread.sleep(500);
        }

        System.out.println("\nWaiting 3 seconds to refill tokens...");
        Thread.sleep(3000);
        System.out.printf("Available tokens after wait: %d%n", limiter.getAvailableTokens());

        System.out.println("Trying 3 more requests after refill...");
        for (int i = 1; i <= 3; i++) {
            boolean allowed = limiter.tryConsume(1);
            System.out.printf("After wait request %d: %s (available=%d)%n", i, allowed ? "ALLOWED" : "REJECTED", limiter.getAvailableTokens());
        }
    }
}
