package vgu.pe2026.ttt.basic;

import java.net.*;
import java.io.*;

public class MultiUserServer {
    private static final int PORT = 5002;
    private static int clientCounter = 0;

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(PORT);
            System.out.println("Multi-user server started on port " + PORT);

            while (true) {
                Socket socket = ss.accept();
                clientCounter++;
                int clientId = clientCounter;
                System.out.println("Client #" + clientId + " connected");

                Thread gameThread = new Thread(new GameSession(socket, clientId));
                gameThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class GameSession implements Runnable {
        private Socket socket;
        private int clientId;

        public GameSession(Socket socket, int clientId) {
            this.socket = socket;
            this.clientId = clientId;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                int turn = Integer.parseInt(in.readLine());

                Board board = new Board2D();
                Player human = new NetworkHuman(in);
                Machine computer = new Machine();

                System.out.println("Client #" + clientId + " game started");
                Game game = new Game(board, human, computer, turn, out);
                game.play();

                System.out.println("Client #" + clientId + " game ended");
            } catch (Exception e) {
                System.out.println("Client #" + clientId + " error: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                    System.out.println("Client #" + clientId + " disconnected");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
