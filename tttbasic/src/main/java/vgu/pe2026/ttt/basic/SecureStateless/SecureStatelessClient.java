package vgu.pe2026.ttt.basic.SecureStateless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import vgu.pe2026.ttt.basic.Board;
import vgu.pe2026.ttt.basic.Board2D;

public class SecureStatelessClient {

    private static final String HOST = "localhost";
    private static final int PORT = 5004;
    private static String lastSignedBoard;
    private static final String MESSAGE_PREFIX = "HMAC:";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Board board = null;
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
                lastSignedBoard = boardStr;
                board = Board2D.fromString(extractBoard(boardStr));
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
                    out.println(lastSignedBoard);
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
                lastSignedBoard = signedBoard;

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
}
