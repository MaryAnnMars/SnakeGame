//To create a window, I need to import javax.swing
import javax.swing.*;

public class App {
    public static  void main(String[] args) throws Exception {
        // The  Measurements of the board
        int boardWidth = 600;
        int boardHeight = boardWidth;

        // To create the window
        JFrame frame = new JFrame("Snake");
        // Make frame visiable
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        // opens up the window at the centre of our screen
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        // The program will termiate when the X button is pressed on the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        // makes sure all of our pixals are in fame
        frame.pack();
        // snake game is going to listen to key press
        snakeGame.requestFocus();
        
    }
}
