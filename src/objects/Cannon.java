
package objects;

import superarturoprat.GameManager;


public class Cannon extends GameObjects {
    private int tileY;
    
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
    
}
