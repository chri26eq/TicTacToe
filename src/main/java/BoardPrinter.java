public class BoardPrinter {



    private final String PLAYER_1;
    private final String PLAYER_2;

    public BoardPrinter(String PLAYER_1, String PLAYER_2) {
        this.PLAYER_1 = PLAYER_1;
        this.PLAYER_2 = PLAYER_2;
    }

    // ANSI farvekoder
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";  // Grøn til "X"
    public static final String RED = "\u001B[31m";    // Rød til "O"

    public void printBoard(String[][] board) {
        int counter = 1;
        int size = board.length; // Antager at board er kvadratisk (f.eks. 3x3)

        System.out.println("┏" + "━┳".repeat(size - 1) + "━┓");

        for (int i = 0; i < size; i++) {
            System.out.print("┃");

            for (int j = 0; j < size; j++) {
                String field;

                if (board[i][j].equals(PLAYER_1)) {
                    field = GREEN + PLAYER_1 + RESET;  // Grøn "X"
                } else if (board[i][j].equals(PLAYER_2)) {
                    field = RED + PLAYER_2 + RESET;    // Rød "O"
                } else {
                    field = String.valueOf(counter);
                }

                counter++;
                System.out.print(field + "┃");
            }
            System.out.println();

            if (i < size - 1) {
                System.out.println("┣" + "━╋".repeat(size - 1) + "━┫");
            }
        }

        System.out.println("┗" + "━┻".repeat(size - 1) + "━┛");
    }


    public void printResult(int gameValue) {
        switch (gameValue) {
            case 0:
                System.out.println("Tie");
                break;
            case 10:
                System.out.println(PLAYER_1 + " won");
                break;
            case -10:
                System.out.println(PLAYER_2 + " won");
                break;
        }
    }
}
