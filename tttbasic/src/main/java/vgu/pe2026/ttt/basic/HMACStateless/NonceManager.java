package vgu.pe2026.ttt.basic.HMACStateless;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NonceManager {
    private static final long MOVE_TIMEOUT_MILLIS = 10000;
    private final Map<Long, Long> nonceTimestamps = new HashMap<>();
    private final Random random = new Random();

    public long generateNonce() {
        long nonce = random.nextLong();
        if (nonce == 0) {
            nonce = 1;
        }
        return Math.abs(nonce);
    }

    public void storeNonce(long nonce, long timestamp) {
        long expirationTime = timestamp + MOVE_TIMEOUT_MILLIS;
        nonceTimestamps.put(nonce, expirationTime);
        
        long currentTime = System.currentTimeMillis();
        nonceTimestamps.entrySet().removeIf(entry -> currentTime > entry.getValue());
    }

    public boolean isValidNonce(long nonce, long timestamp) {
        long now = System.currentTimeMillis();

        if (now - timestamp > MOVE_TIMEOUT_MILLIS) {
            return false;
        }

        if (nonceTimestamps.containsKey(nonce)) {
            return false;
        }

        return true;
    }
}
