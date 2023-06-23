
package objects;

import superarturoprat.GameManager;

/**
 *
 * @author Sebastian
 */
public class Bullet extends Projectile{
    
    public Bullet(int x, int y, int direction, float speed) {
        super(x, y, direction,speed);
        int xOffset=(int)(0*GameManager.SCALE*3);
        int yOffset=(int)(3*GameManager.SCALE*3);
        if(direction==1){
            xOffset=(int)(29*3*GameManager.SCALE);
        }
        initHitbox(x+ xOffset,y+yOffset,12,6);
    }
    
}
