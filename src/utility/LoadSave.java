
package utility;

import entities.Peruano;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import superarturoprat.GameManager;
import superarturoprat.GamePanel;
import static utility.Constants.EnemyConstants.*;


public class LoadSave {
    public static final String PLAYERATLAS="/images/arturoPratt.png";
    public static final String LEVELATLAS="/images/tiles.png";
    
    public static final String MENU_BUTTONS="/images/button_atlas.png";
    public static final String MENU_BACKGROUND="/images/menu_background.png";
    public static final String PAUSE_BACKGROUND="/images/pause_menu.png";
    public static final String SOUND_BUTTONS="/images/sound_button.png";
    public static final String URM_BUTTONS="/images/urm_buttons.png";
    public static final String VOLUME_BUTTONS="/images/volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG="/images/menu_background_img.png";
    public static final String LEVEL01_BACKGROUND="/images/level01_background.png";
    public static final String LEVEL02_BACKGROUND="/images/level02_background.png";
    public static final String LEVEL03_BACKGROUND="/images/level03_background.png";
    public static final String LEVEL04_BACKGROUND="/images/level04_background.png";
    public static final String LEVEL05_BACKGROUND="/images/level05_background.png";
    public static final String LEVEL02_BIGCLOUDS="/images/big_clouds.png";
    public static final String LEVEL02_SECOND_LAYER="/images/secondLayerlvl02.png";
    public static final String LEVEL03_SECOND_LAYER="/images/secondLayerlvl03.png";
    public static final String LEVEL02_SMALLCLOUDS="/images/small_clouds.png";
    public static final String PERUANO_SPRITE="/images/sprite_peruano.png";
    public static final String STATUS_BAR="/images/health_bullet_bar.png";
    public static final String COMPLETED_IMG="/images/completed_sprite.png";
    public static final String POTION_ATLAS="/images/potions_sprites.png";
    public static final String OBJECTS_ATLAS="/images/objects_sprites.png";
    public static final String TRAP_ATLAS="/images/trap_atlas.png";
    public static final String CANNON_ATLAS="/images/cannon_atlas.png";
    public static final String CANNON_BALL="/images/ball.png";
    public static final String DEATH_SCREEN="/images/death_Screen.png";
    public static final String OPTIONS_BACKGROUND="/images/options_background.png";
    public static final String CHANGE_KEY_BACKGROUND="/images/changeKey.png";
    public static final String EMPTY_BUTTON="/images/empty_button.png";
    public static final String INPUTS_BUTTON="/images/inputButtons.png";
    public static final String BULLET="/images/bullet.png";
    public static BufferedImage getSpriteAtlas(String fileName){
        BufferedImage image=null;
        InputStream is=LoadSave.class.getResourceAsStream(fileName);
        try {
            image=ImageIO.read(is);
        
        } catch (IOException ex){
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
                is.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return image;   
    }
    
    public static BufferedImage[] getAllLvLS(){
        URL url = LoadSave.class.getResource("/images/lvls");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(LoadSave.class.getName()).log(Level.SEVERE, null, ex);
        }
        File[] files = file.listFiles();
        File[]fileSorted = new File[files.length];
        for(int i=0;i <fileSorted.length; i++){
            for(int j=0; j< files.length;j++){
                if(files[j].getName().equals((i+1)+(".png"))){
                    fileSorted[i]=files[j];
                }
            }
        }
        BufferedImage[] images =new BufferedImage[fileSorted.length];
        for(int i=0;i<images.length;i++){
            try {
                images[i] = ImageIO.read(fileSorted[i]);
            } catch (IOException ex) {
                Logger.getLogger(LoadSave.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return images;
    }
    
}
