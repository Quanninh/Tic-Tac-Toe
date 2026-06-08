package vgu.pe2026.ttt.basic.Stateless;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import vgu.pe2026.ttt.basic.Board2D;

public class StatelessClient {

    private static final String HOST = "localhost";
    private static final int PORT = 5003;
    private static String lastBoard = null;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Board2D board = null;
        System.out.println("Hello!");

        try(
            Socket socket = new Socket(HOST, PORT);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ){
            out.println("START");

            String status = in.readLine();
            if (status.equals("START")){
                String boardStr = in.readLine();
                lastBoard = boardStr;
                board = Board2D.fromString(boardStr);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

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
                    out.println(lastBoard);
                    //out.println("BOARD:1,1,0,0,0,0,0,0,0");
                }
                System.out.println();

                String status = in.readLine();

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

                String boardStr = in.readLine();
                lastBoard = boardStr;

                board = Board2D.fromString(boardStr);

                

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
}