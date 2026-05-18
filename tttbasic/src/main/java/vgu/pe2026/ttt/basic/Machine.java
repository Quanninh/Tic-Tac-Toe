package vgu.pe2026.ttt.basic;
//import java.util.*;

public class Machine extends Player{
    //private Random random;

    public Machine(){
        super(2);
    }

    // public int chooseMove(Board2D board){
    //     System.out.println("Computer turn");
    //     int[] freeCells = new int[9];
    //     int count = 0;
    //     for (int i = 0; i < 9; i++){
    //         if (board.isFree(i)){
    //             freeCells[count] = i + 1;
    //             count++;
    //         }
    //     }
        
    //     int randomIndex = random.nextInt(count);
    //     return freeCells[randomIndex];
    // }
    public String chooseMove(Board board){
        int move = 0;

        while (!board.isFree(move) && move < 9){
            move++;
        }

        if(move == 9){
            throw new IllegalStateException("No move left");
        }
        return String.valueOf(move);
    }
}