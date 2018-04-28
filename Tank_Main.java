import static java.applet.Applet.newAudioClip;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;

import settings.Game_Controls;
import settings.Game_Mode;
import settings.Game_Frame;

public class Tank_Main extends JApplet implements Runnable {

    static HashMap<Integer, String> tank_movement = new HashMap<>();
    static int borderX = 1280, borderY = 720;
    static int screenWidth = 1280, screenHeight = 720, displayPlayer_X1, displayPlayer_Y1, displayPlayer_X2, displayPlayer_Y2;
    static boolean gameCheck = false;
    private Thread main_Thread;
    BufferedImage buffer_Img, buffer_Img2;
    ImageObserver observer;
    Game_Frame timeCounter;
    Game_Mode gameEvent;
    Game_Controls gameController;
    Tank_Background theBackground;
    static Tank_Player tank_1, tank_2;
    static Tank_Random random_Object;
    static Tank_Player[] player_Tanks = new Tank_Player[3];
    static Image[] tank_hp;
    static ArrayList<Tank_Bullet> tank1_Bullet, tank2_Bullet;
    static AudioClip explosionSound_1, explosionSound_2;
    Font score = new Font("Arial", Font.BOLD, 24);
    Font lives = new Font("Stencil", Font.BOLD, 40);

    public static void main(String[] args) {
        final Tank_Main beta = new Tank_Main();
        beta.init();
        JFrame frame = new JFrame("Tank Wars v1");
        frame.addWindowListener(new WindowAdapter() {});
        frame.getContentPane().add("Center", beta);
        frame.pack();
        frame.setSize(new Dimension(screenWidth, screenHeight));
        frame.setVisible(true);
        frame.setResizable(false);
        beta.start();

    }

    public void init() {
        tank_movement.put(KeyEvent.VK_A, "left1");
        tank_movement.put(KeyEvent.VK_W, "up1");
        tank_movement.put(KeyEvent.VK_S, "down1");
        tank_movement.put(KeyEvent.VK_D, "right1");
        tank_movement.put(KeyEvent.VK_SPACE, "shoot1");
        tank_movement.put(KeyEvent.VK_LEFT, "left2");
        tank_movement.put(KeyEvent.VK_UP, "up2");
        tank_movement.put(KeyEvent.VK_DOWN, "down2");
        tank_movement.put(KeyEvent.VK_RIGHT, "right2");
        tank_movement.put(KeyEvent.VK_ENTER, "shoot2");

        Image[] health_Frames = {getSprite("assets/health_1.png"),
                getSprite("assets/health_2.png"),
                getSprite("assets/health_3.png"),
                getSprite("assets/health_4.png"),
                getSprite("assets/health_5.png"),
                getSprite("assets/explosion2_1.png"),
                getSprite("assets/explosion2_2.png"),
                getSprite("assets/explosion2_3.png"),
                getSprite("assets/explosion2_4.png"),
                getSprite("assets/explosion2_5.png"),
                getSprite("assets/explosion2_6.png"),
                getSprite("assets/explosion2_7.png")};
        tank_hp = health_Frames;


        Image bullets[] = {getSprite("assets/tinyBullet2.png"),
                getSprite("assets/tinyBullet2.png"), getSprite("assets/tinyBullet2.png")};

        theBackground = new Tank_Background();

        String tank1_Images = "assets/Tank_blue_light_strip60.png";
        String tank2_Images = "assets/Tank_red_light_strip60.png";

        tank1_Bullet = new ArrayList();
        tank2_Bullet = new ArrayList();

        tank_1 = new Tank_Player(tank1_Images, tank2_Bullet, tank1_Bullet, bullets, 1);
        tank_2 = new Tank_Player(tank2_Images, tank1_Bullet, tank2_Bullet, bullets, 2);

        player_Tanks[2] = tank_1;
        player_Tanks[1] = tank_2;

        random_Object = new Tank_Random();
        setBackground(Color.black);
        this.setFocusable(true);
        observer = this;
        gameEvent = new Game_Mode();
        gameEvent.addObserver(tank_1);
        gameEvent.addObserver(tank_2);
        gameEvent.addObserver(random_Object);

        int timedEvents[] = {1000, 800, 900, 700};
        timeCounter = new Game_Frame(gameEvent, timedEvents, 1);

        gameController = new Game_Controls(gameEvent);
        addKeyListener(gameController);

        explosionSound_1 = getAudioFile("assets/Explosion_large.wav");
        explosionSound_2 = getAudioFile("assets/Explosion_small.wav");

    }

    private AudioClip getAudioFile(String file) {
        URL url = Tank_Main.class.getResource(file);
        return newAudioClip(url);
    }

    public void start() {
        theBackground.game_music();
        main_Thread = new Thread(this);
        main_Thread.setPriority(Thread.MIN_PRIORITY);
        main_Thread.start();
    }

    @Override
    public void run() {
        Thread me = Thread.currentThread();
        while (main_Thread == me) {
            repaint();
            try {
                main_Thread.sleep(20);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void paint(Graphics graphic) {
        Dimension dimensions = getSize();
        Graphics2D graphic2D_1 = createGraphics2D(borderX, borderY);
        Graphics2D graphic2d_2 = createOuterGraphics2D(borderX, borderY);
        updateAndDisplay(borderX, borderY, graphic2D_1);
        graphic2D_1.dispose();

        if (!gameCheck) {
            displayPlayer_X1 = tank_1.x + 30 - screenWidth / 4;
            if (displayPlayer_X1 < 0) {
                displayPlayer_X1 = 0;
            } else if (displayPlayer_X1 + screenWidth / 2 > borderX) {
                displayPlayer_X1 = borderX - screenWidth / 2;
            }
            displayPlayer_Y1 = tank_1.y + 30 - screenHeight / 2;
            if (displayPlayer_Y1 < 0) {
                displayPlayer_Y1 = 0;
            } else if (displayPlayer_Y1 + screenHeight > borderY) {
                displayPlayer_Y1 = borderY - screenHeight;
            }

            //Player 2's view
            displayPlayer_X2 = tank_2.x + 30 - screenWidth / 4;
            if (displayPlayer_X2 < 0) {
                displayPlayer_X2 = 0;
            } else if (displayPlayer_X2 + screenWidth / 2 > borderX) {
                displayPlayer_X2 = borderX - screenWidth / 2;
            }
            displayPlayer_Y2 = tank_2.y + 30 - screenHeight / 2;
            if (displayPlayer_Y2 < 0) {
                displayPlayer_Y2 = 0;
            } else if (displayPlayer_Y2 + screenHeight > borderY) {
                displayPlayer_Y2 = borderY - screenHeight;
            }
            graphic2d_2.drawImage(buffer_Img.getSubimage(displayPlayer_X1, displayPlayer_Y1, screenWidth / 2, screenHeight), 0, 0, this);
            graphic2d_2.drawImage(buffer_Img.getSubimage(displayPlayer_X2, displayPlayer_Y2, screenWidth / 2, screenHeight), screenWidth / 2, 0, this);
            graphic2d_2.drawLine(dimensions.width / 2 + 2, 0, dimensions.width / 2 + 2, dimensions.height);
            graphic2d_2.setFont(score);
            graphic2d_2.setColor(Color.WHITE);
            graphic2d_2.drawString(tank_1.points + "", dimensions.width / 4, 40);
            graphic2d_2.setColor(Color.WHITE);
            graphic2d_2.drawString(tank_2.points + "", dimensions.width * 3 / 4, 40);
            graphic2d_2.setFont(lives);
            graphic2d_2.setColor(Color.WHITE);
            graphic2d_2.drawString("P1 Lives: " + tank_1.lives + "", 40, 40);
            graphic2d_2.drawString("P2 Lives: " + tank_2.lives + "", dimensions.width - 240, 40);
            graphic2d_2.drawRect(dimensions.width / 2 - (dimensions.width / 5) / 2 - 1, dimensions.height * 3 / 4 - 1, dimensions.width / 5 + 1, dimensions.height / 5 + 1);
            graphic2d_2.drawImage(buffer_Img.getScaledInstance(dimensions.width / 5, dimensions.height / 5, 1), dimensions.width / 2 - (dimensions.width / 5) / 2, dimensions.height * 3 / 4, this);
            graphic2d_2.dispose();
            graphic.drawImage(buffer_Img2, 0, 0, this);
        } else {
            graphic.drawImage(buffer_Img, 0, 0, this);
        }

    }

    public Graphics2D createOuterGraphics2D(int w, int h) {
        Graphics2D graphics = null;
        if (buffer_Img2 == null || buffer_Img2.getWidth() != w || buffer_Img2.getHeight() != h) {
            buffer_Img2 = (BufferedImage) createImage(w, h);
        }
        graphics = buffer_Img2.createGraphics();
        graphics.setBackground(getBackground());
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        graphics.clearRect(0, 0, w, h);
        return graphics;
    }

    public Graphics2D createGraphics2D(int w, int h) {
        Graphics2D graphics = null;
        if (buffer_Img == null || buffer_Img.getWidth() != w || buffer_Img.getHeight() != h) {
            buffer_Img = (BufferedImage) createImage(w, h);
        }
        graphics = buffer_Img.createGraphics();
        graphics.setBackground(getBackground());
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        graphics.clearRect(0, 0, w, h);
        return graphics;
    }

    public void updateAndDisplay(int w, int h, Graphics2D graphics) {

        if (!gameCheck) {
            timeCounter.play();

            theBackground.draw(graphics, this);

            tank_1.move();
            tank_1.draw(graphics, this);

            tank_2.move();
            tank_2.draw(graphics, this);

            random_Object.draw(graphics, this);


            for (int i = 0; i < tank1_Bullet.size(); i++) {
                if (tank1_Bullet.get(i).move()) {
                    tank1_Bullet.remove(i);
                } else {
                    tank1_Bullet.get(i).draw(graphics, this);
                }
            }

            for (int i = 0; i < tank2_Bullet.size(); i++) {
                if (tank2_Bullet.get(i).move()) {
                    tank2_Bullet.remove(i);
                } else {
                    tank2_Bullet.get(i).draw(graphics, this);
                }
            }
        } else {
            theBackground.game_over();
            theBackground.draw(graphics, this);
            String gameOverMsg;
            String gameOverMsg1;
            String gameOverMsg2;
            if (tank_1.points > tank_2.points) {
                gameOverMsg = "Player1: " + (player_Tanks[2].points+5);
                gameOverMsg1 = "Player2: " + player_Tanks[1].points;
                gameOverMsg2 = "Player1 Wins !!";

            } else {
                gameOverMsg = "Player2: " + (player_Tanks[1].points+5);
                gameOverMsg1 = "Player1: " + player_Tanks[2].points;

                gameOverMsg2 = "Player2 Wins !!";

            }
            Font victory_font = new Font("Osaka", Font.BOLD, 60);
            graphics.setFont(victory_font);

            FontRenderContext context = graphics.getFontRenderContext();
            Rectangle2D bounds = victory_font.getStringBounds(gameOverMsg, context);

            double x = (getWidth() - bounds.getWidth()) / 2;
            double y = (getHeight() - bounds.getHeight()) / 2;
            double ascent = -bounds.getY();
            double baseY = y + ascent;

            graphics.setPaint(Color.YELLOW);

            graphics.drawString(gameOverMsg, (int) x, (int) baseY);



            FontRenderContext context1 = graphics.getFontRenderContext();
            Rectangle2D bounds1 = victory_font.getStringBounds(gameOverMsg1, context1);

            double x1 = (getWidth() - bounds1.getWidth()) / 2;
            double y1 = 403.01;
            double ascent1 = -bounds1.getY();
            double baseY1 = y1 + ascent1;

            graphics.setPaint(Color.YELLOW);

            graphics.drawString(gameOverMsg1, (int) x1, (int) baseY1);


            Font victory_font2 = new Font("Osaka", Font.BOLD, 60);
            graphics.setFont(victory_font2);

            FontRenderContext context2 = graphics.getFontRenderContext();
            Rectangle2D bounds2 = victory_font2.getStringBounds(gameOverMsg2, context2);

            double x2 = (getWidth() - bounds2.getWidth()) / 2;
            double y2 = 503 ;
            double ascent2 = -bounds.getY();
            double baseY2 = y2 + ascent2;



            graphics.setPaint(Color.YELLOW);

            graphics.drawString(gameOverMsg2, (int) x2, (int) baseY2);
        }
    }

    public Image getSprite(String resource) {
        URL url = Tank_Main.class.getResource(resource);
        Image img = getToolkit().getImage(url);
        try {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(img, 0);
            tracker.waitForID(0);
        } catch (Exception e) {
            System.out.println(e + " image doesn't match. Try again.");
        }
        return img;
    }

    public BufferedImage getBufferedImage(String image) throws IOException {
        URL url = Tank_Main.class.getResource(image);
        BufferedImage img = ImageIO.read(url);
        try {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(img, 0);
            tracker.waitForID(0);
        } catch (Exception e) {
            System.out.println(e + " image doesn't match. Try again.");
        }
        return img;
    }
}
