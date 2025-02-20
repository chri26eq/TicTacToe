import java.util.Scanner;

public class Player {
    Scanner sc = new Scanner(System.in);

    public void choose(String[][] board, String player) {
        boolean inputIOsInvalid;
        do {
        System.out.print("Chose an available tile: ");
        String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    board[0][0] = player;
                    return;
                case "2":
                    board[0][1] = player;
                    return;
                case "3":
                    board[0][2] = player;
                    return;
                case "4":
                    board[1][0] = player;
                    return;
                case "5":
                    board[1][1] = player;
                    return;
                case "6":
                    board[1][2] = player;
                    return;
                case "7":
                    board[2][0] = player;
                    return;
                case "8":
                    board[2][1] = player;
                    return;
                case "9":
                    board[2][2] = player;
                    return;
                default: inputIOsInvalid = true;
            }
        } while (inputIOsInvalid);
    }




}
