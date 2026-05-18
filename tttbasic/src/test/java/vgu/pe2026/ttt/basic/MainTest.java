package vgu.pe2026.ttt.basic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.*;


public class MainTest {
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private PipedOutputStream outputStream;
    private BufferedReader scanner;

   @BeforeEach
   void setUp() { 
       
    //     outputStream = new PipedOutputStream();
    //     try {
    //         PipedInputStream inputStream = new PipedInputStream(outputStream); // Connect in constructor
    //         scanner = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    //     } catch (IOException ex) {
    //         Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
    //     }

    //    System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() { 
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void emptyOptionTest()throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TicTacToe.main(new String[]{});
        byte[] printout = outputByteArray.toByteArray();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                assertEquals("Please input valid option [1-2]", reader.readLine());
                assertNull(reader.readLine());
        }
    }

    @Test 
    void startGameWithHuman() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "q" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);

        TicTacToe.main(new String[]{"1"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                assertEquals("Hello!", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("Player#1's turn", reader.readLine());
                assertEquals("Choose a cell from 1 to 9: ", reader.readLine());
        }
    }

    @Test
    void startGameWithComputer() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "q" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);

        TicTacToe.main(new String[]{"2"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                assertEquals("Hello!", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("Player#2's turn", reader.readLine());
                skipLine(1, reader);
        }
    }

    @Test
    void invalidStartArgument1() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TicTacToe.main(new String[]{"3"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                assertEquals("Please input valid option [1-2]", reader.readLine());
        }
    }

    @Test
    void invalidStartArgument2() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TicTacToe.main(new String[]{"0"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                assertEquals("Please input valid option [1-2]", reader.readLine());
        }
    }

    @Test
    void invalidStartArgument3() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TicTacToe.main(new String[]{"-1"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                assertEquals("Please input valid option [1-2]", reader.readLine());
        }
    }

    @Test
    void invalidStartArgument4() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TicTacToe.main(new String[]{"abc"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                assertEquals("Please input valid option [1-2]", reader.readLine());
        }
    }

    @Test
    void checkExactArgument1() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TicTacToe.main(new String[]{"01"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                assertEquals("Please input valid option [1-2]", reader.readLine());
        }
    }

    @Test
    void checkExactArgument2() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TicTacToe.main(new String[]{"1", "2"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                assertEquals("Please input valid option [1-2]", reader.readLine());
        }
    }

    @Test
    void checkExactArgument3() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TicTacToe.main(new String[]{" 1"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                assertEquals("Please input valid option [1-2]", reader.readLine());
        }
    }

    @Test
    void boardRender() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "5" + System.lineSeparator() + "q" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);
        TicTacToe.main(new String[]{"1"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                assertEquals("Hello!", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                
                skipLine(2, reader);
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 1 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                skipLine(7, reader);
        }

    }

    @Test
    void validHumanMove() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "5" + System.lineSeparator() + "q" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);
        TicTacToe.main(new String[]{"1"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                skipLine(1, reader);
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                
                assertEquals("Player#1's turn", reader.readLine());
                assertEquals("Choose a cell from 1 to 9: ", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 1 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("Player#2's turn", reader.readLine());
                skipLine(6, reader);
        }
    }

    @Test
    void invalidInput() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "abc" + System.lineSeparator() + "@" + System.lineSeparator() + " " + System.lineSeparator() + "q" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);
        TicTacToe.main(new String[]{"1"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                skipLine(6, reader);
                assertEquals("Please, input a valid number [1-9]", reader.readLine());
                skipLine(3, reader);
                assertEquals("Player#1's turn", reader.readLine());
                assertEquals("Choose a cell from 1 to 9: ", reader.readLine());

                assertEquals("Please, input a valid number [1-9]", reader.readLine());
                skipLine(3, reader);
                assertEquals("Player#1's turn", reader.readLine());
                assertEquals("Choose a cell from 1 to 9: ", reader.readLine());

                assertEquals("Please, input a valid number [1-9]", reader.readLine());
                skipLine(3, reader);
                assertEquals("Player#1's turn", reader.readLine());
                assertEquals("Choose a cell from 1 to 9: ", reader.readLine());
            }
    }

    @Test
    void quitGame() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "q" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);
        TicTacToe.main(new String[]{"1"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                skipLine(6, reader);
                assertEquals("End of the game", reader.readLine());
            }
    }

    @Test
    void verify_q() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "Q" + System.lineSeparator() + " q" + System.lineSeparator() + "q " + System.lineSeparator() + "q" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);
        TicTacToe.main(new String[]{"1"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                skipLine(6, reader);
                assertEquals("Please, input a valid number [1-9]", reader.readLine());
                skipLine(3, reader);
                assertEquals("Player#1's turn", reader.readLine());

                skipLine(1, reader);
                assertEquals("Please, input a valid number [1-9]", reader.readLine());
                skipLine(3, reader);
                assertEquals("Player#1's turn", reader.readLine());

                skipLine(1, reader);
                assertEquals("Please, input a valid number [1-9]", reader.readLine());
                skipLine(3, reader);
                assertEquals("Player#1's turn", reader.readLine());
        }
    } 

    @Test
    void checkInputOutOfRange() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "0" + System.lineSeparator() + "10" + System.lineSeparator() + "-3" + System.lineSeparator() + "q" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);
        TicTacToe.main(new String[]{"1"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                skipLine(6, reader);
                assertEquals("Please, input a valid number [1-9]", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("Player#1's turn", reader.readLine());
                skipLine(1, reader);

                assertEquals("Please, input a valid number [1-9]", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("Player#1's turn", reader.readLine());
                skipLine(1, reader);

                assertEquals("Please, input a valid number [1-9]", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("Player#1's turn", reader.readLine());
                skipLine(1, reader);
            }
    }

    @Test
    void checkOccupiedCell() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "1" + System.lineSeparator() + "1" + System.lineSeparator() + "q" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);
        TicTacToe.main(new String[]{"1"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                skipLine(16, reader);
                assertEquals("The cell is occupied!", reader.readLine());
                skipLine(3, reader);
                assertEquals("Player#1's turn", reader.readLine());
                assertEquals("Choose a cell from 1 to 9: ", reader.readLine());
        }
    }

    @Test
    void checkHumanWinColumn() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "2" + System.lineSeparator() + "5" + System.lineSeparator() + "8" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);
        TicTacToe.main(new String[]{"2"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                skipLine(35, reader);
                assertEquals("The winner is human", reader.readLine());
            }
    }

    @Test
    void checkHumanWinRow() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "7" + System.lineSeparator() + "3" + System.lineSeparator() + "8" + System.lineSeparator() + "9" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);
        TicTacToe.main(new String[]{"2"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                skipLine(45, reader);
                assertEquals("The winner is human", reader.readLine());
            }
    }

    @Test
    void checkHumanWinDiagonal() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "3" + System.lineSeparator() + "5" + System.lineSeparator() + "7" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);
        TicTacToe.main(new String[]{"2"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                skipLine(35, reader);
                assertEquals("The winner is human", reader.readLine());
            }
    }

    @Test
    void checkComputerWin() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "4" + System.lineSeparator() + "5" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);
        TicTacToe.main(new String[]{"2"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                skipLine(30, reader);
                assertEquals("The winner is computer", reader.readLine());
            }
    }

    @Test
    void checkDraw() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "4" + System.lineSeparator() + "3" + System.lineSeparator() + "9" + System.lineSeparator() + "8" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);
        TicTacToe.main(new String[]{"2"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                skipLine(50, reader);
                assertEquals("Draw", reader.readLine());
            }
    }

    @Test
    void checkComputerChoosesFirstCell() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "3" + System.lineSeparator() + "q" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);
        TicTacToe.main(new String[]{"2"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                skipLine(6, reader);
                assertEquals("| 2 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                skipLine(7, reader);
                assertEquals("| 2 | 2 | 1 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
            }
    }

    @Test
    void checkBoardIntegrityAfterMultipleMoves() throws IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "3" + System.lineSeparator()
                    + "5" + System.lineSeparator()
                    + "q" + System.lineSeparator();

        ByteArrayInputStream inputByteArray =
                new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);

        TicTacToe.main(new String[]{"2"});

        byte[] printout = outputByteArray.toByteArray();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            skipLine(6, reader);
            assertEquals("| 2 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());

            // ---- After human plays 3 and computer responds ----
            skipLine(7, reader);
            assertEquals("| 2 | 2 | 1 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());

            // ---- After human plays 5 and computer responds ----
            skipLine(7, reader);
            assertEquals("| 2 | 2 | 1 |", reader.readLine());
            assertEquals("| 2 | 1 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
        }
    }

    @Test
    void checkTurnPrompt() throws IOException{
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "1" + System.lineSeparator() + "abc" + System.lineSeparator() + 2 + System.lineSeparator() + "q" + System.lineSeparator();
        ByteArrayInputStream inputByteArray = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(inputByteArray);
        TicTacToe.main(new String[]{"1"});
        byte[] printout = outputByteArray.toByteArray();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ByteArrayInputStream(printout), StandardCharsets.UTF_8))){
                skipLine(6, reader);
                assertEquals("| 1 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());
                assertEquals("| 0 | 0 | 0 |", reader.readLine());

                skipLine(7, reader);
                assertEquals("Please, input a valid number [1-9]", reader.readLine());
                skipLine(3, reader);
                assertEquals("Player#1's turn", reader.readLine());

                skipLine(1, reader);
                assertEquals("The cell is occupied!", reader.readLine());
            }
    }

    void skipLine(int lines, BufferedReader reader) throws IOException{
        for (int i = 0; i < lines; i++){
            reader.readLine();
        }
    }

    // @Test
    // void testNoArgument() throws IOException{
    //     TicTacToe.main(new String[]{});
    //     //assertEquals("Please input valid option [1-2]" + System.lineSeparator(), outputByteArray.toString());
    //     assertEquals("Please input valid option [1-2]", scanner.readLine());
    // }

    // @Test
    // void testInvalidArgument() throws IOException{
    //     TicTacToe.main(new String[]{"a"});
    //     //assertEquals("Please input valid option [1-2]" + System.lineSeparator(), outputByteArray.toString());
    //     assertEquals("Please input valid option [1-2]", scanner.readLine());
    // }

    // @Test
    // void testInvalidExtraArgument() throws IOException{
    //     TicTacToe.main(new String[]{"a", "extra"});
    //     //assertEquals("Please input valid option [1-2]" + System.lineSeparator(), outputByteArray.toString());
    //     assertEquals("Please input valid option [1-2]", scanner.readLine());
    // }

    // @Test
    // void oneArgumentExtra() throws IOException{
    //     TicTacToe.main(new String[]{"1", "extra"});
    //     //assertEquals("Please input valid option [1-2]" + System.lineSeparator(), outputByteArray.toString());
    //     assertEquals("Hello!", scanner.readLine());
    //     assertEquals("| 0 | 0 | 0 |", scanner.readLine());
    //     assertEquals("| 0 | 0 | 0 |", scanner.readLine());
    //     assertEquals("| 0 | 0 | 0 |", scanner.readLine());
    //     assertEquals("Player#1's turn", scanner.readLine());
    // }

    // @Test
    // void quotedArgument() throws IOException{
    //     TicTacToe.main(new String[]{"1"});
    //     //assertEquals("Please input valid option [1-2]" + System.lineSeparator(), outputByteArray.toString());
    //     assertEquals("Hello!", scanner.readLine());
    //     assertEquals("| 0 | 0 | 0 |", scanner.readLine());
    //     assertEquals("| 0 | 0 | 0 |", scanner.readLine());
    //     assertEquals("| 0 | 0 | 0 |", scanner.readLine());
    //     assertEquals("Player#1's turn", scanner.readLine());
    // }

    // @Test
    // void nonIntegerInputTest() throws IOException{
    //     System.setIn(new ByteArrayInputStream("x".getBytes()));

    //     TicTacToe.main(new String[]{"1"});
    //     for (int i = 0; i < 6; i++){
    //         scanner.readLine();
    //     }

    //     assertEquals("Please, input a valid number [1-9]", scanner.readLine());
    //     scanner.readLine();
    //     scanner.readLine();
    //     scanner.readLine();
    //     assertEquals("Player#1's turn", scanner.readLine());
    // }

    // @Test
    // void outOfRangeInputTest() throws IOException{
    //     System.setIn(new ByteArrayInputStream("10".getBytes()));

    //     TicTacToe.main(new String[]{"1"});
    //     for (int i = 0; i < 6; i++){
    //         scanner.readLine();
    //     }

    //     assertEquals("Please, input a valid number [1-9]", scanner.readLine());
    //     scanner.readLine();
    //     scanner.readLine();
    //     scanner.readLine();
    //     assertEquals("Player#1's turn", scanner.readLine());
    // }

    // @Test
    // void testOccupiedCell() throws IOException{
    //     String input = "1" + System.lineSeparator() + "1";
    //     System.setIn(new ByteArrayInputStream(input.getBytes()));
    //     TicTacToe.main(new String[]{"1"});

    //     // Hello!
    //     scanner.readLine();
        
    //     // Initial board
    //     scanner.readLine();
    //     scanner.readLine();
    //     scanner.readLine();

    //     // Player#1's turn
    //     scanner.readLine();
    //     // Choose a cell...
    //     scanner.readLine();

    //     for (int i = 0; i < 5; i++){
    //         scanner.readLine();
    //     }
    //     String row1 = scanner.readLine();
    //     String row2 = scanner.readLine();
    //     String row3 = scanner.readLine();

    //     scanner.readLine();
    //     scanner.readLine();

    //     assertEquals("The cell is occupied!", scanner.readLine());

    //     String row1after = scanner.readLine();
    //     String row2after = scanner.readLine();
    //     String row3after = scanner.readLine();
    //     assertEquals(row1, row1after);
    //     assertEquals(row2, row2after);
    //     assertEquals(row3, row3after);

    //     assertEquals("Player#1's turn", scanner.readLine());
    // }

    // @Test
    // void validMoveUpdateBoard() throws Exception {
    //     String input = "3" + System.lineSeparator() + "2" + System.lineSeparator();
    //     System.setIn(new ByteArrayInputStream(input.getBytes()));
    //     TicTacToe.main(new String[]{"1"});

    //     for (int i = 0; i < 6; i++){
    //         scanner.readLine();
    //     }

    //     assertEquals("| 0 | 0 | 1 |", scanner.readLine());
    //     assertEquals("| 0 | 0 | 0 |", scanner.readLine());
    //     assertEquals("| 0 | 0 | 0 |", scanner.readLine());

    //     assertEquals("Player#2's turn", scanner.readLine());
    // }

    // @Test
    // void turnHandoffSymmetryTest1() throws Exception{
    //     String input = "3" + System.lineSeparator() + "2" + System.lineSeparator();
    //     System.setIn(new ByteArrayInputStream(input.getBytes()));

    //     // Test with option = 1
    //     TicTacToe.main(new String[]{"1"});

    //     for (int i = 0; i < 9; i++){
    //         scanner.readLine();
    //     }

    //     assertEquals("Player#2's turn", scanner.readLine());
    // }

    // @Test
    // void turnHandoffSymmetryTest2() throws Exception{
    //     String input = "3" + System.lineSeparator() + "2" + System.lineSeparator();
    //     System.setIn(new ByteArrayInputStream(input.getBytes()));

    //     // Test with option = 1
    //     TicTacToe.main(new String[]{"2"});

    //     for (int i = 0; i < 9; i++){
    //         scanner.readLine();
    //     }

    //     assertEquals("Player#1's turn", scanner.readLine());
    // }

    // Computer Move Strategy (First Free Cell)
    
}

