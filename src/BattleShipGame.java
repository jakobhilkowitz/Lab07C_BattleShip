import java.util.ArrayList;
import java.util.Random;

/**
 * Handles the Battleship game logic.
 */
public class BattleShipGame {
    private int[][] board;
    private ArrayList<Ship> ships;
    private int missCount;
    private int strikeCount;
    private int totalMiss;
    private int totalHit;

    /**
     * Creates a new game.
     */
    public BattleShipGame() {
        initializeGame();
    }

    /**
     * Sets up a new game.
     */
    public void initializeGame() {
        board = new int[10][10];
        ships = new ArrayList<>();
        missCount = 0;
        strikeCount = 0;
        totalMiss = 0;
        totalHit = 0;
        placeShips();
    }

    /**
     * Resets the game.
     */
    public void resetGame() {
        initializeGame();
    }

    /**
     * Places all ships on the board randomly.
     */
    public void placeShips() {
        int[] shipSizes = {5, 4, 3, 3, 2};
        Random rand = new Random();

        for (int size : shipSizes) {
            boolean placed = false;

            while (!placed) {
                boolean horizontal = rand.nextBoolean();
                int row = rand.nextInt(10);
                int col = rand.nextInt(10);

                if (horizontal) {
                    if (col + size <= 10 && canPlaceShip(row, col, size, true)) {
                        Ship ship = new Ship(size);

                        for (int i = 0; i < size; i++) {
                            board[row][col + i] = 1;
                            ship.addPosition(row, col + i);
                        }

                        ships.add(ship);
                        placed = true;
                    }
                }
                else {
                    if (row + size <= 10 && canPlaceShip(row, col, size, false)) {
                        Ship ship = new Ship(size);

                        for (int i = 0; i < size; i++) {
                            board[row + i][col] = 1;
                            ship.addPosition(row + i, col);
                        }

                        ships.add(ship);
                        placed = true;
                    }
                }
            }
        }
    }

    /**
     * Checks if a ship can be placed.
     *
     * @param row the start row
     * @param col the start column
     * @param size the ship size
     * @param horizontal true if horizontal
     * @return true if open
     */
    private boolean canPlaceShip(int row, int col, int size, boolean horizontal) {
        for (int i = 0; i < size; i++) {
            if (horizontal) {
                if (board[row][col + i] != 0) {
                    return false;
                }
            }
            else {
                if (board[row + i][col] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Fires at one location.
     *
     * @param row the row
     * @param col the column
     * @return true if hit
     */
    public boolean fireAt(int row, int col) {
        if (board[row][col] == 1) {
            board[row][col] = 3; //update to hit
            totalHit++;
            missCount = 0;

            Ship ship = getShipAt(row, col);
            if (ship != null) {
                ship.registerHit();
            }

            return true;
        }
        else if (board[row][col] == 0) {
            board[row][col] = 2;
            missCount++;
            totalMiss++;

            if (missCount == 5) {
                strikeCount++;
                missCount = 0;
            }

            return false;
        }

        return false;
    }

    /**
     * Gets the ship at a location.
     *
     * @param row the row
     * @param col the column
     * @return the ship or null
     */
    private Ship getShipAt(int row, int col) {
        for (Ship ship : ships) {
            if (ship.occupies(row, col)) {
                return ship;
            }
        }
        return null;
    }

    /**
     * Checks if the ship at a spot is sunk.
     *
     * @param row the row
     * @param col the column
     * @return true if sunk
     */
    public boolean isShipSunk(int row, int col) {
        Ship ship = getShipAt(row, col);

        if (ship != null) {
            return ship.isSunk();
        }

        return false;
    }

    /**
     * Checks if all ships are sunk.
     *
     * @return true if all are sunk
     */
    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the miss counter.
     *
     * @return miss counter
     */
    public int getMissCount() {
        return missCount;
    }

    /**
     * Gets the strike counter.
     *
     * @return strike counter
     */
    public int getStrikeCount() {
        return strikeCount;
    }

    /**
     * Gets total misses.
     *
     * @return total misses
     */
    public int getTotalMiss() {
        return totalMiss;
    }

    /**
     * Gets total hits.
     *
     * @return total hits
     */
    public int getTotalHit() {
        return totalHit;
    }


    /**
     * Prints the board for testing.
     */
    public void printBoard() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }
}