import java.util.ArrayList;
import java.util.List;

public class BoardAnalyser {
    private final int INFINITY = Integer.MAX_VALUE; //ikke uendelig, men stor nok til formålet...
    private final String PLAYER_MAX;
    private final String PLAYER_MIN;

    public BoardAnalyser(String PLAYER_MAX, String PLAYER_MIN) {
        this.PLAYER_MAX = PLAYER_MAX;
        this.PLAYER_MIN = PLAYER_MIN;
    }


    public List<Integer> findBestMove(String[][] board, String player, int allowedDepth) {
        int bestMoveValue = (player.equals(PLAYER_MAX)) ? -INFINITY : INFINITY;
        List<Integer> bestMove = new ArrayList<>(List.of(-1, -1)); // row, col

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isTileEmpty(board[i][j])) {
                    board[i][j] = player;
                    boolean nextIsMaximizer = (player.equals(PLAYER_MAX)) ? false : true;
                    // Start med allowedDepth som den maksimale dybde
                    int moveValue = minimax(board, allowedDepth, nextIsMaximizer);
                    board[i][j] = " ";

                    if (player.equals(PLAYER_MAX)) {
                        if (moveValue > bestMoveValue) {
                            bestMove.set(0, i);
                            bestMove.set(1, j);
                            bestMoveValue = moveValue;
                        }
                    }
                    if (player.equals(PLAYER_MIN)) {
                        if (moveValue < bestMoveValue) {
                            bestMove.set(0, i);
                            bestMove.set(1, j);
                            bestMoveValue = moveValue;
                        }
                    }
                }
            }
        }
        return bestMove;
    }


    public int minimax(String[][] board, int depth, boolean isMaximizer) {

        int value = checkBoardForWinners(board);

        // Hvis analyserede state er et vindende state for X eller Y returner værdien
        // kompenseret dybden.
        // Altså jo dybere man er, jo dårligere er trækket
        // På den måde prioriteres tættere vindende træk højere end dybere vindende træk.
        if (value == 10) return value + depth;
        if (value == -10) return value - depth;

        // Hvis brættet er fyldt eller maksdbde er nået, returner 0
        if (isBoardFull(board) || depth == 0) return 0;

        int bestMove;

        // Maximizers tur -----------------------------------------------

        if (isMaximizer) {
            bestMove = -INFINITY;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (isTileEmpty(board[i][j])) {

                        // Laver et move for at analysere det
                        board[i][j] = PLAYER_MAX;
                        int move = minimax(board, depth - 1, false); // Går et lag dybere "depth - 1"
                        bestMove = Math.max(bestMove, move);

                        // Fjerner move. Ellers ville der "placeres brikker" for alle analyserede moves
                        board[i][j] = " ";
                    }
                }
            }
        }

        // Minimizers tur -----------------------------------------------
        else {
            bestMove = INFINITY;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (isTileEmpty(board[i][j])) {

                        // Laver et move for at analysere det
                        board[i][j] = PLAYER_MIN;
                        int move = minimax(board, depth - 1, true); // Går et lag dybere "depth - 1"
                        bestMove = Math.min(bestMove, move);

                        // Fjerner move. Ellers ville der "placeres brikker" for alle analyserede moves
                        board[i][j] = " ";
                    }
                }
            }
        }
        // returner min eller max bestMove værdi
        return bestMove;
    }


    public boolean isBoardFull(String[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (isEqual(board[i][j], " "))
                    return false;
        return true;
    }


    public boolean isTileEmpty(String tile) {
        return tile.equals(" ");
    }


    //-------------------------------------------------

    // Returnerer +10 hvis PLAYER1(X) vinder, -10 hvis PLAYER2(O) vinder, 0 hvis uafgjort
    public int checkBoardForWinners(String[][] board) {
// Tjekker om der er vindere på top- middle- eller bottom-row - eks(0,0  0,1  0,2)
        for (int row = 0; row < 3; row++) {
            if (isEqual(board[row][0], board[row][1], board[row][2])) {
                if (board[row][0].equals(PLAYER_MAX))
                    return +10;
                else if (board[row][0].equals(PLAYER_MIN))
                    return -10;
            }
        }

// Tjekker om der er vindere på left- center- eller right-column - eks(0,0  1,0  2,0)
        for (int col = 0; col < 3; col++) {
            if (isEqual(board[0][col], board[1][col], board[2][col])) {
                if (board[0][col].equals(PLAYER_MAX))
                    return +10;
                else if (board[0][col].equals(PLAYER_MIN))
                    return -10;
            }
        }

// Tjekker om der er vindere diagonalt - (0,0  1,1  2,2) eller (0,2  1,1 2,0)
        if (isEqual(board[0][0], board[1][1], board[2][2])) {
            if (board[0][0].equals(PLAYER_MAX))
                return +10;
            else if (board[0][0].equals(PLAYER_MIN))
                return -10;
        }

        if (isEqual(board[0][2], board[1][1], board[2][0])) {
            if (board[0][2].equals(PLAYER_MAX))
                return +10;
            else if (board[0][2].equals(PLAYER_MIN))
                return -10;
        }

// ingen vinder, returner 0
        return 0;
    }


    int staticBoardEvaluation(String[][] board) {
        int bestValue = 0;
        int[][] tileStaticValues =
                {
                        {3, 2, 3},
                        {2, 4, 2},
                        {3, 2, 3}
                };


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isTileEmpty(board[i][j])) {
                    bestValue = Math.max(bestValue, tileStaticValues[i][j]);
                }
                ;

            }
        }
        return bestValue;
    }

    //--------Hjælpefunktioner--------
    private boolean isEqual(String... values) {
        for (int i = 1; i < values.length; i++) {
            if (!values[0].equals(values[i])) {
                return false;
            }
        }
        return true;
    }

}
