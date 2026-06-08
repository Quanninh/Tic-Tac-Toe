package vgu.pe2026.ttt.basic.HMACStateless;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import vgu.pe2026.ttt.basic.Board2D;

public class HMACStatelessClient {
    private static final String HOST = "localhost";
    private static final int PORT = 5005;
    private static String lastMessageStr;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board2D board = null;
        System.out.println("Hello!");

        try (Socket socket = new Socket(HOST, PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("START");
            String status = in.readLine();

            if ("START".equals(status)) {
                lastMessageStr = in.readLine();
                String boardStr = parseBoard(lastMessageStr);

                if (boardStr != null) {
                    board = Board2D.fromString(boardStr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            scanner.close();
            return;
        }

        while (true) {
            System.out.println("Current board:");
            board.display();
            System.out.print("Choose a cell from 1 to 9 (or q to quit): ");

            String move = scanner.nextLine().trim();

            try (Socket socket = new Socket(HOST, PORT);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                out.println(move);

                if (!move.equalsIgnoreCase("q")) {
                    out.println(lastMessageStr);
                }
                System.out.println();

                String status = in.readLine();

                if (status == null) {
                    System.out.println("Server disconnected");
                    break;
                }

                if ("QUIT".equals(status)) {
                    System.out.println("Game is finished");
                    System.out.println("End of the game");
                    break;
                }

                if ("INVALID_SIGNATURE".equals(status)) {
                    System.out.println("Board signature is invalid");
                    break;
                }

                if ("EXPIRED_NONCE".equals(status)) {
                    System.out.println("Your move took too long (10 seconds exceeded)");
                    break;
                }

                lastMessageStr = in.readLine();
                String boardStr = parseBoard(lastMessageStr);

                if (boardStr == null) {
                    System.out.println("Server sent an invalid message");
                    break;
                }

                if ("INVALID".equals(status)) {
                    System.out.println("Please, input a valid number [1-9]");
                    continue;
                }

                if ("OCCUPIED".equals(status)) {
                    System.out.println("The cell is occupied!");
                    continue;
                }


                board = Board2D.fromString(boardStr);

                if (board == null) {
                    System.out.println("Server sent an invalid board");
                    break;
                }

                if ("HUMAN_WIN".equals(status)) {
                    board.display();
                    System.out.println("Game is finished");
                    System.out.println("The winner is human");
                    break;
                }

                if ("COMPUTER_WIN".equals(status)) {
                    board.display();
                    System.out.println("Game is finished");
                    System.out.println("The winner is computer");
                    break;
                }

                if ("DRAW".equals(status)) {
                    board.display();
                    System.out.println("Game is finished");
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

    private static String parseBoard(String serialized) {
        if (serialized == null || !serialized.startsWith("BOARD:")) {
            return null;
        }
        try {
            String[] parts = serialized.split("\\|");
            if (parts.length != 4)
                return null;
            return "BOARD:" + parts[0].substring(6);
        } catch (Exception e) {
            return null;
        }
    }
}
