package settings;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;



public interface Game_Background {

     void draw(Graphics2D graphic, ImageObserver observable);
        void game_music();
        void game_over();



}