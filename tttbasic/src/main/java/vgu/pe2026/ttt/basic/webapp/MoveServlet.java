package vgu.pe2026.ttt.basic.webapp;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vgu.pe2026.ttt.basic.Board;
import vgu.pe2026.ttt.basic.Board2D;
import vgu.pe2026.ttt.basic.Machine;
import vgu.pe2026.ttt.basic.Player;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/move")
public class MoveServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods",
                "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers",
                "Content-Type");

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String[] lines = requestBody.split("\\R");

        if (lines.length < 2){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("ERROR");
            return;
        }

        String moveStr = lines[0].trim();
        String boardStr = lines[1].trim();

        Board board = Board2D.fromString(boardStr);

        if (board == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("ERROR");
            return;
        }

        try{
            int move = Integer.parseInt(moveStr);
            if (move < 1 || move > 9){
                resp.getWriter().write("INVALID");
                return;
            }

            if (!board.isFree(move - 1)){
                resp.getWriter().write("OCCUPIED");
                return;
            }

            board.move(move - 1, 1);

            if (board.checkWin() == 1){
                resp.getWriter().write("HUMAN_WIN" + System.lineSeparator() + board.boardToString());
                return;
            }

            if (board.isFull()){
                resp.getWriter().write("DRAW" + System.lineSeparator() + board.boardToString());
                return;
            }

            Player machine = new Machine();

            int computerMove = Integer.parseInt(machine.chooseMove(board));
            board.move(computerMove, 2);

            if (board.checkWin() == 2){
                resp.getWriter().write("COMPUTER_WIN" + System.lineSeparator() + board.boardToString());
                return;
            }

            if (board.isFull()){
                resp.getWriter().write("DRAW" + System.lineSeparator() + board.boardToString());
                return;
            }

            resp.getWriter().write("CONTINUE" + System.lineSeparator() + board.boardToString());
        }catch(NumberFormatException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("INVALID");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods",
                "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers",
                "Content-Type");
        
        resp.setStatus(HttpServletResponse.SC_OK);

    }
}
