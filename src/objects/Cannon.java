
package objects;

import superarturoprat.GameManager;


public class Cannon extends GameObjects {
    private int tileY;
    private int cdTick=501;
    public Cannon(int x, int y, int objectType) {
        super(x, y, objectType);
        tileY = y/GameManager.TILE_SIZE;
        initHitbox(40*2,26*2);
        hitbox.x-=(int)(28*GameManager.SCALE);
        hitbox.y -=(int)(13*GameManager.SCALE);
        
    }
    public void update(){
        if(doAnimation){
            updateAnimationTick();
        }
    }
    public int getTileY(){
        return tileY;
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
