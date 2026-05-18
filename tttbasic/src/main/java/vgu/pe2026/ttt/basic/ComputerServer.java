package vgu.pe2026.ttt.basic;

import java.net.*;
import java.io.*;

public class ComputerServer {
    private static final int HUMAN_PLAYER = 1;
    private static final int COMPUTER_PLAYER = 2;
    public static void main(String[] args){
        try{
            ServerSocket ss = new ServerSocket(5001);

            while(true){
                Machine computer = new Machine();
                Board2D board = new Board2D();

                Socket server = ss.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                PrintWriter out = new PrintWriter(server.getOutputStream(), true);
                int turn = Integer.parseInt(in.readLine());
                while(!board.isFull() && board.checkWin() == 0){
                    if (turn == HUMAN_PLAYER){
                        String clientMove = in.readLine();

                        if (clientMove.contains("q")){
                            break;
                        }
                        int cMove = Integer.parseInt(clientMove);
                        if (cMove > 0 && cMove <= 9 && board.isFree(cMove-1)){
                            board.move(cMove - 1, turn);;
                            turn = COMPUTER_PLAYER;
                        }else if (cMove <= 0 || cMove > 9){
                            out.println("Please, input a valid number [1-9]");
                        }else{
                            out.println("The cell is occupied!");
                        }
                    }else if (turn == COMPUTER_PLAYER){
                        System.out.println("Player#" + turn + "'s turn");
                        int computerMove = Integer.parseInt(computer.chooseMove(board));
                        board.move(computerMove, turn);
                        System.out.println("Computer chose: " + (computerMove+1));
                        turn = HUMAN_PLAYER;
                        out.println(board.boardToString());
                        board.display();
                    }
                }
                int win = board.checkWin();
                System.out.println("Game is finished");
                out.println("Game is finished");
                if (win == HUMAN_PLAYER){
                    System.out.println("The winner is human");
                    out.println("The winner is human");
                }else if (win == COMPUTER_PLAYER){
                    System.out.println("The winner is computer");
                    out.println("The winner is computer");
                }else if (win == 0 && board.isFull()){
                    System.out.println("Draw");
                    out.println("Draw");
                }else{
                    out.println("End of the Game");
                    System.out.println("End of the Game");
                }
                server.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
