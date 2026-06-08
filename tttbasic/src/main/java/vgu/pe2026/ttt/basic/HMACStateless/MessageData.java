package vgu.pe2026.ttt.basic.HMACStateless;

public class MessageData {
    String board;
    long nonce;
    long timestamp;
    String hmacTag;

    MessageData(String board, long nonce, long timestamp, String hmacTag) {
        this.board = board;
        this.nonce = nonce;
        this.timestamp = timestamp;
        this.hmacTag = hmacTag;
    }

    public String getBoard(){
        return this.board;
    }

    public long getNonce(){
        return this.nonce;
    }

    public long getTimeStamp(){
        return this.timestamp;
    }

    public String getHMACTag(){
        return this.hmacTag;
    }

    public static MessageData deserializeMessage(String serialized) {
        if (serialized == null || !serialized.startsWith("BOARD:")) {
            return null;
        }

        try {
            String[] parts = serialized.split("\\|");
            if (parts.length != 4) return null;

            String board = "BOARD:" + parts[0].substring(6);
            long nonce = Long.parseLong(parts[1].substring(6));
            long timestamp = Long.parseLong(parts[2].substring(10));
            String hmacTag = parts[3].substring(5);

            return new MessageData(board, nonce, timestamp, hmacTag);
        } catch (Exception e) {
            return null;
        }
    }

    public static String serializeMessage(String board, long nonce, long timestamp, String hmacTag) {
        return String.format("BOARD:%s|NONCE:%d|TIMESTAMP:%d|HMAC:%s",
                board.substring(6), nonce, timestamp, hmacTag);
    }
}
