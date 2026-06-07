package vgu.pe2026.ttt.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SecureStatelessServer {

    private static final int PORT = 5004;
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String MESSAGE_PREFIX = "HMAC:";
    private static final String DEFAULT_SHARED_SECRET = "ttt-basic-shared-secret";

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Secure server listening on port " + PORT);

            while (true) {

                Socket client = serverSocket.accept();

                System.out.println("Client connected");

                handleRequest(client);

                client.close();

                System.out.println("Connection closed");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(Socket client) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        String moveStr = in.readLine();

        if (moveStr == null) {
            return;
        }

        if (moveStr.equalsIgnoreCase("q")) {
            out.println("QUIT");
            return;
        }

        String signedBoard = in.readLine();

        if (!verifySignedBoard(signedBoard)) {
            out.println("INVALID_SIGNATURE");
            return;
        }

        Board2D board = Board2D.fromString(extractBoard(signedBoard));

        // if (board == null) {
        //     out.println("ERROR");
        //     return;
        // }

        try {

            int move = Integer.parseInt(moveStr);

            if (move < 1 || move > 9) {
                out.println("INVALID");
                return;
            }

            if (!board.isFree(move - 1)) {
                out.println("OCCUPIED");
                return;
            }

            board.move(move - 1, 1);

            if (board.checkWin() == 1) {

                out.println("HUMAN_WIN");

                sendBoard(out, board);

                return;
            }

            if (board.isFull()) {

                out.println("DRAW");

                sendBoard(out, board);

                return;
            }

            Machine machine = new Machine();

            int computerMove = Integer.parseInt(machine.chooseMove(board));

            board.move(computerMove, 2);

            if (board.checkWin() == 2) {

                out.println("COMPUTER_WIN");

                sendBoard(out, board);

                return;
            }

            if (board.isFull()) {

                out.println("DRAW");

                sendBoard(out, board);

                return;
            }

            out.println("CONTINUE");

            sendBoard(out, board);

        } catch (NumberFormatException e) {

            out.println("INVALID");
        }
    }

    private static void sendBoard(PrintWriter out, Board2D board) {

        String boardString = board.boardToString();

        out.println(signBoard(boardString));
    }

    private static String signBoard(String boardString) {
        return MESSAGE_PREFIX + createHmac(boardString) + ":" + boardString;
    }

    private static boolean verifySignedBoard(String signedBoard) {
        String boardString = extractBoard(signedBoard);

        if (boardString == null) {
            return false;
        }

        String expected = signBoard(boardString);
        byte[] expectedBytes = expected.getBytes(StandardCharsets.UTF_8);
        byte[] actualBytes = signedBoard.getBytes(StandardCharsets.UTF_8);

        return MessageDigest.isEqual(expectedBytes, actualBytes);
    }

    private static String extractBoard(String signedBoard) {
        if (signedBoard == null || !signedBoard.startsWith(MESSAGE_PREFIX)) {
            return null;
        }

        int boardStart = signedBoard.indexOf(":BOARD:");

        if (boardStart == -1) {
            return null;
        }

        return signedBoard.substring(boardStart + 1);
    }

    private static String createHmac(String message) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec key = new SecretKeySpec(sharedSecret().getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
            mac.init(key);
            return Base64.getEncoder().encodeToString(mac.doFinal(message.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException("Unable to create HMAC", e);
        }
    }

    private static String sharedSecret() {
        String secret = System.getenv("TTT_HMAC_SECRET");

        if (secret == null || secret.isEmpty()) {
            return DEFAULT_SHARED_SECRET;
        }

        return secret;
    }
}
