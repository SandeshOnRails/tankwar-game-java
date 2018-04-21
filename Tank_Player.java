package Game;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import tank_misc.Game_Mode;
import tank_misc.Game_Moves;

public class Tank_Player extends Tank_Main implements Game_Moves {

    private BufferedImage tank_strip, setImage;
    private ArrayList<Tank_Bullet> Bullets, currentBullets;
    private Image[] bulle_strip;
    int centerX, centerY, bulletType = 0, bulletXSpeed = 0, bulletYSpeed = 0;
    int tankWidth, tankHeight, x, y;
    final int health = 4;
    int health_count, points = 0;
    int speed = 3, angleDirection = 0, movementRate = 0, playerLocation, lives = 2, cooldown = 4;
    ImageObserver observer;
    int xSpeed = 0, ySpeed = 0;
    private String controller;

    Tank_Player(String tank_Img, ArrayList bullets, ArrayList currentbullet, Image bulletstrip[], int playerLoc) {
        this.bulle_strip = bulletstrip;
        try {
            this.tank_strip = getBufferedImage(tank_Img);
        } catch (IOException e) {
            System.out.println(e);
        }
        this.Bullets = bullets;
        this.currentBullets = currentbullet;
        tankWidth = this.tank_strip.getWidth() / 60;
        tankHeight = this.tank_strip.getHeight();
        x = playerLoc * borderX / 3;
        y = borderY / 2;
        health_count = health;
        this.playerLocation = playerLoc;
        this.controller = playerLoc + "";
        centerX = x + tankWidth / 4;
        centerY = y + tankHeight / 4;
    }

    public void draw(Graphics graphic, ImageObserver observer) {
        if (health_count > 0) {
            setImage = tank_strip.getSubimage(tankWidth * (angleDirection / 6), 0, tankWidth, tankHeight);
            graphic.drawImage(setImage, x, y, observer);
            graphic.drawImage(tank_hp[4 - health_count], x + 5, y - 10, observer);
        } else if (health_count <= 0 && lives > 0) {
            graphic.drawImage(tank_hp[cooldown++], x, y, observer);
            if (cooldown == 12) {
                cooldown = 5;
                health_count = health;
                x = playerLocation * borderX / 3;
                y = playerLocation * borderY / 3;
                lives--;
                player_Tanks[playerLocation].points += 5;
                player_Tanks[playerLocation].x = (player_Tanks[playerLocation].playerLocation) * borderX / 3;
                player_Tanks[playerLocation].y = (player_Tanks[playerLocation].playerLocation) * borderY / 3;
                explosionSound_1.play();
            }
        } else {
            gameCheck = true;
        }
    }

    public void update(Observable observer, Object eventType) {
        Game_Mode gameEventHandle = (Game_Mode) eventType;
        if (gameEventHandle.typeEvent <= 1) {
            KeyEvent key = (KeyEvent) gameEventHandle.eventID;
            String tank_motion = tank_movement.get(key.getKeyCode());
            if (tank_motion.equals("left" + controller)) {
                movementRate = 6 * gameEventHandle.typeEvent;
            } else if (tank_motion.equals("right" + controller)) {
                movementRate = -6 * gameEventHandle.typeEvent;
            } else if (tank_motion.equals("up" + controller)) {
                ySpeed = (int) (-1 * speed * Math.sin(Math.toRadians(angleDirection))) * gameEventHandle.typeEvent;
                xSpeed = (int) (speed * Math.cos(Math.toRadians(angleDirection))) * gameEventHandle.typeEvent;
            } else if (tank_motion.equals("down" + controller)) {
                ySpeed = (int) (speed * Math.sin(Math.toRadians(angleDirection))) * gameEventHandle.typeEvent;
                xSpeed = (int) (-1 * speed * Math.cos(Math.toRadians(angleDirection))) * gameEventHandle.typeEvent;
            } else if (tank_motion.equals("shoot" + controller)) {
                if (gameEventHandle.typeEvent == 0) {
                    bulletXSpeed = (int) (15 * Math.cos(Math.toRadians(angleDirection)));
                    bulletYSpeed = (int) (-15 * Math.sin(Math.toRadians(angleDirection)));
                    centerX = x + tankWidth / 4 + bulletXSpeed * 2;
                    centerY = y + tankHeight / 4 + bulletYSpeed * 2;
                    currentBullets.add(new Tank_Bullet(bulle_strip[bulletType], centerX, centerY, bulletXSpeed, bulletYSpeed));
                }

            }
        }
    }

    public void move() {
        angleDirection += movementRate;
        if (angleDirection == -6) {
            angleDirection = 354;

        } else if (angleDirection == 360) {
            angleDirection = 0;
        }
        if ((x + xSpeed < borderX - 70) && (x + xSpeed > 0)
                && (!(player_Tanks[playerLocation].collision(x + xSpeed, y, tankWidth, tankHeight)))
                && (!(random_Object.collision(x + xSpeed, y, tankWidth, tankHeight)))) {
            x += xSpeed;
        }
        if ((y + ySpeed < borderY - 88) && (y + ySpeed > 0)
                && (!(player_Tanks[playerLocation].collision(x, y + ySpeed, tankWidth, tankHeight)))
                && (!(random_Object.collision(x, y + ySpeed, tankWidth, tankHeight)))) {
            y += ySpeed;
        }

        for (int i = 0; i < Bullets.size(); i++) {
            if (Bullets.get(i).collision(x + 20, y, tankWidth - 20, tankHeight)) {
                if (health_count >= 1) {
                    health_count -= (player_Tanks[playerLocation].bulletType + 1);
                    explosionSound_2.play();
                }
            }
        }

    }


    public boolean collision(int x, int y, int w, int h) {
        x += 5;
        y += 5;
        w -= 10;
        h -= 15;
        if ((y + h > this.y) && (y < this.y + tankHeight)) {
            if ((x + w > this.x) && (x < this.x + tankWidth)) {
                return true;
            }
        }
        return false;
    }

}
