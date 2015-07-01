package com.jprada.game;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jprada.core.GameWindow;

/**
 * Created by Juan Camilo Prada on 25/06/2014.
 */
public class GameApp {

    public static void main(String args[]) {
    	
        GameWindow.setGameWindowParameters(800, 600, "My Game Application", 0.4f, 0.6f, 0.9f, 0f);
        GameWindow gameWin = GameWindow.createGameWindow();
    }
}
