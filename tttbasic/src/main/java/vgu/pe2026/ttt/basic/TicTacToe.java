package vgu.pe2026.ttt.basic;

import java.util.*;

// Refactor this main code, using threads
public class TicTacToe {
    public static void main(String[] args){
        Board board = new Board2D();
        Scanner keyboard = new Scanner(System.in);
        Human human = new Human(keyboard);
        Machine computer = new Machine();

        int turn;
        try{
            if (args.length != 1 || !(args[0].equals("1") || args[0].equals("2"))){
                System.out.println("Please input valid option [1-2]");
                return;
            }
            turn = Integer.parseInt(args[0]);
        }catch(Exception e){
            System.out.println("Please input valid option [1-2]");
            return;
        }

        if (turn != 1 && turn != 2){
            System.out.println("Please input valid option [1-2]");
            return;
        }
        
        Game game = new Game(board, human, computer, turn);
        game.play();
    }
}