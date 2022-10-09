/**
 * BrickBreakerGameplay handles the movement and interaction of the ball, paddle, and bricks in the Brick Breaker game.
 * 
 * @version 1.0, June 2022
 */

// Classes imported into and used in the program.
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.awt.Rectangle;

public class BrickBreakerGameplay extends BrickBreakerBuild implements KeyListener, ActionListener {

    private Timer timer; // To perform tasks repeatedly (to move the paddle and ball in the game).

    private int delay = 6; // In milliseconds, delay between task repetitions.

    private int ballXDirection = -1; // Direction of the ball (-1 for left or 1 for right).

    private int ballYDirection = -1; // Direction of the ball (-1 for up or 1 for down).

    /**
     * Constructor for objects of class BrickBreakerGameplay.
     */
    public BrickBreakerGameplay() {

        window.addKeyListener(this); // To detect when key pressed.

        timer = new Timer(delay, this); // Create a new instance of Timer object.

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.start(); // Starts the game.
                gamePanel.remove(startButton);
                gamePanel.remove(instructionsLabel);
            }
        });
    }

    /**
     * Needed because this program implements KeyListener. When the right or left
     * arrow keys are pressed, the paddle moves right or left.
     */
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // Detects if right arrow key is pressed.

            if (paddleXCoord >= PANEL_WIDTH - paddleWidth) {

                paddleXCoord = PANEL_WIDTH - paddleWidth; // Stop moving right if at the end of the panel.

            } else {

                paddleXCoord += 15; // Move the paddle right.

            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) { // Detects if left arrow key is pressed.

            if (paddleXCoord <= 0) {

                paddleXCoord = 0; // Stop moving left if at the end of the panel.

            } else {

                paddleXCoord -= 15; // Move the paddle left.

            }
        }
    }

    /**
     * Needed because this program implements ActionListener. Moves the ball and
     * changes its direction as needed. Repaints the game panel to incorporate
     * motion in the game.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Move the ball in the direction where it is going.
        ballXCoord += ballXDirection;
        ballYCoord += ballYDirection;

        // Reverse the ball's direction in the x-axis when it touches a side of the game
        // panel.
        if (ballXCoord < 0) {

            ballXDirection = 1;

        } else if (ballXCoord > (500 - ballSize)) {

            ballXDirection = -1;

        }

        if (ballYCoord < 0) { // Reverse the ball's direction in the y-axis when it touches the top of the
                              // game panel.

            ballYDirection = 1;

        } else if ((ballYCoord == 500 - paddleHeight) && (ballXCoord <= paddleXCoord + paddleWidth)
                && (ballXCoord >= paddleXCoord)) { // If ball touches paddle, reverse its direction in the y-axis.

            ballYDirection = -1;

            if (ballXCoord > paddleXCoord + paddleWidth / 2) { // If ball touches the right half of the paddle, reverse
                                                               // its direction in the x-axis. (Purpose: to propel ball
                                                               // in different directions when it hits the paddle.)
                ballXDirection = -ballXDirection;

            }
        }

        // To detect when the ball hits a brick.
        for (int i = 0; i < tiles.length; i++) {

            for (int j = 0; j < tiles[0].length; j++) {

                if (tiles[i][j] == 1) { // Make sure a brick exists.

                    // Coordinates of the brick.
                    int brickXCoord = j * tileWidth + ((PANEL_WIDTH - tileWidth * columns) / 2);
                    int brickYCoord = i * tileHeight + 50;
                    int brickWidth = tileWidth;
                    int brickHeight = tileHeight;

                    // Create a new rectangle representing the brick.
                    Rectangle brick = new Rectangle(brickXCoord, brickYCoord, brickWidth, brickHeight);

                    // Create a new rectangle representing the ball (rectangle needed to use
                    // .intersects()).
                    Rectangle ball = new Rectangle(ballXCoord, ballYCoord, ballSize, ballSize);

                    // When the ball hits a brick.
                    if (ball.intersects(brick)) {

                        tiles[i][j] = 0; // Remove brick.

                        numberOfBricks--; // Decrease total number of bricks by 1.

                        score++; // Increase score by 1.

                        // Change the direction of the ball when it hits the brick, depending on where
                        // it hits.
                        if (ballXCoord + ballSize <= brickXCoord || ballXCoord >= brickXCoord + brickWidth) {

                            ballXDirection = -ballXDirection;

                        } else {

                            ballYDirection = -ballYDirection;

                        }

                    }

                }
            }

        }

        if (ballYCoord >= PANEL_HEIGHT || numberOfBricks == 0) {

            timer.stop(); // Stop the game when the ball is out of bounds.

        }

        gamePanel.repaint(); // Change the position of the ball, the paddle if moved, the brick if the
                             // ball hits it by repainting the shapes.

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Needed for KeyListener. Not applicable to the program.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Needed for KeyListener. Not applicable to the program.
    }
}
