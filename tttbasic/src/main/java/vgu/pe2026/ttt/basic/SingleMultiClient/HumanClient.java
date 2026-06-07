package vgu.pe2026.ttt.basic.SingleMultiClient;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import vgu.pe2026.ttt.basic.Board;
import vgu.pe2026.ttt.basic.Board2D;

public class HumanClient {

    public static void main(String[] args) {

        try {

            Socket client = new Socket("localhost", 5002);

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
                    if (response.startsWith("BOARD:")){
                        Board board = Board2D.fromString(response);
                        board.display();
                    }else{
                        System.out.println(response);
                    }

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