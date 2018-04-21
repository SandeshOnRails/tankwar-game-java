import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import settings.Game_Update;

public class Tank_Wall extends Tank_Main implements Game_Update {

    Image wallImage = getSprite("settings/Wall2.png");
    int x, y, width, height, invisible, time;
    boolean isVisible;

    Tank_Wall(int x, int y) {
        this.x = x;
        this.y = y;
        width = wallImage.getWidth(null);
        height = wallImage.getHeight(null);
        isVisible = true;
        invisible = 1200;
        time = 0;
    }

    @Override
    public void update() {
        if (isVisible) {
            for (int i = 0; i < tank1_Bullet.size(); i++) {
                if (tank1_Bullet.get(i).collision(x + 20, y, width - 20, height)) {
                    isVisible = false;
                    explosionSound_2.play();
                }
            }
            for (int i = 0; i < tank2_Bullet.size(); i++) {
                if (tank2_Bullet.get(i).collision(x + 20, y, width - 20, height)) {
                    isVisible = false;
                    explosionSound_2.play();
                }
            }
        } else {
            time++;
            if ((time >= invisible) && (!player_Tanks[1].collision(x, y, width, height)) && (!player_Tanks[2].collision(x, y, width, height))) {
                isVisible = true;
                time = 0;
            }
        }
    }

    @Override
    public void draw(Graphics graphic, ImageObserver observer_2) {
        if (isVisible) {
            graphic.drawImage(wallImage, x, y, observer);
        }
    }

    public boolean collision(int x, int y, int w, int h) {
        if (isVisible) {
            if ((y + h > this.y) && (y < this.y + height)) {
                if ((x + w > this.x) && (x < this.x + width)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
