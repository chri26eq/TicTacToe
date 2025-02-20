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
        // En vindende værdi reduces med "depth"s værdi.
        // Derfor, jo højere "allowedDepth", jo lavere "startingDepth"

        int startingDepth = 10 - allowedDepth;

        //hvis player er X then best value = -Inf ellers +Inf
        int bestMoveValue = (player.equals(PLAYER_MAX)) ? -INFINITY : INFINITY;

        List<Integer> bestMove = new ArrayList<>(List.of(-1, -1)); // row, col

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (isFieldEmpty(board[i][j])) {
                    board[i][j] = player;

                    boolean nextIsMaximizer = (player.equals(PLAYER_MAX)) ? false : true; // hvis player er X skal næste tjek være minimizer

                    int moveValue = minimax(board, startingDepth, nextIsMaximizer);

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
//        System.out.println("Best move for " + player + " is " + bestMove);

        return bestMove;
    }


    public int minimax(String[][] board, int depth, boolean isMaximizer) {




        int value = checkBoardForWinners(board);

//        if (value == 10 || value == -10) return value;

        // Hvis analyserede state er et vindende state for X eller Y returner værdien
        // kompenseret dybden.
        if (value == 10) return value - depth;
        if (value == -10) return value + depth;

        // hvis alle felter er brugt og ingen vinder er fundet returner 0
        if (isBoardFull(board)) return 0;


        int bestMove;

        // Maximizers tur -----------------------------------------------

        if (isMaximizer) {
            bestMove = -INFINITY;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (isFieldEmpty(board[i][j])) {

                        // Laver et move for at analysere det
                        board[i][j] = PLAYER_MAX;

                        int move = minimax(board, depth + 1, false);
                        bestMove = Math.max(bestMove, move);

                        // Fjerner move. Ellers ville der "placeres brikker" for alle analyserede moves
                        board[i][j] = " ";
                    }
                }
            }
            return bestMove;
        }

        // Minimizers tur -----------------------------------------------
        else {
            bestMove = INFINITY;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (isFieldEmpty(board[i][j])) {

                        // Laver et move for at analysere det
                        board[i][j] = PLAYER_MIN;

                        int move = minimax(board, depth + 1, true);
                        bestMove = Math.min(bestMove, move);

                        // Fjerner move. Ellers ville der "placeres brikker" for alle analyserede moves
                        board[i][j] = " ";
                    }
                }
            }
            return bestMove;
        }
    }


    public boolean isBoardFull(String[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (isEqual(board[i][j], " "))
                    return false;
        return true;
    }


    private boolean isFieldEmpty(String field) {
        return field.equals(" ");
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
        int[][] fieldStaticValues =
                {
                        {3, 2, 3},
                        {2, 4, 2},
                        {3, 2, 3}
                };


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isFieldEmpty(board[i][j])) {
                    bestValue = Math.max(bestValue, fieldStaticValues[i][j]);
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
