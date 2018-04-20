package settings;


import java.awt.Graphics;
import java.awt.image.ImageObserver;

public interface Game_Projectiles {
    public boolean collision(int x, int y, int w, int h);
    boolean move();
    public void draw(Graphics graphic, ImageObserver observer);
}

