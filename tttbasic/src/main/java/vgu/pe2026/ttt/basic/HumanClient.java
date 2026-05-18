package vgu.pe2026.ttt.basic;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class HumanClient {

    private static final int HUMAN_PLAYER = 1;
    private static final int COMPUTER_PLAYER = 2;

    public static void main(String[] args) {

        try {

            Socket client = new Socket("localhost", 5001);

            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(client.getInputStream()));

            PrintWriter out =
                    new PrintWriter(client.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);

            System.out.println("Connected to server.");

            // choose starting player
            int turn;

            while (true) {

                System.out.println(
                        "Enter first turn (1 = Human, 2 = Computer): ");

                turn = scanner.nextInt();

                if (turn == HUMAN_PLAYER ||
                    turn == COMPUTER_PLAYER) {
                    break;
                }

                System.out.println("Invalid turn.");
            }

            scanner.nextLine();

            // send starting turn
            out.println(turn);

            while (true) {

                // human sends move first
                if (turn == HUMAN_PLAYER) {

                    System.out.println(
                            "Choose a cell from 1 to 9 (or q to quit): ");

                    String move = scanner.nextLine();

                    out.println(move);

                    if (move.equalsIgnoreCase("q")) {
                        break;
                    }
                }

                // receive server response
                String response;

                while ((response = in.readLine()) != null) {

                    System.out.println(response);

                    if (response.contains("Game is finished") ||
                        response.contains("winner") ||
                        response.contains("Draw") ||
                        response.contains("End of the Game")) {

                        client.close();
                        return;
                    }

                    // stop after board received
                    if (!in.ready()) {
                        break;
                    }
                }

                // after server responds,
                // human turn comes again
                turn = HUMAN_PLAYER;
            }

            client.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}