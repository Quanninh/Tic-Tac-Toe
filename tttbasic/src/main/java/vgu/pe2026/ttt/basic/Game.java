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
        output.println("Hello!");
        if (output != null) {
            output.println(board.boardToString());
        }
        //board.display();

        boolean isQuit = false;
        while (!board.isFull() && board.checkWin() == 0){
            if (turn == HUMAN_PLAYER){
                //System.out.println("Player#" + turn + "'s turn");
                String choiceTemp = human.chooseMove(board);
                if (choiceTemp.equals("q")){
                    isQuit = true;
                    break;
                }
                try {
                    int choice = Integer.parseInt(choiceTemp);
                    if (choice > 0 && choice <= 9 && board.isFree(choice-1)){
                        board.move(choice-1, turn);
                        turn = COMPUTER_PLAYER;
                    }else if (!(choice > 0 && choice <= 9)){
                        output.println(board.boardToString());
                        output.println("Please, input a valid number [1-9]");
                    }
                    else{
                        output.println(board.boardToString());
                        output.println("The cell is occupied!");
                    }
                } catch (NumberFormatException e) {
                    output.println("Please, input a valid number [1-9]");
                }
            }else{
                //System.out.println("Player#" + turn + "'s turn");
                int choice = Integer.parseInt(computer.chooseMove(board));
                board.move(choice, turn);
                //System.out.println("Computer chose: " + (choice+1));
                turn = HUMAN_PLAYER;
                output.println(board.boardToString());
            }
            //board.display();
        }

        if (isQuit) {
            output.println("Game is finished");
            output.println("End of the game");
        } else {
            int win = board.checkWin();
            output.println("Game is finished");
            if (win == HUMAN_PLAYER){
                output.println("The winner is human");
            }else if (win == COMPUTER_PLAYER){
                output.println("The winner is computer");
            }else{
                output.println("Draw");
            }
        }
    }
}
