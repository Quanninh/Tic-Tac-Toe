package vgu.pe2026.ttt.basic;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class StatelessClient {

    private static final String HOST = "localhost";
    private static final int PORT = 5003;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Board2D board = new Board2D();

        while (true) {

            System.out.println();
            System.out.println("Current board:");
            board.display();

            System.out.print("Choose a cell from 1 to 9 (or q to quit): ");

            String move = scanner.nextLine().trim();

            try (
                    Socket socket = new Socket(HOST, PORT);

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                out.println(move);

                out.println(board.boardToString());

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

                if (status.equals("INVALID")) {
                    System.out.println("Please, input a valid number [1-9]");
                    continue;
                }

                if (status.equals("OCCUPIED")) {
                    System.out.println("The cell is occupied!");
                    continue;
                }


                board = Board2D.fromString(in.readLine());

                // System.out.println("Board after server move:");

                // board.display();

                if (status.equals("HUMAN_WIN")) {

                    System.out.println("The winner is human");

                    break;
                }

                if (status.equals("COMPUTER_WIN")) {

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
}