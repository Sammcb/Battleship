import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Battleship.java
 * Creates a battleship board for the player and computer and handles the turn taking
 * Sam McBroom
 * AP Computer Science
 * 01/28/2016
 */

public class Battleship {
    static Random num = new Random();
    static Scanner input = new Scanner(System.in);
    static boolean gameOver = false;
    // Create the boards
    static Board player = new Board();
    static Board comp = new Board();
    static Board choice = new Board();
    public static void main(String[] args) {
        String ship;

        // Explain the rules
        System.out.println("You may hide your ships.");
        System.out.println("You must choose two coordinates, representing the endpoints for each ship, to place a ship.");
        System.out.println("You cannot place ships diagonally.");
        // Place Aircraft carrier
        System.out.println("");
        System.out.println("Your first ship is the Aircraft carrier. It is five spots long and is marked by an \"@\"");
        ship = "aircraft_carrier";
        player.printBoard();
        printKey();
        while (!pickCoords(ship)) {
            System.out.println("Please enter valid coordinates.");
        }
        // Generate computer's ship
        compCoords(ship);
        // Place Battleship
        System.out.println("");
        System.out.println("Your second ship is the Battleship. It is four spots long and is marked by a \"#\"");
        ship = "battleship";
        player.printBoard();
        printKey();
        while (!pickCoords(ship)) {
            System.out.println("Please enter valid coordinates.");
        }
        // Generate computer's ship
        compCoords(ship);
        // Place Submarine
        System.out.println("");
        System.out.println("Your third ship is the Submarine. It is three spots long and is marked by a \"~\"");
        ship = "submarine";
        player.printBoard();
        printKey();
        while (!pickCoords(ship)) {
            System.out.println("Please enter valid coordinates.");
        }
        // Generate computer's ship
        compCoords(ship);
        // Place Destroyer
        System.out.println("");
        System.out.println("Your fourth ship is the Destroyer. It is three spots long and is marked by an \"&\"");
        ship = "destroyer";
        player.printBoard();
        printKey();
        while (!pickCoords(ship)) {
            System.out.println("Please enter valid coordinates.");
        }
        // Generate computer's ship
        compCoords(ship);
        // Place Patrol boat
        System.out.println("");
        System.out.println("Your fith ship is the Patrol boat. It is 2 spots long and is marked by an \"*\"");
        ship = "patrol_boat";
        player.printBoard();
        printKey();
        while (!pickCoords(ship)) {
            System.out.println("Please enter valid coordinates.");
        }
        // Generate computer's ship
        compCoords(ship);

        // For showing computer's choices
        System.out.println("---------------------------");
        comp.printBoard();
        System.out.println("---------------------------");

        // Print player's board with all ships placed
        player.printBoard();

        // Take turns
        while (!gameOver) {
            // Show the player a board representing the hits or misses on the computer's board
            System.out.println("");
            System.out.println("Computer's board:");
            choice.printBoard();
            // Player takes their turn
            while (!playerTurn()) {
            }
            // Computer takes its turn
            player.turn();
            // Show the player their board so they can see where the computer has guessed
            System.out.println("");
            System.out.println("Your board:");
            player.printBoard();
            // See if player destroyed any ships
            switch (comp.shipsGone()) {
                case 1:
                    System.out.println("You destroyed the Aircraft carrier!");
                    break;
                case 2:
                    System.out.println("You destroyed the Battleship!");
                    break;
                case 3:
                    System.out.println("You destroyed the Submarine!");
                    break;
                case 4:
                    System.out.println("You destroyed the Destroyer!");
                    break;
                case 5:
                    System.out.println("You destroyed the Patrol boat!");
                    break;
                default:
                    break;
            }
            // See if computer destroyed any ships
            switch (player.shipsGone()) {
                case 1:
                    System.out.println("Your Aircraft carrier was destroyed!");
                    break;
                case 2:
                    System.out.println("Your Battleship was destroyerd!");
                    break;
                case 3:
                    System.out.println("Your Submarine was destroyerd!");
                    break;
                case 4:
                    System.out.println("Your Destroyer was destroyerd!");
                    break;
                case 5:
                    System.out.println("Your Patrol boat was destroyerd!");
                    break;
                default:
                    break;
            }

            System.out.println("---------------------------");
            // Did the game end?
            if (player.totalShips < 1) {
                gameOver = true;
                System.out.println("Computer won.");
            }
        }
    }

    // Sets player's ship
    public static boolean pickCoords(String ship) {
        // Set up coordinate pair
        int y1, y2;
        String x1, x2;
        // Ask the user for the points
        System.out.println("Pick row:");
        x1 = input.nextLine();
        x1 = input.nextLine();
        System.out.println("Pick column:");
        // Do this in case the user enters a letter
        try {
            y1 = input.nextInt();
        } catch (InputMismatchException exception) {
            return false;
        }
        System.out.println("Pick row:");
        x2 = input.nextLine();
        x2 = input.nextLine();
        System.out.println("Pick column:");
        // Do this in case the user enters a letter
        try {
            y2 = input.nextInt();
        } catch (InputMismatchException exception) {
            return false;
        }
        // Make sure the letters are upper case to match the array
        x1 = x1.toUpperCase();
        x2 = x2.toUpperCase();
        // Place players ship, if possible
        return player.placeShip(x1, y1, x2, y2, ship);
    }

    // Set's computer's ship
    public static void compCoords(String ship) {
        int[] compX1 = new int[2];
        int[] compX2 = new int[2];
        boolean check = false;

        // Set up the computer's board
        do {
            // Randomly pick the first spot
            for (int i = 0; i < 2; i++) {
                compX1[i] = num.nextInt(10);
            }
            // Vertical or horizontal
            switch (num.nextInt(2)) {
                // Horizontal
                case 0:
                    compX2[0] = compX1[0];
                    // Determine if the ship can be placed on way, if not then it is placed in the other direction
                    if (compX1[1] - 4 >= 0) {
                        compX2[1] = compX1[1] - 4;
                    } else {
                        compX2[1] = compX1[1] + 4;
                    }
                    break;
                // Vertical
                case 1:
                    compX2[1] = compX1[1];
                    // Determine if the ship can be placed on way, if not then it is placed in the other direction
                    if (compX1[0] - 4 >= 0) {
                        compX2[0] = compX1[0] - 4;
                    } else {
                        compX2[0] = compX1[0] + 4;
                    }
                    break;
            }
            // Place computer's ship, if possible
            if (comp.compPlaceShip(compX1, compX2, ship)) {
                check = true;
            }
        } while (!check);
    }

    // Print the key for ships
    public static void printKey() {
        System.out.println("Aircraft carrier: @, Battleship: #, Submarine: ~, Destroyer: &, Patrol boat: *");
    }

    // Lets the player take their turn
    public static boolean playerTurn() {
        // Set up coordinates
        String x1;
        int y1;
        // Pick valid coordinates
        do {
            System.out.println("Choose a spot to hit:");
            System.out.println("Pick row:");
            x1 = input.nextLine();
            x1 = input.nextLine();
            System.out.println("Pick column:");
            try {
                y1 = input.nextInt();
            } catch (InputMismatchException exception) {
                return false;
            }
            x1 = x1.toUpperCase();
        } while (!comp.choice(x1, y1));
        // Mark spot guessed
        comp.setSpot(x1, y1);
        // Update the board the player sees
        updateBoard(comp.getBoard(), choice.getBoard());
        // Did the game end?
        if (comp.totalShips < 1) {
            gameOver = true;
            System.out.println("You won!");
        }
        return true;
    }

    // Update the board the player sees to allow them to see what they guessed
    public static void updateBoard(char[][] board1, char[][] board2) {
        for (int i = 0; i < board2.length; i++) {
            for (int n = 0; n < board2.length; n++) {
                if (board1[i][n] == 'X') {
                    board2[i][n] = 'X';
                } else if (board1[i][n] == '!') {
                    board2[i][n] = '!';
                }
            }
        }
    }

}
