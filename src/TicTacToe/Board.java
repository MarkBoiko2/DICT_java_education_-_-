package TicTacToe;

public class Board {
    private final char[][] cells = new char[3][3];

    public Board() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = ' ';
            }
        }
    }

    // (1,1) = верхняя левая
    public boolean isEmpty(int row, int col) {
        int r = row - 1;
        int c = col - 1;
        return cells[r][c] == ' ';
    }

    public void set(int row, int col, char symbol) {
        int r = row - 1;
        int c = col - 1;
        cells[r][c] = symbol;
    }

    public void print() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(cells[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public GameResult getResult() {
        boolean xWins = isWinner('X');
        boolean oWins = isWinner('O');

        if (xWins) return GameResult.X_WINS;
        if (oWins) return GameResult.O_WINS;

        if (hasEmpty()) return GameResult.NOT_FINISHED;
        return GameResult.DRAW;
    }

    private boolean hasEmpty() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[i][j] == ' ') return true;
            }
        }
        return false;
    }

    private boolean isWinner(char ch) {
        // rows
        for (int i = 0; i < 3; i++) {
            if (cells[i][0] == ch && cells[i][1] == ch && cells[i][2] == ch) return true;
        }
        // cols
        for (int j = 0; j < 3; j++) {
            if (cells[0][j] == ch && cells[1][j] == ch && cells[2][j] == ch) return true;
        }
        // diagonals
        return (cells[0][0] == ch && cells[1][1] == ch && cells[2][2] == ch)
                || (cells[0][2] == ch && cells[1][1] == ch && cells[2][0] == ch);
    }
}
