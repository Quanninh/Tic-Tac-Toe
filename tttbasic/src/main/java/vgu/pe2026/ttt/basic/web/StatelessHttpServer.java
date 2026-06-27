package vgu.pe2026.ttt.basic.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import vgu.pe2026.ttt.basic.Board;
import vgu.pe2026.ttt.basic.Board2D;
import vgu.pe2026.ttt.basic.Machine;
import vgu.pe2026.ttt.basic.Player;

public class StatelessHttpServer {

    private static final int PORT = 5003;

    public static void main(String[] args) throws IOException {

        HttpServer server =
                HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/start",
                StatelessHttpServer::handleStart);

        server.createContext("/move",
                StatelessHttpServer::handleMove);

        server.createContext("/quit",
                StatelessHttpServer::handleQuit);

        server.setExecutor(null);

        server.start();

        System.out.println("HTTP Tic-Tac-Toe Server running on port " + PORT);
    }

    private static void handleStart(HttpExchange exchange)
            throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {

            sendResponse(exchange, 405, "Method Not Allowed");

            return;
        }

        String response = "START" + System.lineSeparator() + "BOARD:0,0,0,0,0,0,0,0,0";

        sendResponse(exchange, 200, response);
    }

    private static void handleQuit(HttpExchange exchange) throws IOException {

        String response = "QUIT";

        sendResponse(exchange, 200, response);
    }

    private static void handleMove(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {

            sendResponse(exchange, 405, "Method Not Allowed");

            return;
        }

        String requestBody = readRequestBody(exchange);

        String[] lines = requestBody.split("\\R");

        if (lines.length < 2) {

            sendResponse(exchange, 400, "ERROR");

            return;
        }

        String moveStr = lines[0].trim();
        String boardStr = lines[1].trim();

        Board board = Board2D.fromString(boardStr);

        if (board == null) {

            sendResponse(exchange, 400, "ERROR");

            return;
        }

        try {

            int move = Integer.parseInt(moveStr);

            if (move < 1 || move > 9) {

                sendResponse(exchange, 200, "INVALID");

                return;
            }

            if (!board.isFree(move - 1)) {

                sendResponse(exchange, 200, "OCCUPIED");

                return;
            }

            board.move(move - 1, 1);

            if (board.checkWin() == 1) {

                sendResponse(
                        exchange,
                        200,
                        "HUMAN_WIN" + System.lineSeparator() + board.boardToString()
                );

                return;
            }

            if (board.isFull()) {

                sendResponse(
                        exchange,
                        200,
                        "DRAW" + System.lineSeparator() + board.boardToString()
                );

                return;
            }

            Player machine = new Machine();

            int computerMove = Integer.parseInt(machine.chooseMove(board));

            board.move(computerMove, 2);

            if (board.checkWin() == 2) {

                sendResponse(
                        exchange,
                        200,
                        "COMPUTER_WIN" + System.lineSeparator() + board.boardToString()
                );

                return;
            }

            if (board.isFull()) {

                sendResponse(
                        exchange,
                        200,
                        "DRAW" + System.lineSeparator() + board.boardToString()
                );

                return;
            }

            sendResponse(
                    exchange,
                    200,
                    "CONTINUE" + System.lineSeparator() + board.boardToString()
            );

        } catch (NumberFormatException e) {

            sendResponse(exchange, 400,
                    "INVALID");
        }
    }

    private static String readRequestBody(HttpExchange exchange) throws IOException {

        InputStream in = exchange.getRequestBody();

        return new String(in.readAllBytes());
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders()
                .add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders()
                .add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders()
                .add("Access-Control-Allow-Headers", "Content-Type");

        byte[] bytes = response.getBytes();

        exchange.sendResponseHeaders(statusCode, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}