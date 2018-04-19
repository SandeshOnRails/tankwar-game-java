package settings;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class Game_Controls extends KeyAdapter {

    Game_Controls game;

    public Game_Controller(Game_Mode game_Event) {
        this.game = game_Event;
    }

    public void keyPressed(KeyEvent key) {
        game.setValue(key, 1);
    }

    public void keyReleased(KeyEvent key) {
        game.setValue(key, 0);
    }


}




