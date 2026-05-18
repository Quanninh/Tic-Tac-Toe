package vgu.pe2026.ttt.basic;

public class Game {
    private Board board;
    private Human human;
    private Machine computer;
    private int turn;

    private static final int HUMAN_PLAYER = 1;
    private static final int COMPUTER_PLAYER = 2;

    public Game(Board board, Human human, Machine computer, int turn){
        this.board = board;
        this.human = human;
        this.computer = computer;
        this.turn = turn;
    }
    
    public void play(){
        System.out.println("Hello!");
        board.display();

        //Thread gameThread = new Thread(() -> {
            while (!board.isFull() && board.checkWin() == 0){
                if (turn == HUMAN_PLAYER){
                    System.out.println("Player#" + turn + "'s turn");
                    System.out.println("Choose a cell from 1 to 9: ");
                    String choiceTemp = human.chooseMove(board);
                    if (choiceTemp.equals("q")){
                        System.out.println("End of the game");
                        return;
                    }
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
                }else{
                    System.out.println("Player#" + turn + "'s turn");
                    int choice = Integer.parseInt(computer.chooseMove(board));
                    board.move(choice, turn);
                    System.out.println("Computer chose: " + (choice+1));
                    turn = HUMAN_PLAYER;
                }
                board.display();
            }
            int win = board.checkWin();
            System.out.println("Game is finished");
            if (win == HUMAN_PLAYER){
                System.out.println("The winner is human");
            }else if (win == COMPUTER_PLAYER){
                System.out.println("The winner is computer");
            }else{
                System.out.println("Draw");
            }
        //});
        //gameThread.start();
    }
}
