package settings;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

public interface Game_Moves extends Observer {
    public void move();
    public void draw(Graphics graphic, ImageObserver observer);
    public void update(Observable object, Object argument);
    public boolean collision(int x, int y, int w, int h);
    boolean move();
    public void draw(Graphics graphic, ImageObserver observer);
}
