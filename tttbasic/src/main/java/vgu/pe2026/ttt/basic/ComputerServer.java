package vgu.pe2026.ttt.basic;

import java.net.*;
import java.io.*;

public class ComputerServer {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(5001);
            System.out.println("Server started on port 5001");

            while (true) {
                Socket socket = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                try {
                    int turn = Integer.parseInt(in.readLine());

                    Board board = new Board2D();
                    Player human = new NetworkHuman(in);
                    Machine computer = new Machine();

                    Game game = new Game(board, human, computer, turn, out);
                    game.play();
                } finally {
                    socket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
