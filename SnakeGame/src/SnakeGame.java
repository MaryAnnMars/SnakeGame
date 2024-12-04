import java.awt.*;
import java.awt.event.*;
// this is going to be used for storing the segments of the snakes body
import java.util.ArrayList;
import java.util.Random;
// this is going to be used to place random x and y values for our food on the screen
import java.util.random.*;

import javax.swing.JPanel;
import javax.swing.*;

//ActionListner needs an action perform method (makes tile move)
public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile{
        // variables
        int x;
        int y;

        // constructer
        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
// variables

    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    // Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random;

    //Game Logic
    Timer gameLoop;
    // moving tiles
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    // Constructor
    SnakeGame(int boardWidth,  int boardHeight){
        // this.boardWidth, meaning the boardWidth belonging to this class
            this.boardWidth = boardWidth;
            this.boardHeight = boardHeight;
            setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
            setBackground(Color.black);
            // listens to key pressed
            addKeyListener(this);
            // snake to listen
            setFocusable(true);

            //Tile   
            snakeHead = new Tile(5, 5);
            snakeBody = new ArrayList<Tile>();

            // Tile
            food = new Tile(10, 10);

            //Random object
            random = new Random();
            placeFood();

            velocityX = 0;
            velocityY = 0; 

            gameLoop = new Timer(100, this);
            gameLoop.start();
    }

    //Function  "Graphic g" is used for drawing 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public  void draw(Graphics g){
        //Grid 
        // for (int i = 0; i < boardWidth/tileSize; i++){
        //     //(x1,  y1,  x2,  y2)
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
        //     g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        // }

        //Food
        g.setColor(Color.red);
      //  g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        //snake head
        g.setColor(Color.green);
        //g.fillRect(snakeHead.x * tileSize, snakeHead.y *tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);
        //Snake Body
        for (int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //g.fillRect(snakePart.x * tileSize, snakePart.y* tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }
        
        //Score
        g.setFont(new Font("Ariel", Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over:  " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
            else{
                g.drawString("Score: " +  String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
            }
        
}

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize); //600/25 = 24 randam number from 0 -24
        food.y = random.nextInt(boardHeight/tileSize); //600/25 = 24 randam number from 0 -24
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move(){
        //eat food
        if (collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //Snake Body moveing along with the head
        for (int i = snakeBody.size() -1; i >= 0; i-- ){
            Tile snakePart = snakeBody.get(i);
            if (i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y= snakeHead.y;
            }
            else {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //Game over condtions
        for(int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //collide with the snake head
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }
    if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize> boardWidth || 
    snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight){
        gameOver = true;
    }
}

    @Override 
    // every 100ms we are going to call action performed which will draw over and over
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e .getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }
    }

    //do not need
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
