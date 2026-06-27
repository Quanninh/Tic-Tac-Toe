package vgu.pe2026.ttt.basic.web;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import vgu.pe2026.ttt.basic.Board2D;
import vgu.pe2026.ttt.basic.Board;



public class StatelessHttpClient {

	private static final String BASE_URL = "http://localhost:5003";

	private static String lastBoard;

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		HttpClient client = HttpClient.newHttpClient();

		Board board = null;

		System.out.println("Hello!");

		try {

			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(BASE_URL + "/start"))
					.GET()
					.build();

			HttpResponse<String> response = client.send(
					request,
					HttpResponse.BodyHandlers.ofString());

			String[] lines = response.body().split("\\R");

			if (lines[0].equals("START")) {

				lastBoard = lines[1];

				board = Board2D.fromString(lastBoard);
			}

		} catch (Exception e) {

			e.printStackTrace();
			return;
		}

		while (true) {

			System.out.println("Current board:");

			board.display();

			System.out.print(
					"Choose a cell from 1 to 9 (or q to quit): ");

			String move = scanner.nextLine().trim();

			try {

				if (move.equalsIgnoreCase("q")) {

					HttpRequest quitRequest = HttpRequest.newBuilder()
							.uri(
								URI.create(BASE_URL + "/quit"))
							.POST(
									HttpRequest.BodyPublishers.noBody())
							.build();

					client.send(quitRequest, HttpResponse.BodyHandlers.ofString());

					System.out.println("Game is finished");

					break;
				}

				String requestBody = move + System.lineSeparator() + lastBoard;

				HttpRequest request = HttpRequest.newBuilder()
						.uri(URI.create(BASE_URL + "/move"))
						.header(
								"Content-Type",
								"text/plain")
						.POST(
								HttpRequest.BodyPublishers.ofString(requestBody))
						.build();

				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

				String[] lines = response.body().split("\\R");

				String status = lines[0];

				if (status.equals("INVALID")) {

					System.out.println(
							"Please input a valid number [1-9]");

					continue;
				}

				if (status.equals("OCCUPIED")) {

					System.out.println("The cell is occupied!");

					continue;
				}

				if (lines.length > 1) {

					lastBoard = lines[1];

					board = Board2D.fromString(lastBoard);
				}

				if (status.equals("HUMAN_WIN")) {

					board.display();

					System.out.println("Game is finished");

					System.out.println("The winner is human");

					break;
				}

				if (status.equals("COMPUTER_WIN")) {

					board.display();

					System.out.println("Game is finished");

					System.out.println("The winner is computer");

					break;
				}

				if (status.equals("DRAW")) {

					board.display();

					System.out.println("Game is finished");

					System.out.println("Draw");

					break;
				}

			} catch (Exception e) {

				e.printStackTrace();

				break;
			}
		}

		scanner.close();
	}
}