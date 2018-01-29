import java.util.Arrays;
import java.util.Random;

/**
 * Board.java
 * Creates a board and also handles the checkers for picking a spot
 * Sam McBroom
 * AP Computer Science
 * 01/28/2016
 */

public class Board {
    // Set up the row, column, and board
    public String[] selector_row = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    public int[] selector_col = new int[10];
    public char[][] board = new char[10][10];
    // Set the total ships left
    public int totalShips = 17;
    // Store a spot if it is a hit
    public int hit1 = 0;
    public int hit2 = 0;
    public int z = 0; // Column of comp choice
    public int y = 0; // Row of comp choice
    public Random num = new Random();
    // Is spot a hit?
    public boolean hit = false;
    // For checking around the hit spot
    public int hitCount = 0;
    // For after the computer has finish with initial pass
    public int selector1, selector2;
    // Determine if a ship has been blown up
    public boolean aircraft_carrierGone = false;
    public boolean battleshipGone = false;
    public boolean submarineGone = false;
    public boolean destroyerGone = false;
    public boolean patrol_boatGone = false;
    // Has the player been told if a ship was destroyed
    boolean aircraftNotification = false;
    boolean battleshipNotification = false;
    boolean submarineNotification = false;
    boolean destroyerNotification = false;
    boolean patrolNotification = false;

    // Set the board and column
    public Board() {
        for (int i = 0; i < board.length; i++) {
            for (int n = 0; n < board[i].length; n++) {
                board[i][n] = 'O';
            }
        }
        for (int i = 0; i < selector_col.length; i++) {
            selector_col[i] = (i + 1);
        }
    }

    // Print the board
    public void printBoard() {
        // This prints the numbers at the top
        System.out.print("   ");
        for (int i = 0; i < selector_col.length; i++) {
            System.out.format("%2s", selector_col[i]);
            // This adds an extra space to numbers that have two digits
            if (i > 7) {
                System.out.print(" ");
            }
        }
        System.out.println("");

        // This prints the board and the row markers
        for (int i = 0; i < board.length; i++) {
            System.out.print("[" + selector_row[i] + "]");
            for (int n = 0; n < board[i].length; n++) {
                System.out.print(" " + board[i][n]);
            }
            System.out.println("");
        }
    }

    // Place the player's ship
    public boolean placeShip(String x1, int y1, String x2, int y2, String ship) {
        int length = 0;
        char boat = '@';

        // Make sure the coords are in the board
        if (checkRow(x1) && checkCol(y1) && checkRow(x2) && checkCol(y2)) {
            // Determine what ship they are placing
            if (ship.equals("aircraft_carrier")) {
                length = 5;
                boat = '@';
            } else if (ship.equals("battleship")) {
                length = 4;
                boat = '#';
            } else if (ship.equals("submarine")) {
                length = 3;
                boat = '~';
            } else if (ship.equals("destroyer")) {
                length = 3;
                boat = '&';
            } else {
                length = 2;
                boat = '*';
            }
            // Determine if the ship is horizontal or vertical
            // Horizontal
            // Make sure the ship is the proper length
            if (x1.equals(x2) && (y1 - y2 == length-1 || y2 - y1 == length-1)) {
                // Which way to check and generate spaces
                if (y1 < y2) {
                    // Make sure all spots are ocean
                    for (int i = 0; i < length; i++) {
                        if (board[Arrays.asList(selector_row).indexOf(x1)][(y1-1)+i] != 'O') {
                            return false;
                        }
                    }
                    // Replace spots with the ship
                    for (int i = 0; i < length; i++) {
                        board[Arrays.asList(selector_row).indexOf(x1)][(y1-1)+i] = boat;
                    }
                    // Which way to check and generate spaces
                } else {
                    for (int i = 0; i < length; i++) {
                        if (board[Arrays.asList(selector_row).indexOf(x1)][(y1-1)-i] != 'O') {
                            return false;
                        }
                    }
                    for (int i = 0; i < length; i++) {
                        board[Arrays.asList(selector_row).indexOf(x1)][(y1-1)-i] = boat;
                    }
                }
                return true;
                // Vertical
                // Make sure the ship is the proper length
            } else if (y1 == y2 && (Arrays.asList(selector_row).indexOf(x1) - Arrays.asList(selector_row).indexOf(x2) == length-1 || Arrays.asList(selector_row).indexOf(x2) - Arrays.asList(selector_row).indexOf(x1) == length-1)) {
                // Which way to check and generate spaces
                if (Arrays.asList(selector_row).indexOf(x1) < Arrays.asList(selector_row).indexOf(x2)) {
                    // Make sure all spots are ocean
                    for (int i = 0; i < length; i++) {
                        if (board[Arrays.asList(selector_row).indexOf(x1)+i][y1-1] != 'O') {
                            return false;
                        }
                    }
                    // Replace spots with the ship
                    for (int i = 0; i < length; i++) {
                        board[Arrays.asList(selector_row).indexOf(x1)+i][y1-1] = boat;
                    }
                    // Which way to check and generate spaces
                } else {
                    // Make sure all spots are ocean
                    for (int i = 0; i < length; i++) {
                        if (board[Arrays.asList(selector_row).indexOf(x1)-i][y1-1] != 'O') {
                            return false;
                        }
                    }
                    // Replace spots with the ship
                    for (int i = 0; i < length; i++) {
                        board[Arrays.asList(selector_row).indexOf(x1)-i][y1-1] = boat;
                    }
                }
                return true;
            }
        }
        return false;
    }

    // Is the row in the board
    public boolean checkRow(String row) {
        for (int i = 0; i < selector_row.length; i++) {
            if (row.equals(selector_row[i])) {
                return true;
            }
        }
        return false;
    }

    // Is the column in the board
    public boolean checkCol(int col) {
        for (int i = 0; i < selector_col.length; i++) {
            if (col == selector_col[i]) {
                return true;
            }
        }
        return false;
    }

    // Place computer's ship
    public boolean compPlaceShip(int[] compX1, int[] compX2, String ship) {
        int length = 0;
        char boat = '@';
        // Try for the given coords
        try {
            // Make sure the coords are in the board
            if (checkRow(selector_row[compX1[0]]) && checkCol(compX1[1]) && checkRow(selector_row[compX2[0]]) && checkCol(compX2[1])) {
                // Determine what ship is being placed
                if (ship.equals("aircraft_carrier")) {
                    length = 5;
                    boat = '@';
                } else if (ship.equals("battleship")) {
                    length = 4;
                    boat = '#';
                } else if (ship.equals("submarine")) {
                    length = 3;
                    boat = '~';
                } else if (ship.equals("destroyer")) {
                    length = 3;
                    boat = '&';
                } else {
                    length = 2;
                    boat = '*';
                }

                // Horizontal
                if (compX1[0] == compX2[0]) {
                    // Which way to check and generate spaces
                    if (compX1[1] < compX2[1]) {
                        // Make sure all spots are ocean
                        for (int i = 0; i < length; i++) {
                            if (board[compX1[0]][compX1[1]+i] != 'O') {
                                return false;
                            }
                        }
                        // Place ship
                        for (int i = 0; i < length; i++) {
                            board[compX1[0]][compX1[1]+i] = boat;
                        }
                        // Which way to check and generate spaces
                    } else {
                        // Make sure all spots are ocean
                        for (int i = 0; i < length; i++) {
                            if (board[compX1[0]][compX1[1]-i] != 'O') {
                                return false;
                            }
                        }
                        // Place ship
                        for (int i = 0; i < length; i++) {
                            board[compX1[0]][compX1[1]-i] = boat;
                        }
                    }
                    // Vertical
                } else if (compX1[1] == compX2[1]) {
                    // Which way to check and generate spaces
                    if (compX1[0] < compX2[0]) {
                        // Make sure all spots are ocean
                        for (int i = 0; i < length; i++) {
                            if (board[compX1[0]+i][compX1[1]] != 'O') {
                                return false;
                            }
                        }
                        // Place ship
                        for (int i = 0; i < length; i++) {
                            board[compX1[0]+i][compX1[1]] = boat;
                        }
                        // Which way to check and generate spaces
                    } else {
                        // Make sure all spots are ocean
                        for (int i = 0; i < length; i++) {
                            if (board[compX1[0]-i][compX1[1]] != 'O') {
                                return false;
                            }
                        }
                        // Place ship
                        for (int i = 0; i < length; i++) {
                            board[compX1[0]-i][compX1[1]] = boat;
                        }
                    }
                }
                return true;
            }
            return false;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    // Return the total number of ship spots remaining
    public int ships() {
        return totalShips;
    }

    // Check player's choice
    public boolean choice(String x1, int y1) {
        // Are the spots in the board?
        if (checkRow(x1) && checkCol(y1)) {
            // If ocean, return valid
            if (board[Arrays.asList(selector_row).indexOf(x1)][y1-1] == 'O') {
                return true;
                // If missed or hit, return invalid
            } else if (board[Arrays.asList(selector_row).indexOf(x1)][y1-1] == 'X' || board[Arrays.asList(selector_row).indexOf(x1)][y1-1] == '!') {
                System.out.println("You already guessed that spot.");
                return false;
                // If ship, return valid
            } else {
                return true;
            }
        }
        return false;
    }

    // See if a ship has been destroyed
    // Returns do not conflict with checking because only one ship could be destroyed per turn
    public int shipsGone() {

        aircraft_carrierGone = true;
        battleshipGone = true;
        submarineGone = true;
        destroyerGone = true;
        patrol_boatGone = true;

        for (int i = 0; i < board.length; i++) {
            for (int n = 0; n < board[i].length; n++) {
                // Has the Aircraft carrier been destroyed
                if (board[i][n] == '@') {
                    aircraft_carrierGone = false;
                    // Has the Battleship been destroyed
                } else if (board[i][n] == '#') {
                    battleshipGone = false;
                    // Has the Submarine been destroyed
                } else if (board[i][n] == '~') {
                    submarineGone = false;
                    // Has the Destroyer been destroyed
                } else if (board[i][n] == '&') {
                    destroyerGone = false;
                    // Has the Patrol boat been destroyed
                } else if (board[i][n] == '*') {
                    patrol_boatGone = false;
                }
            }
        }

        if (aircraft_carrierGone && !aircraftNotification) {
            aircraftNotification = true;
            return 1;
        } else if (battleshipGone && !battleshipNotification) {
            battleshipNotification = true;
            return 2;
        } else if (submarineGone && !submarineNotification) {
            submarineNotification = true;
            return 3;
        } else if (destroyerGone && !destroyerNotification) {
            destroyerNotification = true;
            return 4;
        } else if (patrol_boatGone && !patrolNotification) {
            patrolNotification = true;
            return 5;
        } else {
            return 0;
        }



            /*
		// Has the Aircraft carrier been destroyed
		if (!aircraft_carrierGone) {
			for (int i = 0; i < board.length; i++) {
				for (int n = 0; n < board[i].length; n++) {
					if (board[i][n] == '@') {
						//return 0;
                                                break;
					}
				}
			}
			aircraft_carrierGone = true;
			return 1;
		// Has Battleship been destroyed
		} else if (!battleshipGone) {
			for (int i = 0; i < board.length; i++) {
				for (int n = 0; n < board[i].length; n++) {
					if (board[i][n] == '#') {
						//return 0;
                                                break;
					}
				}
			}
			battleshipGone = true;
			return 2;
		// Has Submarine been destroyed
		} else if (!submarineGone) {
			for (int i = 0; i < board.length; i++) {
				for (int n = 0; n < board[i].length; n++) {
					if (board[i][n] == '~') {
						//return 0;
                                                break;
					}
				}
			}
			submarineGone = true;
			return 3;
		// Has Destroyer been destroyed
		} else if (!destroyerGone) {
			for (int i = 0; i < board.length; i++) {
				for (int n = 0; n < board[i].length; n++) {
					if (board[i][n] == '&') {
						//return 0;
                                                break;
					}
				}
			}
			destroyerGone = true;
			return 4;
		// Has Patrol boat been destroyed
		} else if (!patrol_boatGone) {
			for (int i = 0; i < board.length; i++) {
				for (int n = 0; n < board[i].length; n++) {
					if (board[i][n] == '*') {
						//return 0;
                                                break;
					}
				}
			}
			patrol_boatGone = true;
			return 5;
		}
		return 0;
                */
    }

    // Set the guessed spot to the appropriate mark (player)
    public void setSpot(String x1, int y1) {
        if (board[Arrays.asList(selector_row).indexOf(x1)][y1-1] == 'O') {
            board[Arrays.asList(selector_row).indexOf(x1)][y1-1] = 'X';
        } else {
            board[Arrays.asList(selector_row).indexOf(x1)][y1-1] = '!';
            // Reduce number of ships computer has left
            totalShips--;
        }
    }

    // Return the board
    public char[][] getBoard() {
        return board;
    }

    // Computer's turn
    public boolean turn() {
        boolean worked = false;
        // Was the last spot a hit?
        if (!hit) {
            // Check to see if the row should be incremented
            if (z > 9) {
                y++;
                z = 0;
                // See if z needs to be offset
                if (y % 2 == 0) {
                    z = 0;
                } else {
                    z = 1;
                }
            }
            // If AI is still doing the normal checking
            while (y < 10) {
                // Check spot to place appropriate mark
                // If ocean, make as missed
                if (board[y][z] == 'O') {
                    board[y][z] = 'X';
                    z += 2;
                    return true;
                    // If missed or hit, try again
                } else if (board[y][z] == 'X' || board[y][z] == '!') {
                    z += 2;
                    turn();
                    return false;
                    // Otherwise it must be one of the ships, so make it as hit
                } else {
                    board[y][z] = '!';
                    // Initiate hit testing
                    hit = true;
                    // Reduce the number of spots the player has left
                    totalShips--;
                    // Mark the hit spot
                    hit1 = y;
                    hit2 = z;
                    z += 2;
                    return true;
                }
            }

            // Run this after we have gone through the entire board to get the remaining spots
            while (!worked) {
                // Pick a random spot
                selector1 = num.nextInt(10);
                selector2 = num.nextInt(10);
                // If ocean mark as missed
                if (board[selector1][selector2] == 'O') {
                    board[selector1][selector2] = 'X';
                    // Stop guessing
                    worked = true;
                    // If missed or hit, try again
                } else if (board[selector1][selector2] == 'X' || board[selector1][selector2] == '!') {
                    worked = false;
                    // Otherwise it must be a ship, so mark as hit and stop guessing
                } else {
                    board[selector1][selector2] = '!';
                    // Reduce player's remaining ships
                    totalShips--;
                    // Initiate hit testing
                    hit = true;
                    // Make hit spot
                    hit1 = selector1;
                    hit2 = selector2;
                    // Stop guessing
                    worked = true;
                }
            }
            // If last move was a hit
        } else {
            // Check around the hit spot
            switch (hitCount) {
                case 0:
                    // Try above
                    try {
                        // If ocean, mark as missed and end turn
                        if (board[hit1-1][hit2] == 'O') {
                            board[hit1-1][hit2] = 'X';
                            // Move to next spot next turn
                            hitCount++;
                            break;
                            // If missed or hit, move to next spot
                        } else if (board[hit1-1][hit2] == 'X' || board[hit1-1][hit2] == '!') {
                            hitCount++;
                            // If hit, stop checking around this spot and look around the spot that was hit next time
                        } else {
                            board[hit1-1][hit2] = '!';
                            // Reduce player's remaining ships
                            totalShips--;
                            // Marked hit spot
                            hit1 = hit1-1;
                            // Rest the checker
                            hitCount = 0;
                            break;
                        }
                        // If spot does not exist, then move on to next one
                    } catch (IndexOutOfBoundsException e) {
                        hitCount++;
                    }
                case 1:
                    // Try to the right
                    try {
                        // If ocean, mark as missed and end turn
                        if (board[hit1][hit2+1] == 'O') {
                            board[hit1][hit2+1] = 'X';
                            // Move to next spot next turn
                            hitCount++;
                            break;
                            // If missed or hit, move to next spot
                        } else if (board[hit1][hit2+1] == 'X' || board[hit1][hit2+1] == '!') {
                            hitCount++;
                            // If hit, stop checking around this spot and look around the spot that was hit next time
                        } else {
                            board[hit1][hit2+1] = '!';
                            // Reduce player's remaining ships
                            totalShips--;
                            // Marked hit spot
                            hit2 = hit2+1;
                            // Rest the checker
                            hitCount = 0;
                            break;
                        }
                        // If spot does not exist, then move on to next one
                    } catch (IndexOutOfBoundsException e) {
                        hitCount++;
                    }
                case 2:
                    // Try bottom
                    try {
                        // If ocean, mark as missed and end turn
                        if (board[hit1+1][hit2] == 'O') {
                            board[hit1+1][hit2] = 'X';
                            // Move to next spot next turn
                            hitCount++;
                            break;
                            // If missed or hit, move to next spot
                        } else if (board[hit1+1][hit2] == 'X' || board[hit1+1][hit2] == '!') {
                            hitCount++;
                            // If hit, stop checking around this spot and look around the spot that was hit next time
                        } else {
                            board[hit1+1][hit2] = '!';
                            // Reduce player's remaining ships
                            totalShips--;
                            // Marked hit spot
                            hit1 = hit1+1;
                            // Rest the checker
                            hitCount = 0;
                            break;
                        }
                        // If spot does not exist, then move on to next one
                    } catch (IndexOutOfBoundsException e) {
                        hitCount++;
                    }
                case 3:
                    // Try left
                    try {
                        // If ocean, mark as missed and resume ordinary checking
                        if (board[hit1][hit2-1] == 'O') {
                            board[hit1][hit2-1] = 'X';
                            // Move to next spot next turn
                            hitCount = 0;
                            hit = false;
                            break;
                            // If missed or hit, resume ordinary checking
                        } else if (board[hit1][hit2-1] == 'X' || board[hit1][hit2-1] == '!') {
                            hitCount = 0;
                            hit = false;
                            turn();
                            break;
                            // If hit, stop checking around this spot and look around the spot that was hit next time
                        } else {
                            board[hit1][hit2-1] = '!';
                            // Reduce player's remaining ships
                            totalShips--;
                            // Marked hit spot
                            hit2 = hit2-1;
                            // Rest the checker
                            hitCount = 0;
                            break;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        // If all spot have been checked, then return to normal pass
                        hitCount = 0;
                        hit = false;
                        turn();
                        break;
                    }
            }
        }
        return true;
    }
}
