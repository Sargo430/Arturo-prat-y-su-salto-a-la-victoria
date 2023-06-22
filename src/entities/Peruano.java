
package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import superarturoprat.GameManager;
import static utility.Constants.Directions.LEFT;
import static utility.Constants.EnemyConstants.*;

import static utility.Constants.Directions.*;
/**
 *
 * @author Sebastian
 */
public class Peruano extends Enemy{
    public Peruano(float x, float y) {
        super(x, y,PERUANO_WIDTH, PERUANO_HEIGHT, PERUANO,1);
        initHitbox(x,y-64,(int)(28*2),(int)(116));
        initAttackBox();
    }
    private void updateBehaviour(int[][]lvlData,Player player){
        if(firstUpdate){
            firstUpdateCheck(lvlData);
        }
        if(inAir){
            updateInAir(lvlData);
        }else{
           switch(enemyState){
               case IDLE:
                   newState(RUNNING);
                   break;
               case RUNNING:
                   if(canSeePlayer(lvlData,player)){
                       turnTowardsPlayer(player);
                       if(isPlayerInAttackRange(player) && cooldown(250)){
                       newState(ATTACKING);
                   }
                   
                   }
                   move(lvlData);
                   
                   break;
               case ATTACKING:
                   if(aniIndex==0){
                       attackChecked=false;
                   }
                   if(aniIndex==1 && !attackChecked){
                       checkPlayerHit(attackBox,player);
                       resetCdTick();
                   }
                   break;
               case HIT:
                   break;
           }  
        }
    }
    public void update(int[][]lvlData,Player player){
        updateBehaviour(lvlData,player);
        updateAnimationTick();
        updateCdTick();
        updateAttackBox();
    }
    public int flipX(){
        if(walkDir==RIGHT){
            return 0;
        }else{
            return width;
        }
    }
    public int flipW(){
        if(walkDir==RIGHT){
            return 1;
        }else{
            return -1;
        }
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,(int)(58*GameManager.SCALE*2),(int)(48*GameManager.SCALE*2));
    }

    private void updateAttackBox() {
        if(walkDir==RIGHT){
            attackBox.x=hitBox.x+hitBox.width+ (int)(5*GameManager.SCALE*2);
        }else if(walkDir==LEFT){
            attackBox.x=hitBox.x-hitBox.width - (int)(34*GameManager.SCALE*2);
        }
        attackBox.y=hitBox.y + (10 * GameManager.SCALE);
    }
    
}
