package lastpencil;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class LastPencil {
    String player1 = "John";
    String player2 = "Jack";
    int count;
    void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many pencils would you like to use:");
        String input = scanner.nextLine();
        char a = ' ';

        while (!isNumeric(input) || input.equals("0") || input.equals(" ") || input.charAt(0) == ' ') {
            if (input.equals("0")) {
                System.out.println("The number of pencils should be positive");
            } else {
                System.out.println("The number of pencils should be numeric");
            }
            input = scanner.nextLine();
        }
        while (Integer.parseInt(input) <= 0) {
            if (Integer.parseInt(input) < 0) {
                System.out.println("The number of pencils should be numeric");
            } else {
                System.out.println("The number of pencils should be positive");
            }
            input = scanner.nextLine();
        }
        this.count = Integer.parseInt(input);

        this.gameLoop();
    }

    void gameLoop() {
        String[] players = {this.player1, this.player2};
        Scanner scanner = new Scanner(System.in);
        System.out.println("Who will be the first (John, Jack):");
        String firstPlayer = scanner.next();
        while (findIndex(players, firstPlayer) == -1) {
            System.out.printf("Choose between '%s' and '%s'\n", this.player1, this.player2);
            firstPlayer = scanner.next();
        }
        String botPlayer = this.player2;
        int index = findIndex(players,firstPlayer);
        while (true) {
            this.printPencils();
            System.out.printf("%s's turn!\n", players[index % 2]);
            String input;
            if (players[index % 2] == botPlayer) {
                input = Integer.toString(this.makeBotPlayerMove());
                System.out.println(input);
            } else {
                input = scanner.next();
            }

            int consumedCount = 0;
            while (!isNumeric(input) || findIndex(new String[]{"1", "2", "3"}, input) == -1 || findIndex(new int[]{1, 2, 3}, this.count) > -1) {
                if (!isNumeric(input) || input.equals("0")) {
                    System.out.println("Possible values: '1', '2' or '3'");
                    input = scanner.next();
                    continue;
                } else if (findIndex(new String[]{"1", "2", "3"}, input) == -1) {
                    System.out.println("Possible values: '1', '2' or '3'");
                    input = scanner.next();
                    continue;
                }
                 consumedCount= Integer.parseInt(input);

                if (consumedCount > this.count) {
                    System.out.println("Too many pencils were taken");
                    input = scanner.next();
                    continue;
                }
                break;
            }
            consumedCount= Integer.parseInt(input);
            this.count -= consumedCount;
            if (this.count == 0) {
                System.out.printf("%s won!", players[(index + 1) % 2]);
                return;
            }
            index++;
        }
    }

    int makeBotPlayerMove() {
        if (this.isInLosingPosition1Pencil()) {
            if (this.count == 1) {
                return 1;
            } else {
                Random random = new Random();
                return 1 + random.nextInt(2);
            }
        } else if (this.isInLosingPosition1Pencil()) {
            return 1;
        } else if (this.winningPositionWith2pencils()) {
            return 2;
        } else if (this.winningPositionWith3pencils()) {
            return 3;
        }
        return 1;
    }

    boolean isInLosingPosition1Pencil() {
        return this.count % 4 == 1;
    }
    boolean winningPositionWith3pencils() {
        return this.count % 4 == 0;
    }
    boolean winningPositionWith2pencils() {
        return this.count % 4 == 3;
    }
    boolean winningPositionWith1pencils() {
        return this.count % 4 == 2;
    }

    void printPencils() {
        String pencilSign = "|";
        System.out.println(pencilSign.repeat(this.count));
    }

    public static int findIndex(String[] arr, String t)
    {
        if (arr == null) {
            return -1;
        }
        int len = arr.length;
        int i = 0;
        while (i < len) {
            if (Objects.equals(arr[i], t)) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }
    public static int findIndex(int[] arr, int t)
    {
        if (arr == null) {
            return -1;
        }
        int len = arr.length;
        int i = 0;
        while (i < len) {
            if (Objects.equals(arr[i], t)) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
