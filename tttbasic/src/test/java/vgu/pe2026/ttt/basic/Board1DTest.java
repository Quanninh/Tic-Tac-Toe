package vgu.pe2026.ttt.basic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;

public class Board1DTest{
    // Verify that isFull() returns true when all cells are occupied
    @Test
    void isFullBoard1DTest(){
        Board1D board = new Board1D();
        for (int i = 0; i < 9; i++){
            board.move(i, 1);
        }
        assertTrue(board.isFull());
    }

    // Verify that isFull() returns false when at least one cell is empty
    @Test
    void isNotFullBoard1DTest(){
        Board1D board = new Board1D();
        for (int i = 0; i < 5; i++){
            board.move(i, 2);
        }

        for (int j = 5; j < 9; j++){
            board.move(j, 0);
        }

        assertFalse(board.isFull());
    }

    // Verify that checkWin() returns the correct player when a winning condition is met
    @Test
    void checkWinTest(){
        Board1D board = new Board1D();
        board.move(0, 1);
        board.move(3, 1);
        board.move(6, 1);
        assertEquals(1, board.checkWin());
    }

    // Verify that checkWin() returns 0 when there is no winner
    @Test
    void checkNotWinTest(){
        Board1D board = new Board1D();
        board.move(0, 1);
        board.move(3, 2);
        board.move(6, 1);
        assertEquals(0, board.checkWin());
    }

    // Verify that isFree() returns true for an empty cell
    @Test
    void checkIsFree(){
        Board1D board = new Board1D();
        for (int i = 0; i < 6; i++){
            board.move(i, 1);
        }
        assertTrue(board.isFree(7));
    }

    // Verify that isFree() returns false for an occupied cell
    @Test
    void checkIsNotFree(){
        Board1D board = new Board1D();
        for (int i = 0; i < 6; i++){
            board.move(i, 1);
        }
        assertFalse(board.isFree(2));
    }

    // Verify that move() correctly updates the board state
    @Test
    void checkMoveUpdateCell(){
        Board1D board = new Board1D();
        board.move(1, 1);
        assertFalse(board.isFree(1));
    }

    // Verify that display() prints the board in the correct format 
    @Test
    void checkDisplay(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Board1D board = new Board1D(output);
        board.move(0, 1);

        board.display();
        String printed = output.toString();
        String expected =
            "| 1 | 0 | 0 |" + System.lineSeparator()+
            "| 0 | 0 | 0 |" + System.lineSeparator()+
            "| 0 | 0 | 0 |" + System.lineSeparator();

        assertEquals(expected, printed);
    }

    // Verify that display() output does not match an incorrect format 
    @Test
    void checkInvalidDisplay(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Board1D board = new Board1D(output);
        board.move(0, 1);

        board.display();
        String printed = output.toString();
        String expected =
            "| 1 | 0 | 0 |" + System.lineSeparator() +
            "| 0 | 0 | 0 |" + System.lineSeparator();

        assertNotEquals(expected, printed);
    }

    @Test
    void intialBoardTest(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Board board = new Board1D(output);
        for (int i = 0; i < 9; i++){
            board.move(i, i+1);
        }
        
        board.display();

        String[] lines = output.toString().split(System.lineSeparator());

        assertEquals(3, lines.length);
        assertEquals("| 1 | 2 | 3 |", lines[0]);
        assertEquals("| 4 | 5 | 6 |", lines[1]);
        assertEquals("| 7 | 8 | 9 |", lines[2]);
    }
}