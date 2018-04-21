import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Observable;

import settings.Game_Mode;
import settings.Game_Moves;

public class Tank_Random extends Tank_Main implements Game_Moves {

    Image healthPowerUp = getSprite("assets/powerup_Health.png");
    Image powerup_1 = getSprite("assets/powerup.png");
    Image powerup_2 = getSprite("assets/powerup_2.png");
    ArrayList<Tank_PowerUp> powerUp = new ArrayList();
    ArrayList<Tank_Wall> Walls = new ArrayList();

    public Tank_Random() {
        for (int i = 0; i < borderY / 32; i++) {
            Walls.add(new Tank_Wall(borderX / 2, i * (borderY / 32)));
            Walls.add(new Tank_Wall((borderX / 2) - 32, i * (borderY / 32)));
            Walls.add(new Tank_Wall((borderX / 2) + 32, i * (borderY / 32)));
        }
    }

    @Override
    public void move() {
    }

    @Override
    public void draw(Graphics graphic, ImageObserver observer) {
        for (int i = 0; i < powerUp.size(); i++) {
            if (!powerUp.get(i).update()) {
                powerUp.get(i).draw(graphic, observer);
            } else {
                powerUp.remove(i);
            }
        }

        for (int i = 0; i < Walls.size(); i++) {
            Walls.get(i).update();
            Walls.get(i).draw(graphic, observer);
        }
    }

    public boolean collision(int x, int y, int w, int h) {
        for (int i = 0; i < Walls.size(); i++) {
            if (Walls.get(i).collision(x, y, w, h)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(Observable observer, Object event) {
        Game_Mode gameEvent = (Game_Mode) event;
        if (gameEvent.typeEvent == 3) {
            if ((int) gameEvent.eventID == 0) {
                powerUp.add(new Tank_PowerUp(healthPowerUp, 100, 100, 0, true));
                powerUp.add(new Tank_PowerUp(powerup_1, borderX - 100, borderY - 100, 1, false));
            }
            if ((int) gameEvent.eventID == 1) {
                powerUp.add(new Tank_PowerUp(powerup_2, 700, borderY - 100, 2, false));
                powerUp.add(new Tank_PowerUp(healthPowerUp, borderX - 700, 100, 0, true));
            }
            if ((int) gameEvent.eventID == 2) {
                powerUp.add(new Tank_PowerUp(healthPowerUp, 400, borderY / 2, 0, true));
                powerUp.add(new Tank_PowerUp(powerup_1, borderX - 400, borderY / 2, 1, false));
            }
            if ((int) gameEvent.eventID == 3) {
                powerUp.add(new Tank_PowerUp(powerup_2, 500, borderY / 3, 2, false));
                powerUp.add(new Tank_PowerUp(healthPowerUp, borderX - 500, borderY / 3, 0, true));
            }
        }
    }
}
