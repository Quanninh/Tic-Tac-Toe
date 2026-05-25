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
            int turn = 1;

            // send starting turn
            out.println(turn);

            while (true) {

                // receive board/message from server
                String response;

                while ((response = in.readLine()) != null) {

                    System.out.println(response);

                    if (!in.ready()) {
                        break;
                    }
                }

                // game over
                if (response.contains("winner") ||
                    response.contains("Draw") ||
                    response.contains("End of the game")) {
                        break;
                }

                // ask human move
                System.out.println(
                        "Choose a cell from 1 to 9 (or q to quit): ");

                String move = scanner.nextLine();

                out.println(move);
            }

            client.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}