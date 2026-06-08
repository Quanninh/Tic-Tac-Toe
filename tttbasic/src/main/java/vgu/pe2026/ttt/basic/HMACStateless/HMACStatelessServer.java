package vgu.pe2026.ttt.basic.HMACStateless;

import java.io.*;
import java.net.*;

import vgu.pe2026.ttt.basic.Board2D;
import vgu.pe2026.ttt.basic.Machine;

public class HMACStatelessServer {
    private static final int PORT = 5005;
    private static final HMACManager hmacManager = new HMACManager();
    private static final NonceManager nonceManager = new NonceManager();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("HMAC Stateless Server listening on port " + PORT);

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

        String request = in.readLine();

        if (request == null) {
            return;
        }

        if ("START".equals(request)) {
            sendInitialBoard(out);
            return;
        }

        if ("q".equalsIgnoreCase(request)) {
            out.println("QUIT");
            return;
        }

        handleMove(in, out, request);
    }

    private static void sendInitialBoard(PrintWriter out) {
        Board2D board = new Board2D();
        long nonce = nonceManager.generateNonce();
        long timestamp = System.currentTimeMillis();
        String boardStr = board.boardToString();
        String hmacTag = hmacManager.createHmac(boardStr, nonce, timestamp);

        out.println("START");
        out.println(MessageData.serializeMessage(boardStr, nonce, timestamp, hmacTag));
    }

    private static void handleMove(BufferedReader in, PrintWriter out, String moveStr)
            throws IOException {
        String gameMessageStr = in.readLine();

        if (gameMessageStr == null) {
            out.println("ERROR");
            return;
        }

        MessageData messageData = MessageData.deserializeMessage(gameMessageStr);
        if (messageData == null) {
            out.println("ERROR");
            return;
        }

        if (!hmacManager.verifyHmac(messageData.getBoard(), messageData.getNonce(),
                messageData.getTimeStamp(), messageData.getHMACTag())) {
            out.println("INVALID_SIGNATURE");
            return;
        }

        if (!nonceManager.isValidNonce(messageData.getNonce(), messageData.getTimeStamp())) {
            out.println("EXPIRED_NONCE");
            return;
        }

        nonceManager.storeNonce(messageData.getNonce(), messageData.getTimeStamp());

        Board2D board = Board2D.fromString(messageData.getBoard());
        if (board == null) {
            out.println("ERROR");
            return;
        }

        try {
            int move = Integer.parseInt(moveStr);

            if (move < 1 || move > 9) {
                out.println("INVALID");
                sendBoard(out, board);
                return;
            }

            if (!board.isFree(move - 1)) {
                out.println("OCCUPIED");
                sendBoard(out, board);
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
        long newNonce = nonceManager.generateNonce();
        long newTimestamp = System.currentTimeMillis();
        String boardStr = board.boardToString();
        String hmacTag = hmacManager.createHmac(boardStr, newNonce, newTimestamp);

        out.println(MessageData.serializeMessage(boardStr, newNonce, newTimestamp, hmacTag));
    }

}
