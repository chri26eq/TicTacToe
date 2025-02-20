import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        final String PLAYER_X = "X";
        final String PLAYER_O = "O";
        BoardAnalyser boardAnalyser = new BoardAnalyser(PLAYER_X, PLAYER_O);
        BoardPrinter boardPrinter = new BoardPrinter(PLAYER_X, PLAYER_O);
        Player player = new Player();


        String[][] demoBoard =
                {
                        {"O", "X", "O"},
                        {"O", "X", "X"},
                        {" ", " ", " "}
                };
        String[][] fullBoard =
                {
                        {"O", "X", "O"},
                        {"O", "X", "X"},
                        {"X", "O", "X"}
                };
        String[][] emptyBoard =
                {
                        {" ", " ", " "},
                        {" ", " ", " "},
                        {" ", " ", " "}
                };


        int gameValue;
        boolean isBoardFull;
        while (true) {

            boardPrinter.printBoard(emptyBoard);

            gameValue = boardAnalyser.checkBoardForWinners(emptyBoard);
            isBoardFull = boardAnalyser.isBoardFull(emptyBoard);
            if (gameValue != 0 || isBoardFull) break;


            player.choose(emptyBoard, PLAYER_X);
            boardPrinter.printBoard(emptyBoard);

            gameValue = boardAnalyser.checkBoardForWinners(emptyBoard);
            isBoardFull = boardAnalyser.isBoardFull(emptyBoard);
            if (gameValue != 0 || isBoardFull) break;


            Thread.sleep(2000);

            List<Integer> moveO = boardAnalyser.findBestMove(emptyBoard, PLAYER_O, 1);
            emptyBoard[moveO.get(0)][moveO.get(1)] = PLAYER_O;

        }

        switch (gameValue) {
            case 0:
                System.out.println("Tie");
                break;
            case -10:
                System.out.println(PLAYER_O + " won");
                break;
            case 10:
                System.out.println(PLAYER_X + " won");
                break;
        }

    }
}
