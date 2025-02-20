import java.util.Scanner;

public class UserInput {
    private final Scanner sc = new Scanner(System.in);
    private final String[][] board;
    private final String player;
    private final BoardAnalyser boardAnalyser;

    public UserInput(String[][] board, String player, BoardAnalyser boardAnalyser) {
        this.board = board;
        this.player = player;
        this.boardAnalyser = boardAnalyser;
    }

    public void choose() {
        int i = -1;
        int j = -1;


        while (true) {
            System.out.print("Chose an available tile: ");
            String choice = sc.nextLine();


            if (!choice.matches("[1-9]")) {
                System.out.println("Invalid input. Please choose a number between 1 and 9.");
                continue;
            }


            switch (choice) {
                case "1" -> {
                    i = 0;
                    j = 0;
                }
                case "2" -> {
                    i = 0;
                    j = 1;
                }
                case "3" -> {
                    i = 0;
                    j = 2;
                }
                case "4" -> {
                    i = 1;
                    j = 0;
                }
                case "5" -> {
                    i = 1;
                    j = 1;
                }
                case "6" -> {
                    i = 1;
                    j = 2;
                }
                case "7" -> {
                    i = 2;
                    j = 0;
                }
                case "8" -> {
                    i = 2;
                    j = 1;
                }
                case "9" -> {
                    i = 2;
                    j = 2;
                }
            }

            if (!boardAnalyser.isTileEmpty(board[i][j])) {
                System.out.println("Tile is not empty. Choose another.");
                continue;
            }

            board[i][j] = player;
            return;


        }
    }


    public int number(String prompt, int min, int max) {
        System.out.println(prompt);


        while (true) {
            String choice = sc.nextLine();
            try {
                int num = Integer.parseInt(choice);
                if (num < min || num > max) {
                    System.out.println("Invalid input. Please choose a number between " + min + " and " + max + ".");
                    System.out.println(prompt);
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                System.out.println(prompt);
                continue;
            }
             return Integer.parseInt(choice);
        }
    }


}
