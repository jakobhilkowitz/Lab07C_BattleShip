import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

/**
 * Displays the Battleship GUI.
 */
public class BattleshipFrame extends JFrame {
    private BattleShipGame game;
    private CellButton[][] boardButtons;
    private JLabel missLabel;
    private JLabel strikeLabel;
    private JLabel totalMissLabel;
    private JLabel totalHitLabel;
    private JButton playAgainButton;
    private JButton quitButton;

    /**
     * Creates the frame.
     */
    public BattleshipFrame() {
        game = new BattleShipGame();
        game.printBoard();
        boardButtons = new CellButton[10][10];

        buildGUI();

        setTitle("Battleship");
        setSize(700, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Builds the GUI.
     */
    public void buildGUI() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        missLabel = new JLabel("MISS: 0");
        strikeLabel = new JLabel("STRIKE: 0");
        totalMissLabel = new JLabel("TOTAL MISS: 0");
        totalHitLabel = new JLabel("TOTAL HIT: 0");

        topPanel.add(missLabel);
        topPanel.add(strikeLabel);
        topPanel.add(totalMissLabel);
        topPanel.add(totalHitLabel);

        add(topPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(10, 10));

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                CellButton button = new CellButton(row, col);
                button.setFont(new Font("Arial", Font.BOLD, 18));

                button.addActionListener(e -> {
                    CellButton clicked = (CellButton)e.getSource();
                    handleCellClick(clicked.getRow(), clicked.getCol());
                });

                boardButtons[row][col] = button;
                boardPanel.add(button);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        playAgainButton = new JButton("Play Again");
        quitButton = new JButton("Quit");

        playAgainButton.addActionListener(e -> resetGame());
        quitButton.addActionListener(e -> endGame());

        bottomPanel.add(playAgainButton);
        bottomPanel.add(quitButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Handles one board click.
     *
     * @param row the row
     * @param col the column
     */
    public void handleCellClick(int row, int col) {
        boolean hit = game.fireAt(row, col);
        CellButton button = boardButtons[row][col];

        button.setEnabled(false);

        if (hit) {
            button.setText("X");
            button.setBackground(Color.RED);
            button.setOpaque(true);

            if (game.isShipSunk(row, col)) {
                JOptionPane.showMessageDialog(this, "You sunk a ship!");
            }

            if (game.allShipsSunk()) {
                updateStatus();

                int choice = JOptionPane.showConfirmDialog(
                        this,
                        "You won! Play again?",
                        "Win",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    resetBoardOnly();
                    game.resetGame();
                    updateStatus();
                }
                else {
                    System.exit(0);
                }
                return;
            }
        }
        else {
            button.setText("M");
            button.setBackground(Color.YELLOW);
            button.setOpaque(true);

            if (game.getStrikeCount() == 3) {
                updateStatus();

                int choice = JOptionPane.showConfirmDialog(
                        this,
                        "You lost! Play again?",
                        "Loss",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    resetBoardOnly();
                    game.resetGame();
                    updateStatus();
                }
                else {
                    System.exit(0);
                }
                return;
            }
        }

        updateStatus();
    }

    /**
     * Updates the labels.
     */
    public void updateStatus() {
        missLabel.setText("MISS: " + game.getMissCount());
        strikeLabel.setText("STRIKE: " + game.getStrikeCount());
        totalMissLabel.setText("TOTAL MISS: " + game.getTotalMiss());
        totalHitLabel.setText("TOTAL HIT: " + game.getTotalHit());
    }

    /**
     * Resets the game after asking.
     */
    public void resetGame() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to play again?",
                "Play Again",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            resetBoardOnly();
            game.resetGame();
            updateStatus();
        }
    }

    /**
     * Clears the board buttons.
     */
    private void resetBoardOnly() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                boardButtons[row][col].setText("");
                boardButtons[row][col].setEnabled(true);
                boardButtons[row][col].setBackground(null);
                boardButtons[row][col].setOpaque(true);
            }
        }
    }

    /**
     * Ends the game after asking.
     */
    public void endGame() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Quit",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}