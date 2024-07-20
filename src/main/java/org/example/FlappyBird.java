package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
     int boardWidth = 360;
     int boardHeight = 640;

     Image backgroundimg;
     Image birdimg;
     Image topPipeimg;
     Image downPipeimg;

     int birdX = boardWidth/8;
     int birdY = boardHeight/2;

     int birdWidth = 34;
     int birdHeight = 24;

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if (gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY = -9;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    class Bird{
         int x = birdX;
         int y = birdY;
         int wigth = birdWidth;
         int height = birdHeight;
         Image img;

         Bird(Image img){
             this.img = img;
         }
     }

     int pipeX = boardWidth;
     int pipeY = 0;

     int pipeWidth = 64;

     int pipeHeight = 512;

     class Pipe{
         int x = pipeX;
         int y = pipeY;
         int width = pipeWidth;
         int height = pipeHeight;
         Image img;
         boolean passed = false;

         public Pipe(Image img){
             this.img = img;
         }
     }


     Bird bird;

    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;

     Timer gameLoop;
     Timer placePipesTimer;

     boolean gameOver = false;

     public FlappyBird(){
         setPreferredSize(new Dimension(boardWidth, boardHeight));
         setFocusable(true);
         addKeyListener(this);
         //setBackground(Color.BLUE);

         // load img
         try {
             backgroundimg = new ImageIcon("E:/project java/practik/bird/src/main/resources/images/flappybirdbg.png").getImage();

             birdimg = new ImageIcon("E:/project java/practik/bird/src/main/resources/images/flappybird.png").getImage();
             bird = new Bird(birdimg);

             pipes = new ArrayList<Pipe>();

             topPipeimg = new ImageIcon("E:/project java/practik/bird/src/main/resources/images/toppipe.png").getImage();
             downPipeimg = new ImageIcon("E:/project java/practik/bird/src/main/resources/images/bottompipe.png").getImage();

             placePipesTimer = new Timer(1500, new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     placePipes();
                 }
             });
             placePipesTimer.start();


             gameLoop = new Timer(1000/60, this);
             gameLoop.start();
         } catch (Exception e){
             System.out.println(e);
         }
     }

     public boolean collision(Bird a, Pipe b){
         return a.x < b.x + b.width &&
                 a.x + a.wigth > b.x &&
                 a.y < b.y + b.height &&
                 a.y + a.height > b.y;
     }

     public void placePipes(){
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4;

        Pipe topPipe = new Pipe(topPipeimg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe downPipe = new Pipe(downPipeimg);
        downPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(downPipe);
     }

     public void paintComponent(Graphics g){
         super.paintComponent(g);
         draw(g);
     }

     public void move(){
         velocityY += gravity;
         bird.y += velocityY;
         bird.y = Math.max(bird.y, 0);

         for (Pipe pipe: pipes){
             pipe.x += velocityX;

             if (collision(bird, pipe)){
                 gameOver = true;
             }
         }

         if (bird.y > boardHeight){
             gameOver = true;
         }
     }


     public void draw(Graphics g){
         g.drawImage(backgroundimg, 0,0, boardWidth, boardHeight,null);

         g.drawImage(bird.img, bird.x, bird.y, bird.wigth, bird.height,null);

         for (Pipe pipe: pipes){
             g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
         }
     }
}
