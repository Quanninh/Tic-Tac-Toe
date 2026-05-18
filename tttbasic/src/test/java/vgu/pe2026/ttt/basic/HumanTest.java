package vgu.pe2026.ttt.basic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
// import java.io.ByteArrayOutputStream;
// import java.io.PrintStream;
import java.util.*;

public class HumanTest{
    // Test the chooseMove() method return a correct value or not with Board 1D
    @Test
    void testChooseMoveBoard1D(){
        Scanner scanner = new Scanner("5" + System.lineSeparator());
        Human human = new Human(scanner);

        int move = Integer.parseInt(human.chooseMove(new Board1D()));
        assertEquals(5, move);
    }

    // Refactor the code chooseMoveTest()
    // Test the chooseMove() method return a correct value or not with Board 2D
    @Test
    void testChooseMoveBoard2D(){
        Scanner scanner = new Scanner("5" + System.lineSeparator());
        Human human = new Human(scanner);

        int move = Integer.parseInt(human.chooseMove(new Board2D()));
        assertEquals(5, move);
    }

    // Refactor this code
    // Test the chooseMove() method, which receive the invalid input - a string - with board 1D
    @Test
    void testInvalidInputMoveBoard1D(){
        Scanner scanner = new Scanner("abc" + System.lineSeparator());
        Human human = new Human(scanner);

        int move = Integer.parseInt(human.chooseMove(new Board1D()));
        assertEquals(-1, move);
    }


    // Test the chooseMove() method, which receive the invalid input - a string - with board 2D
    @Test
    void testInvalidInputMoveBoard2D(){
        Scanner scanner = new Scanner("abc" + System.lineSeparator());
        Human human = new Human(scanner);

        int move = Integer.parseInt(human.chooseMove(new Board2D()));
        assertEquals(-1, move);
    }

    @Test
    void testQuitGame(){
        Scanner scanner = new Scanner("q" + System.lineSeparator());
        Human human = new Human(scanner);

        String move = human.chooseMove(new Board2D());
        assertEquals("q", move);
    }
}