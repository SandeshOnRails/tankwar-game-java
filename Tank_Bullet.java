
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import settings.Game_Projectile;

public class Tank_Bullet extends Tank_Main implements Game_Projectile {

    Image image_Bullet;
    int x, y, sizeX, sizeY, xSpeed, ySpeed;
    ImageObserver observer;

    Tank_Bullet(Image image, int x, int y, int xSpeed, int ySpeed) {
        this.image_Bullet = image;
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        sizeX = image.getWidth(null);
        sizeY = image.getHeight(null);
    }

    public void draw(Graphics graphic, ImageObserver observer) {
        graphic.drawImage(image_Bullet, x, y, observer);
    }

    @Override
    public boolean move() {
        y += ySpeed;
        x += xSpeed;
        if ((x > borderX + sizeX || x < -1 * sizeX) || (y > borderY + sizeY || y < -1 * sizeY)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean collision(int x, int y, int w, int h) {
        if ((y + h > this.y) && (y < this.y + sizeY)) {
            if ((x + w > this.x) && (x < this.x + sizeX)) {
                this.x = 2 * borderX;
                this.y = 2 * borderY;
                return true;
            }
        }
        return false;
    }
}
