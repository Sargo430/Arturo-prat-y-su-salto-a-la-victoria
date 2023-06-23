
package objects;

import superarturoprat.GameManager;
import static utility.Constants.Projectiles.CANNON_BALL_HEIGHT;
import static utility.Constants.Projectiles.CANNON_BALL_WIDTH;

/**
 *
 * @author Sebastian
 */
public class CannonBall extends Projectile{
    
    public CannonBall(int x, int y, int direction,float speed) {
        super(x, y, direction,speed);
        
        int xOffset=(int)(-3*GameManager.SCALE*3);
        int yOffset=(int)(5*GameManager.SCALE*3);
        if(direction==1){
            xOffset=(int)(29*3*GameManager.SCALE);
        }
        initHitbox(x+ xOffset,y+yOffset,CANNON_BALL_WIDTH,CANNON_BALL_HEIGHT);
    }
    
}
