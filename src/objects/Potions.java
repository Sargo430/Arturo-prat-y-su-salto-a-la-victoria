
package objects;

import superarturoprat.GameManager;


public class Potions extends GameObjects{
    private float hoverOffset;
    private int maxHoverOffset,hoverDirection =1;
    
    public Potions(int x, int y, int objectType) {
        super(x, y, objectType);
        doAnimation=true;
        initHitbox(7*2,14*2);
        xDrawOffset=(int)(3*GameManager.SCALE*2);
        yDrawOffset=(int)(2*GameManager.SCALE*2);
        maxHoverOffset =(int)(15*GameManager.SCALE);
        
    }
    public void update(){
        updateAnimationTick(); 
        updateHover();
    }

    private void updateHover() {
        hoverOffset +=(0.075f * GameManager.SCALE *hoverDirection);
        if(hoverOffset >= maxHoverOffset){
            hoverDirection=-1;
        }else if (hoverOffset<0){
            hoverDirection=1;
        }
        hitbox.y=y + hoverOffset;
    }
    
}
