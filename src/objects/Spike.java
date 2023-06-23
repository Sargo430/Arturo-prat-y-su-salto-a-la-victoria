
package objects;

import superarturoprat.GameManager;

/**
 *
 * @author Sebastian
 */
public class Spike extends GameObjects{
    private int cdTick;
    public Spike(int x, int y, int objectType) {
        super(x, y, objectType);
        initHitbox(64,32);
        xDrawOffset=0;
        yDrawOffset=(int)(32*GameManager.SCALE);
        hitbox.y +=yDrawOffset;
    }
    public boolean cooldown(int cd){
        
        return cdTick>cd;
        
    }
    public void updateCdTick(){
        cdTick++;
    }
    public void resetCdTick(){
        cdTick=0;
    }
    
}
