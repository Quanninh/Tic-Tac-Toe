package vgu.pe2026.ttt.basic;

import java.io.*;

public class Game {
    private Board board;
    private Player human;
    private Machine computer;
    private int turn;
    private PrintWriter output;

    private static final int HUMAN_PLAYER = 1;
    private static final int COMPUTER_PLAYER = 2;

    public Game(Board board, Player human, Machine computer, int turn) {
        this(board, human, computer, turn, null);
    }

    public Game(Board board, Player human, Machine computer, int turn, PrintWriter output) {
        this.board = board;
        this.human = human;
        this.computer = computer;
        this.turn = turn;
        this.output = output;
    }
    
    public void play(){
        if (output != null) {
            output.println(board.boardToString());
        }
        System.out.println("Hello!");
        board.display();

        boolean isQuit = false;
        while (!board.isFull() && board.checkWin() == 0){
            if (turn == HUMAN_PLAYER){
                System.out.println("Player#" + turn + "'s turn");
                String choiceTemp = human.chooseMove(board);
                if (choiceTemp.equals("q")){
                    isQuit = true;
                    break;
                }
                if (choiceTemp.equals("-1")) {
                    System.out.println("Invalid input!");
                    continue;
                }
                try {
                    int choice = Integer.parseInt(choiceTemp);
                    if (choice > 0 && choice <= 9 && board.isFree(choice-1)){
                        board.move(choice-1, turn);
                        turn = COMPUTER_PLAYER;
                    }else if (!(choice > 0 && choice <= 9)){
                        System.out.println("Please, input a valid number [1-9]");
                    }
                    else{
                        System.out.println("The cell is occupied!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please, input a valid number [1-9]");
                }
            }else{
                System.out.println("Player#" + turn + "'s turn");
                int choice = Integer.parseInt(computer.chooseMove(board));
                board.move(choice, turn);
                System.out.println("Computer chose: " + (choice+1));
                turn = HUMAN_PLAYER;
                send(board.boardToString());
            }
            board.display();
        }

        if (isQuit) {
            send("Game is finished");
            send("End of the game");
        } else {
            int win = board.checkWin();
            send("Game is finished");
            if (win == HUMAN_PLAYER){
                send("The winner is human");
            }else if (win == COMPUTER_PLAYER){
                send("The winner is computer");
            }else{
                send("Draw");
            }
        }
    }

    private void send(String message) {
        if (output != null) {
            output.println(message);
        }
    }
}
