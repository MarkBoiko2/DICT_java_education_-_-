package TicTacToe;

import java.util.Scanner;

public class Game {
    private final Board board = new Board();
    private char current = 'X';

    public void play(Scanner sc) {
        board.print();

        while (true) {
            System.out.print("Enter the coordinates: ");
            String line = sc.nextLine();

            int[] coords = parseCoords(line);
            if (coords == null) {
                System.out.println("You should enter numbers!");
                continue;
            }

            int row = coords[0];
            int col = coords[1];

            if (row < 1 || row > 3 || col < 1 || col > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            if (!board.isEmpty(row, col)) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            board.set(row, col, current);
            board.print();

            GameResult result = board.getResult();
            if (result == GameResult.X_WINS) {
                System.out.println("X wins");
                break;
            } else if (result == GameResult.O_WINS) {
                System.out.println("O wins");
                break;
            } else if (result == GameResult.DRAW) {
                System.out.println("Draw");
                break;
            }

            current = (current == 'X') ? 'O' : 'X';
        }
    }

    // Возвращает {row, col} или null если не 2 числа
    private int[] parseCoords(String line) {
        if (line == null) return null;
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 2) return null;

        try {
            int r = Integer.parseInt(parts[0]);
            int c = Integer.parseInt(parts[1]);
            return new int[]{r, c};
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
