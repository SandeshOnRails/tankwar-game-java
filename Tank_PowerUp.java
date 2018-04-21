import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import settings.Game_Update;

public class Tank_PowerUp extends Tank_Main {
    Image image;
    int x, y, damage, width, height;
    boolean health;


    Tank_PowerUp(Image img, int x, int y, int damageChange, boolean healthChange) {
        this.x = x;
        this.y = y;
        this.image = img;
        this.damage = damageChange;
        this.health = healthChange;
        width = img.getWidth(null);
        height = img.getWidth(null);
    }

    public boolean update() {
        if(tank_1.collision(x, y, width, height)) {
            if(health) tank_1.health_count = tank_1.health;
            tank_1.bulletType = damage;
            return true;
        } else if(tank_2.collision(x, y, width, height)) {
            if(health) tank_2.health_count = tank_2.health;
            tank_2.bulletType = damage;
            return true;
        } else {
            return false;
        }
    }

    public void draw(Graphics graphic, ImageObserver observer) {
        graphic.drawImage(image, x, y, observer);
    }

}
