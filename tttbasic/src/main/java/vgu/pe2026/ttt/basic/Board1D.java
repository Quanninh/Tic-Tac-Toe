package vgu.pe2026.ttt.basic;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Board1D extends Board{
    private int[] cells;
    private PrintStream printer;

    public Board1D(ByteArrayOutputStream out){
        this.printer = new PrintStream(out);

        cells = new int[9];

        for (int i = 0; i < 9; i++){
            cells[i] = 0;
        }
    }

    public Board1D(){
        this.printer = new PrintStream(System.out);        
        cells = new int[9];

        for (int i = 0; i < 9; i++){
            cells[i] = 0;
        }
    }

    @Override
    public void display(){
        for (int i = 0; i < 9; i++){
            // Define get(index) to replace cells[i]
            printer.print("| " + cells[i] + " ");
            if ((i + 1) % 3 == 0){
                printer.println("|");
            }
        }
    }

    @Override
    public String boardToString(){
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < 9; i++){
            sb.append("| ").append(cells[i]).append(" ");

            if ((i + 1) % 3 == 0){
                sb.append("|\n");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean isFree(int choice){
        return cells[choice] == 0;
    }

    @Override
    public boolean isFull(){
        for (int i = 0; i < 9; i++){
            if (cells[i] == 0){
                return false;
            }
        }
        return true;
    }

    @Override
    public void move(int choice, int turn){
        cells[choice] = turn;
    }

    @Override
    public int checkWin(){
        int[][] winPatterns = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

        for (int[] patterns : winPatterns){
            if (cells[patterns[0]] != 0 && cells[patterns[0]] == cells[patterns[1]] && 
            cells[patterns[1]] == cells[patterns[2]]){
                return cells[patterns[0]];
            }
        }
        return 0;
    }
}