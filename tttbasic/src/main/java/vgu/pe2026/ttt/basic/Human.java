package vgu.pe2026.ttt.basic;

import java.util.*;
public class Human extends Player{
    private Scanner keyboard;

    public Human(Scanner keyboard){
        super(1);
        this.keyboard = keyboard;
    }

    @Override
    public String chooseMove(Board board){
        if (!keyboard.hasNextLine()) {
            return "-1"; 
        }

        String moveCheck = keyboard.nextLine();
        if (isInteger(moveCheck)){
            return moveCheck;
        }else if (moveCheck.equals("q")){
            return "q";
        }
        else{
            return "-1";
        }
    }

    public static boolean isInteger(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
}
