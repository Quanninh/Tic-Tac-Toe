package vgu.pe2026.ttt.basic;

import java.io.*;
import java.net.*;

public class StatelessServer {

    private static final int PORT = 5003;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Server listening on port " + PORT);

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

        StringBuilder boardBuilder = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            String line = in.readLine();

            if (line == null) {
                return;
            }

            boardBuilder.append(line).append("\n");
        }

        Board2D board = Board2D.fromString(boardBuilder.toString());

        if (board == null) {
            out.println("ERROR");
            return;
        }

        if (moveStr.equalsIgnoreCase("q")) {
            out.println("QUIT");
            return;
        }

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

    private static void sendBoard(
            PrintWriter out,
            Board2D board) {

        String[] lines =
                board.boardToString().split("\n");

        for (String line : lines) {
            out.println(line);
        }
    }
}