
package objects;

import superarturoprat.GameManager;
import static utility.Constants.ObjectConstants.BOX;


public class GameContainer extends GameObjects{
    
    public GameContainer(int x, int y, int objectType) {
        super(x, y, objectType);
        createHitbox();
    }

    private void createHitbox() {
        if(objectType == BOX){
            initHitbox(25*2,18*2);
            xDrawOffset =(int)(7*GameManager.SCALE*2);
            yDrawOffset =(int)(12*GameManager.SCALE);
        }else{
           initHitbox(23,25);
            xDrawOffset =(int)(8*GameManager.SCALE*2);
            yDrawOffset =(int)(6*GameManager.SCALE*2); 
        }
        hitbox.y -= yDrawOffset +(int)(GameManager.SCALE+1);
        hitbox.x -= xDrawOffset/2 +3;
    }
    public void update(){
        if(doAnimation){
            updateAnimationTick();
        }
    }
    
}
