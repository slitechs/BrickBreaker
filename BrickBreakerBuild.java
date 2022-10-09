
/**
 * BrickBreakerBuild is used to set up the components (window, game panel, bricks, paddle, ball) in the Brick Breaker game.
 *
 * @version 1.0, June 2022
 */

// Classes imported into and used in the program.
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.FlowLayout;
import java.awt.Dimension;

public class BrickBreakerBuild {

    // Specify dimensions of game panel.
    final int PANEL_WIDTH = 500;
    final int PANEL_HEIGHT = 500;

    // Specify dimensions of frame.
    final int FRAME_WIDTH = PANEL_WIDTH;
    final int FRAME_HEIGHT = PANEL_HEIGHT + 38;

    public int[][] tiles; // Array to store brick placement.

    public static int rows = 5; // Rows of bricks.

    public static int columns = 8; // Columns of bricks.

    public int numberOfBricks = rows * columns; // Total number of bricks.

    public int tileWidth = 50; // Width of each brick.

    public int tileHeight = 20; // Height of each brick.

    public int paddleWidth = 100; // Width of paddle.

    public int paddleHeight = 10; // Height of paddle.

    public int paddleXCoord = (PANEL_WIDTH / 2) - (paddleWidth / 2); // X-coordinate of paddle.

    public int ballSize = 10; // Size of ball.

    public int ballXCoord = (PANEL_WIDTH / 2) - (ballSize / 2); // X-coordinate of ball.

    public int ballYCoord = 500 - paddleHeight; // Y-coordinate of ball.

    JFrame window = new JFrame("Brick Breaker Game"); // Create a window for the program to run in.

    MyJPanel gamePanel = new MyJPanel(); // Create a game panel.

    JButton startButton = new JButton("PRESS HERE TO START!"); // Start button for the game.

    JLabel instructionsLabel; // Label for instructions displayed at start of game.

    public int score = 0; // The score for the game.

    /**
     * Constructor for objects of class BrickBreakerBuild.
     */
    public BrickBreakerBuild() {

        // Set window properties.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        window.setResizable(false);

        startButton.setFocusable(false); // Makes sure JButton is not able to gain focus so that KeyListener works.

        window.setContentPane(gamePanel); // Add game panel to window.

        gamePanel.setLayout(new FlowLayout()); // Set FlowLayout.

        // Add instructions to window.
        instructionsLabel = new JLabel(getInstructions());
        instructionsLabel.setPreferredSize(new Dimension(450, 450));
        instructionsLabel.setVerticalAlignment(JLabel.CENTER);
        gamePanel.add(instructionsLabel);

        startButton.setVerticalAlignment(JLabel.CENTER);
        gamePanel.add(startButton); // Add start button to window.

        // Configure game panel appearance.
        gamePanel.setOpaque(true);
        gamePanel.setBackground(new Color(238, 238, 255));

        tiles = new int[rows][columns]; // Set the arrangement of bricks according to the specified number of rows and
                                        // columns.

        // Mark where each brick is in the array.
        for (int i = 0; i < tiles.length; i++) {

            for (int j = 0; j < tiles[0].length; j++) {

                tiles[i][j] = 1;

            }
        }

        window.setVisible(true); // Make the window visible.
    }

    /**
     * Internal class. Its paintComponent() method overrides JPanel's to draw in the
     * main content panel. Draws the components used in the game.
     */
    class MyJPanel extends JPanel {

        /**
         * MyJPanel class constructor.
         */
        public MyJPanel() {

            super();

        }

        /**
         * Overrides the JPanel paintComponent method.
         * Draws the components seen in the window.
         */
        @Override
        public void paintComponent(Graphics g) {

            // Ensures the background will have the main content pane's background color.
            super.paintComponent(g);

            // Draw all the bricks.
            for (int i = 0; i < tiles.length; i++) {

                for (int j = 0; j < tiles[0].length; j++) {

                    if (tiles[i][j] > 0) {

                        // Draw the brick.
                        g.setColor(Color.ORANGE);
                        g.fillRect(j * tileWidth + ((PANEL_WIDTH - tileWidth * columns) / 2), i * tileHeight + 50,
                                tileWidth, tileHeight);

                        // Draw the border of the brick.
                        g.setColor(Color.BLACK);
                        g.drawRect(j * tileWidth + ((PANEL_WIDTH - tileWidth * columns) / 2), i * tileHeight + 50,
                                tileWidth, tileHeight);

                    }

                }

            }

            // Draw the paddle.
            g.setColor(Color.GRAY);
            g.fillRect(paddleXCoord, 500, paddleWidth, paddleHeight);

            // Draw the ball.
            g.setColor(Color.WHITE);
            g.fillOval(ballXCoord, ballYCoord, ballSize, ballSize);

            // Draw the border of the ball.
            g.setColor(Color.BLACK);
            g.drawOval(ballXCoord, ballYCoord, ballSize, ballSize);

            // Draw the score.
            g.setColor(Color.BLACK);
            g.drawString("Score: " + score, 10, 20);

            // If the ball touches the bottom, draw the game over text.
            if (ballYCoord >= PANEL_HEIGHT) {

                g.setColor(Color.BLACK);
                g.drawString("GAME OVER", PANEL_WIDTH / 2 - 30, PANEL_HEIGHT / 2);

            }

            // If all the bricks have been broken, display the win message.
            if (numberOfBricks == 0) {

                g.setColor(Color.BLACK);
                g.drawString("YOU WON!", PANEL_WIDTH / 2 - 30, PANEL_HEIGHT / 2);

            }

        }

    }

    /**
     * getInstructions() is used to get the instructions of the game.
     * 
     * @return instructions String.
     */
    public String getInstructions() {

        String instructions = "<html>INSTRUCTIONS:<br>";

        instructions += "Use the right and left arrow keys to move the paddle.<br>";

        instructions += "The goal of the game is to use the ball to hit all the bricks.<br>";

        instructions += "Don't let the ball touch the bottom, otherwise the game will end.<br>";

        instructions += "Your score is how many bricks you hit.<br>";

        instructions += "HAVE FUN!<br><br>";

        instructions += "Press the start button to continue.</html>";

        return instructions;

    }

}
