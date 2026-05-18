package vgu.pe2026.ttt.basic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
// import java.io.ByteArrayOutputStream;
// import java.io.PrintStream;
// import java.util.*;

public class MachineTest{

    // Verify that Machine selects the first available free cell on a Board1D
    @Test
    void testChooseMoveBoard1D(){
        Board board = new Board1D();
        board.move(0, 1);
        board.move(1, 1);

        Machine machine = new Machine();
        assertEquals(2, Integer.parseInt(machine.chooseMove(board)));
    }

    // Verify that Machine works correctly with Board2D and selects the first free cell
    @Test
    void testChooseMoveBoard2D(){
        Board board = new Board2D();
        board.move(0, 1);
        board.move(1, 1);

        Machine machine = new Machine();
        assertEquals(2, Integer.parseInt(machine.chooseMove(board)));
    }
}