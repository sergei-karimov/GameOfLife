package org.bmsoft;

import org.bmsoft.game.Game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Game started");
        Game game = new Game();
        game.frame.setResizable(false);
        game.frame.setTitle("Game of life");
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();

    }
}