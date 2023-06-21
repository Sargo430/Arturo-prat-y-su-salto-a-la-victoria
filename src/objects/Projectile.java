
package objects;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import static utility.Constants.Projectiles.*;

public class Projectile {
   private Rectangle2D.Float hitbox;
   private int direction; 
   private boolean active=true;
   public Projectile(int x, int y,int direction){
       this.direction=direction;

   }
   public void updatePos(){
       hitbox.x +=direction*SPEED;
   }
   public void setPos(int x,int y){
       hitbox.x=x;
       hitbox.y=y;
       
   }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    protected void initHitbox(int x, int y,int width,int height){
        hitbox=new Rectangle2D.Float(x,y,width,height);
    }
   
}
