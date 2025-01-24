import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.event.*;

public class Snake_Game extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    int board_width;
    int board_height;
    int tileSize = 25;
    Tile snakeHead ;
    ArrayList<Tile> snakeBody;

    // Food
    Tile food;
    Random random;

    // Timer
    Timer gameLoop;

    // velocity
    int velocityX;
    int velocityY;

    boolean gameOver = false;

    Snake_Game(int board_height, int board_width){
        this.board_height = board_height;
        this.board_width = board_width;
        setPreferredSize(new Dimension(this.board_height, this.board_width));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 1;
        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize,true);

        g.setColor(Color.GREEN);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize, true);

        for(int i = 0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize-16, tileSize);
        } else {
            g.drawString("Score : " + String.valueOf(snakeBody.size()), tileSize-16, tileSize);
        }

    }

    public void placeFood(){
        food.x = random.nextInt(board_width/tileSize);
        food.y = random.nextInt(board_height/tileSize);
    }

    public void move(){
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }
        
        for(int i = snakeBody.size()-1 ; i >= 0; i--){
            Tile snakePart = snakeBody.get(i);
            if(i==0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakeTile = snakeBody.get(i-1);
                snakePart.x = prevSnakeTile.x;
                snakePart.y = prevSnakeTile.y;
            }
        }
        
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        for(int i = 0; i < snakeBody.size();i++){
            if(collision(snakeHead, snakeBody.get(i))){
                gameOver = true;
            }
        }

        if(snakeHead.x*tileSize < 0 || snakeHead.y *tileSize < 0 || 
           snakeHead.x *tileSize > board_width || snakeHead.y*tileSize > board_height){
            gameOver = true;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        } else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        } else if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        } else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
    
}

