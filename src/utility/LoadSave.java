
package utility;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import superarturoprat.GameManager;
import superarturoprat.GamePanel;


public class LoadSave {
    public static final String PLAYERATLAS="/images/arturoPratt.png";
    public static final String LEVELATLAS="/images/tiles.png";
    //public static final String LEVEL_ONE_DATA="/images/level01.png";
    public static final String LEVEL_ONE_DATA="/images/level01_long.png";
    public static final String MENU_BUTTONS="/images/button_atlas.png";
    public static final String MENU_BACKGROUND="/images/menu_background.png";
    public static final String PAUSE_BACKGROUND="/images/pause_menu.png";
    public static final String SOUND_BUTTONS="/images/sound_button.png";
    public static final String URM_BUTTONS="/images/urm_buttons.png";
    public static final String VOLUME_BUTTONS="/images/volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG="/images/menu_background_img.png";
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
    public static int[][] getLevelData(){
        
        BufferedImage image= getSpriteAtlas(LEVEL_ONE_DATA);
        int[][] lvlData = new int[image.getHeight()][image.getWidth()];
        for(int j=0;j< image.getHeight();j++){
            for(int i=0; i< image.getWidth();i++){
                Color color = new Color(image.getRGB(i, j));
                int value= color.getRed();
                if(value>21){
                    value=0;
                }
                lvlData[j][i]= value;
            }
        }
        return lvlData;
    }
}
