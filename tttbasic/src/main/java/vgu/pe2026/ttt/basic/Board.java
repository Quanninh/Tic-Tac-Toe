package vgu.pe2026.ttt.basic;

// make board abstract to implement even 1D or 2D board

public abstract class Board{
    public abstract void display(); 
    public abstract boolean isFree(int choice);
    public abstract boolean isFull();
    public abstract void move(int choice, int turn);
    public abstract int checkWin();
    public abstract String boardToString();
    //public abstract int getCell(int index);
}