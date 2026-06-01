package vgu.pe2026.ttt.basic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Board2D extends Board{
    private int[][] cells;
    private PrintStream printer;

    public Board2D(ByteArrayOutputStream out){
        this.printer = new PrintStream(out);
        cells = new int[3][3];

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                cells[i][j] = 0;
            }
        }
    }

    public Board2D(){
        this.printer = new PrintStream(System.out);
        cells = new int[3][3];

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                cells[i][j] = 0;
            }
        }
    }
    @Override
    public void display(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                printer.print("| " + cells[i][j] + " ");
            }
            printer.print("|");
            printer.print("\n");
        }
    }

    @Override
    public String boardToString(){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                sb.append("| ").append(cells[i][j]).append(" ");
            }
            sb.append("|\n");
        }
        return sb.toString();
    }

    @Override
    public boolean isFree(int choice){
        int row = choice / 3;
        int col = choice % 3;
        return cells[row][col] == 0;
    }

    @Override 
    public boolean isFull(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (cells[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void move(int choice, int turn){
        int row = choice / 3;
        int col = choice % 3;
        cells[row][col] = turn;
    }

    @Override
    public int checkWin(){
        for (int i = 0; i < 3; i++){
            if (cells[i][0] != 0 && cells[i][0] == cells[i][1] && cells[i][1] == cells[i][2]){
                return cells[i][0];
            }if (cells[0][i] != 0 && cells[0][i] == cells[1][i] && cells[1][i] == cells[2][i]){
                return cells[0][i];
            }
        }

        if (cells[0][0] != 0 && cells[0][0] == cells[1][1] && cells[1][1] == cells[2][2]){
            return cells[0][0];
        }if (cells[0][2] != 0 && cells[0][2] == cells[1][1] && cells[1][1] == cells[2][0]){
            return cells[0][2];
        }
        return 0;
    }

    public static Board2D fromString(String boardStr) {
        Board2D board = new Board2D();
        String[] lines = boardStr.split("\n");
        if (lines.length < 3) return null;

        for (int i = 0; i < 3; i++) {
            String line = lines[i].trim();
            String[] parts = line.split("\\|");
            if (parts.length < 4) return null;

            for (int j = 0; j < 3; j++) {
                try {
                    int value = Integer.parseInt(parts[j + 1].trim());
                    board.cells[i][j] = value;
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return board;
    }
}