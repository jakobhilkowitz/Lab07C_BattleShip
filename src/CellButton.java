import javax.swing.JButton;

/**
 * A button that stores its row and column.
 */
public class CellButton extends JButton {
    private int row;
    private int col;

    /**
     * Creates a button for one board cell.
     *
     * @param row the row
     * @param col the column
     */
    public CellButton(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the row.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column.
     *
     * @return the column
     */
    public int getCol() {
        return col;
    }
}