 import static java.applet.Applet.newAudioClip;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;

import settings.Game_Background;

public class Tank_Background extends Tank_Main implements Game_Background {

    BufferedImage Buffered_Map;
    BufferedImage background_map;
    
    URL url_sound = Tank_Main.class.getResource("assets/music.wav");
    AudioClip backgroundMusic = newAudioClip(url_sound);
    Boolean gameOver = true;
    
    public Tank_Background(){
        try{
        File file = new File(getClass().getResource("assets/Background.bmp").toURI());
    
     this.background_map = ImageIO.read(file);

        }
    catch(Exception e){
        System.out.println( e.getMessage());
    }
    }

    public void draw(Graphics2D g, ImageObserver observer) {
       
       g.drawImage(background_map, 0, 0, this);
       int width = this.background_map.getWidth();
       
       for(int i =0; i < 4; i ++){
           
           g.drawImage(this.background_map, width,0 , this);
           width = width + 320;
       }
       
       width =0;
       int height = this.background_map.getHeight();
       
       
       for(int i =0; i < 4; i++){
       
           g.drawImage(this.background_map, width,height, this);
           
           width = width + 320;
           
       }
       width =0;
       height = height + 240;
       for(int i =0; i < 4; i++){
           g.drawImage(this.background_map, width, height, this);
           width += 320;
       }
       
       
       
    }

    @Override
    public void game_music() {
        backgroundMusic.loop();
    }

    @Override
    public void game_over() {
        if (gameOver) {

            backgroundMusic.stop();
            gameOver = false;
        }
    }


}
