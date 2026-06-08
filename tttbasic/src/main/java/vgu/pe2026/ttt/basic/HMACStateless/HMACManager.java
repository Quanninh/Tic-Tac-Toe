package vgu.pe2026.ttt.basic.HMACStateless;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HMACManager {
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String DEFAULT_SHARED_SECRET = "ttt-hmac-secure-secret";
    private final String sharedSecret;

    public HMACManager() {
        this.sharedSecret = getSharedSecret();
    }

    public String createHmac(String board, long nonce, long timestamp) {
        try {
            String message = String.format("%s|%d|%d", board, nonce, timestamp);
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec key = new SecretKeySpec(
                    sharedSecret.getBytes(StandardCharsets.UTF_8),
                    HMAC_ALGORITHM
            );
            mac.init(key);
            byte[] hmacBytes = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hmacBytes);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to create HMAC", e);
        }
    }

    public boolean verifyHmac(String board, long nonce, long timestamp, String providedHmac) {
        try {
            String expectedHmac = createHmac(board, nonce, timestamp);
            byte[] expectedBytes = expectedHmac.getBytes(StandardCharsets.UTF_8);
            byte[] providedBytes = providedHmac.getBytes(StandardCharsets.UTF_8);
            return constantTimeEquals(expectedBytes, providedBytes);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean constantTimeEquals(byte[] a, byte[] b) {
        int result = a.length ^ b.length;
        for (int i = 0; i < Math.min(a.length, b.length); i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }

    private String getSharedSecret() {
        String secret = System.getenv("TTT_HMAC_SECRET");
        if (secret == null || secret.isEmpty()) {
            return DEFAULT_SHARED_SECRET;
        }
        return secret;
    }
}
