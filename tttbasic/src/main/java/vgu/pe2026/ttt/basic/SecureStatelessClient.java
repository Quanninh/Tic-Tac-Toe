package vgu.pe2026.ttt.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SecureStatelessClient {

    private static final String HOST = "localhost";
    private static final int PORT = 5004;
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String MESSAGE_PREFIX = "HMAC:";
    private static final String DEFAULT_SHARED_SECRET = "ttt-basic-shared-secret";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Board2D board = new Board2D();

        System.out.println("Hello!");
        while (true) {
            System.out.println("Current board:");
            board.display();

            System.out.print("Choose a cell from 1 to 9 (or q to quit): ");

            String move = scanner.nextLine().trim();

            try (
                    Socket socket = new Socket(HOST, PORT);

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                out.println(move);

                if (!move.equalsIgnoreCase("q")) {
                    out.println(signBoard(board.boardToString()));
                }

                String status = in.readLine();
                System.out.println();

                if (status == null) {
                    System.out.println("Server disconnected");
                    break;
                }

                if (status.equals("QUIT")) {
                    System.out.println("Game is finished");
                    System.out.println("End of the game");
                    break;
                }

                if (status.equals("INVALID_SIGNATURE")) {
                    System.out.println("Board signature is invalid");
                    break;
                }

                if (status.equals("INVALID")) {
                    System.out.println("Please, input a valid number [1-9]");
                    continue;
                }

                if (status.equals("OCCUPIED")) {
                    System.out.println("The cell is occupied!");
                    continue;
                }

                String signedBoard = in.readLine();

                if (!verifySignedBoard(signedBoard)) {
                    System.out.println("Server board signature is invalid");
                    break;
                }

                board = Board2D.fromString(extractBoard(signedBoard));

                if (board == null) {
                    System.out.println("Server sent an invalid board");
                    break;
                }

                if (status.equals("HUMAN_WIN")) {
                    board.display();
                    System.out.println("Game is finished");
                    System.out.println("The winner is human");

                    break;
                }

                if (status.equals("COMPUTER_WIN")) {
                    board.display();
                    System.out.println("Game is finished");
                    System.out.println("The winner is computer");

                    break;
                }

                if (status.equals("DRAW")) {

                    System.out.println("Draw");

                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        scanner.close();
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
