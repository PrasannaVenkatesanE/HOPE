    
    public class TokenBucketRateLimiter {
    private final long maxTokens;
    private final long refillIntervalMillis;
    private final long tokensPerRefill;

    private long availableTokens;
    private long lastRefillTimestamp;

    public TokenBucketRateLimiter(long maxTokens, long tokensPerRefill, long refillIntervalMillis) {
        if (maxTokens <= 0 || tokensPerRefill <= 0 || refillIntervalMillis <= 0) {
            throw new IllegalArgumentException("All parameters must be positive.");
        }

        this.maxTokens = maxTokens;
        this.tokensPerRefill = tokensPerRefill;
        this.refillIntervalMillis = refillIntervalMillis;
        this.availableTokens = maxTokens;
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    public synchronized boolean tryConsume(long tokens) {
        refillTokens();
        if (tokens <= 0) {
            throw new IllegalArgumentException("Requested tokens must be positive.");
        }
        if (availableTokens >= tokens) {
            availableTokens -= tokens;
            return true;
        }
        return false;
    }

    private void refillTokens() {
        long now = System.currentTimeMillis();
        if (now <= lastRefillTimestamp) {
            return;
        }

        long elapsedMillis = now - lastRefillTimestamp;
        long intervals = elapsedMillis / refillIntervalMillis;
        if (intervals > 0) {
            long tokensToAdd = intervals * tokensPerRefill;
            availableTokens = Math.min(maxTokens, availableTokens + tokensToAdd);
            lastRefillTimestamp += intervals * refillIntervalMillis;
        }
    }

    public synchronized long getAvailableTokens() {
        refillTokens();
        return availableTokens;
    }

    public long getMaxTokens() {
        return maxTokens;
    }

    public long getRefillIntervalMillis() {
        return refillIntervalMillis;
    }

    public long getTokensPerRefill() {
        return tokensPerRefill;
    }
}
