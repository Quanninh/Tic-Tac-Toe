package vgu.pe2026.ttt.basic;

public abstract class Player{
    protected int id;

    public Player(int id){
        this.id = id;
    }

    public abstract String chooseMove(Board board);
}