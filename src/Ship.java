import java.awt.Point;
import java.util.ArrayList;

/**
 * Stores one ship and its hits.
 */
public class Ship {
    private int size;
    private int hitCount;
    private ArrayList<Point> positions;

    /**
     * Creates a ship of a given size.
     *
     * @param size the ship size
     */
    public Ship(int size) {
        this.size = size;
        hitCount = 0;
        positions = new ArrayList<>();
    }

    /**
     * Adds one location for the ship.
     *
     * @param row the row
     * @param col the column
     */
    public void addPosition(int row, int col) {
        positions.add(new Point(row, col));
    }

    /**
     * Checks if the ship uses a location.
     *
     * @param row the row
     * @param col the column
     * @return true if it is there
     */
    public boolean occupies(int row, int col) {
        for (Point p : positions) {
            if (p.x == row && p.y == col) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds one hit to the ship.
     */
    public void registerHit() {
        hitCount++;
    }

    /**
     * Checks if the ship is sunk.
     *
     * @return true if sunk
     */
    public boolean isSunk() {
        return hitCount >= size;
    }
}