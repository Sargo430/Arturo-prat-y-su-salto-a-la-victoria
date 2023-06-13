
package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import superarturoprat.GamePanel;
import utility.Constants;
import static utility.Constants.Directions.DOWN;
import static utility.Constants.Directions.LEFT;
import static utility.Constants.Directions.RIGHT;
import static utility.Constants.Directions.UP;
import static utility.Constants.PlayerConstants.getSpriteAmount;
import static utility.Constants.PlayerConstants.getSpriteScale;

/**
 *
 * @author Sebastian
 */
public class Player extends Entity{
    private BufferedImage[][] animations;
    private int animationTick,animationIndex=0;
    private int animationSpeed=7;
    private Constants.PlayerAction playerAction= Constants.PlayerAction.IDLE;
    private int playerDir=-1;
    private boolean moving = false;
    
    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }
    public void update(){
        updateAnimationTick();
        setAnimation();
        updatePos();
    }
    public void render(Graphics g){
        g.drawImage(animations[playerAction.ordinal()][animationIndex],(int)x,(int)y,128*getSpriteScale(playerAction),128,null);
    }
    private void loadAnimations() {
        InputStream is=getClass().getResourceAsStream("/images/arturoPratt.png");;
        try {
            BufferedImage arturoPratt=ImageIO.read(is);
            animations = new BufferedImage[7][16];
            for(int j=0;j<animations.length;j++ ){
                if(j<5){
                    for(int i =0; i<animations[j].length;i++){
                        animations[j][i]= arturoPratt.getSubimage(i*64,j*64,64,64);
                    }
                }else{
                    for(int i =0; i<2;i++){
                        animations[j][i]= arturoPratt.getSubimage(i*64*2,j*64,64*2,64);
                    }
                }
        }
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
                is.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        
    }
    public void setDirection(int direction){
       this.playerDir=direction;
       moving=true;
   }
   public void setMoving(boolean moving){
       this.moving=moving;
   }
   private void updateAnimationTick() {
        animationTick++;
        if (animationTick>=animationSpeed){
            animationTick=0;
            animationIndex++;
            if(animationIndex>=getSpriteAmount(playerAction)){
                animationIndex=0;
            }
        }
    }

    private void setAnimation() {
        if(moving){
            playerAction=Constants.PlayerAction.RUNNING;
        }
        else{
            playerAction=Constants.PlayerAction.IDLE;
        }
    }

    private void updatePos() {
        if(moving){
            switch(playerDir){
                case LEFT:
                    x-=5;
                    break;
                case UP:
                    y-=5;
                    break;
                case RIGHT:
                    x+=5;
                    break;
                case DOWN:
                    y+=5;
                    break;
                    
            }
        }
    }
    
}
