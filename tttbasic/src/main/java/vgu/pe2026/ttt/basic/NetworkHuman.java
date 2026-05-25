package vgu.pe2026.ttt.basic;

import java.io.*;

public class NetworkHuman extends Player {
    private BufferedReader input;

    public NetworkHuman(BufferedReader input) {
        super(1);
        this.input = input;
    }

    @Override
    public String chooseMove(Board board) {
        try {
            String moveCheck = input.readLine();
            if (moveCheck == null) {
                return "-1";
            }
            return moveCheck;
        } catch (IOException e) {
            return "-1";
        }
    }
}
