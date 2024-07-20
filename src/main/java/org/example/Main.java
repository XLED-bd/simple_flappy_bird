package org.example;

import javax.swing.*;

public class Main {

    private static final int boardWidth = 360;
    private static final int boardHeight = 640;

    public static void main(String[] args) {

        FlappyBird bird = new FlappyBird();
        JFrame frame = new JFrame("bird");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.add(bird);
        frame.pack();
        bird.requestFocus();
        frame.setVisible(true);
    }
}