
package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import superarturoprat.GameManager;
import static utility.Constants.ANIMATION_SPEED;
import static utility.Constants.ObjectConstants.*;


public class GameObjects {
    protected int x,y,objectType;
    protected Rectangle2D.Float hitbox;
    protected boolean doAnimation,active=true;
    protected int aniTick,aniIndex;
    protected int xDrawOffset, yDrawOffset;
    public GameObjects(int x, int y, int objectType){
        this.x=x;
        this.y=y;
        this.objectType=objectType;
        
    }
    protected void initHitbox( int width, int height) {
        hitbox = new Rectangle2D.Float(x,y,(int)(width*GameManager.SCALE),(int)(height*GameManager.SCALE));
    }
    public void drawHitbox(Graphics g, int xLvlOffset){
        g.setColor(Color.PINK);
        g.drawRect((int)hitbox.x-xLvlOffset,(int)hitbox.y,(int)hitbox.width,(int)hitbox.height);
    }
    protected void updateAnimationTick(){
        aniTick ++;
        if(aniTick>=20){
            aniTick=0;
            aniIndex++;
            if(aniIndex>= getSpriteAmount(objectType)){
                aniIndex=0;
                if(objectType == BARREL || objectType == BOX){
                    doAnimation =false;
                    active=false;
                    
                }else if(objectType == CANNON_LEFT||objectType == CANNON_RIGHT){
                    doAnimation = false;
                }
            }
        }
    }
    public void reset(){
        aniIndex=0;
        aniTick=0;
        active=true;
        if(objectType == BARREL || objectType == BOX || objectType == CANNON_RIGHT||objectType == CANNON_LEFT){
            doAnimation =false;           
        }else{
            doAnimation=true;
        }
    }

    public int getObjectType() {
        return objectType;
    }
    public boolean isDoAnimation() {
        return doAnimation;
    }

    public void setDoAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }

    public boolean isActive() {
        return active;
    }
    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public void setActive(boolean active){
        this.active=active;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    } 
    public int getAniIndex(){
        return aniIndex;
    }
    public void setAnimation(boolean doAnimation){
        this.doAnimation=doAnimation;
    }
    public int getAniTick(){
        return aniTick;
    }

    
    
}
