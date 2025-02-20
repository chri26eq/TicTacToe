import java.util.List;

public class Game {

    public static void run() throws InterruptedException {
        final String[][] board = {
                {" ", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}
        };

        final String PLAYER_X = "X";
        final String PLAYER_O = "O";
        final BoardAnalyser boardAnalyser = new BoardAnalyser(PLAYER_X, PLAYER_O);
        final BoardPrinter boardPrinter = new BoardPrinter(PLAYER_X, PLAYER_O);
        final UserInput userInput = new UserInput(board, PLAYER_X, boardAnalyser);

        int gameValue;
        boolean boardIsFull;

        boolean playerStarts = userInput.number("Who plays first: \n1) You\n2) PC", 1, 2) == 1;
        int depth = userInput.number("Choose algorithm max search depth between 1 and 10", 1, 10);

        String currentPlayer = playerStarts ? PLAYER_X : PLAYER_O;

        while (true) {
            boardPrinter.printBoard(board);

// tjek om spillet er slut
            gameValue = boardAnalyser.checkBoardForWinners(board);
            boardIsFull = boardAnalyser.isBoardFull(board);
            if (gameValue != 0 || boardIsFull) break;

            if (currentPlayer.equals(PLAYER_X)) {
                userInput.choose();
            } else {
                System.out.println("PC is thinking...");
                Thread.sleep(1500);
                List<Integer> move = boardAnalyser.findBestMove(board, PLAYER_O, depth);
                board[move.get(0)][move.get(1)] = PLAYER_O;
            }

// skift spiller
            currentPlayer = currentPlayer.equals(PLAYER_X) ? PLAYER_O : PLAYER_X;
        }

        boardPrinter.printBoard(board);
        boardPrinter.printResult(gameValue);
    }
}
