package settings;


import java.awt.Graphics;
import java.awt.image.ImageObserver;

public interface Game_Update {
    void update();
    void draw(Graphics graphic, ImageObserver observer);
}
