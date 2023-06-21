
package objects;

import superarturoprat.GameManager;

/**
 *
 * @author Sebastian
 */
public class Spike extends GameObjects{
    
    public Spike(int x, int y, int objectType) {
        super(x, y, objectType);
        initHitbox(64,32);
        xDrawOffset=0;
        yDrawOffset=(int)(32*GameManager.SCALE);
        hitbox.y +=yDrawOffset;
    }
    
}
