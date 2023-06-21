
package entities;

import java.awt.geom.Rectangle2D;
import superarturoprat.GameManager;
import static utility.Constants.ANIMATION_SPEED;
import static utility.Constants.EnemyConstants.*;
import static utility.HelpMethods.*;
import static utility.Constants.Directions.*;
import static utility.Constants.GRAVITY;
public abstract class Enemy extends Entity{
    protected int  enemyState;
    
    private int enemyType;
    protected boolean firstUpdate=true;
    protected int walkDir=LEFT;
    protected int tileY;
    protected float attackDistance=128;
    protected boolean active=true;
    protected boolean attackChecked;
    public Enemy(float x, float y, int width, int height, int enemyType,int entityType) {
        super(x, y, width, height,entityType);
        this.enemyType=enemyType;
        maxHealth=getMaxHealth(enemyType);
        currentHealth=maxHealth;
        walkSpeed = GameManager.SCALE*1.0f;
    }
    protected void updateAnimationTick(){
        aniTick ++;
        if(aniTick>=ANIMATION_SPEED){
            aniTick=0;
            aniIndex++;
            if(aniIndex>= getSpriteAmount(enemyType,enemyState)){
                aniIndex=0;
                switch(enemyState){
                    case ATTACKING,HIT -> enemyState=IDLE;
                    case DEAD->active=false;
                }
                
            }
        }
    }
    protected void firstUpdateCheck(int[][] lvlData){
        if(firstUpdate){
            if(!isEntityOnFloor(hitBox,lvlData)){
                inAir=true;
                
            }
        }
        firstUpdate=false;
    }
    protected void updateInAir(int[][] lvlData){
        if(canMoveHere(hitBox.x,hitBox.y,hitBox.width,hitBox.height,lvlData)){
            hitBox.y+=airSpeed;
            airSpeed+=GRAVITY;
        }else{
            inAir=false;
            hitBox.y=GetEntityYPosCollision(hitBox,airSpeed,entityType);
            tileY=(int)(hitBox.y/GameManager.TILE_SIZE);
        }
    }
    protected void move(int[][] lvlData){
        float xSpeed= 0;
        if(walkDir==LEFT){
            xSpeed=-walkSpeed;
        }else{
            xSpeed = walkSpeed;
        }
        if(canMoveHere(hitBox.x+xSpeed,hitBox.y,hitBox.width,hitBox.height,lvlData)){
            if(isFloor(hitBox,xSpeed,lvlData)){
                hitBox.x +=xSpeed;
                return;
            }
        }
        changeWalkDir();
    }

    public int getEnemyState(){
        return enemyState;
    }

    protected void changeWalkDir() {
        if(walkDir==LEFT){
            walkDir=RIGHT;
        }else{
            walkDir=LEFT;
        }
    }
    public void newState(int enemyState){
        this.enemyState=enemyState;
        aniTick=0;
        aniIndex=0;
    }
    protected boolean canSeePlayer(int[][] lvlData,Player player){
        int playerTileY= (int)(player.getHitBox().y/GameManager.TILE_SIZE);
        System.out.println(playerTileY +" "+tileY);
        if(playerTileY==tileY){
            if(isPlayerInRange(player)){
                if(isSightClear(lvlData,hitBox,player.hitBox,tileY)){
                    return true;
                }
            }
        }
        return false;
    }
    protected void turnTowardsPlayer(Player player){
        if(player.hitBox.x >hitBox.x){
            walkDir=RIGHT;
        }else{
            walkDir=LEFT;
        }
    }
    protected boolean isPlayerInAttackRange(Player player){
        int absValue = Math.abs((int)(player.hitBox.x-hitBox.x));
        return absValue <= attackDistance;
    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = Math.abs((int)(player.hitBox.x-hitBox.x));
        return absValue <= attackDistance*5;
    }
    public void hurt(int amount){
        currentHealth-=amount;
        if(currentHealth <=0){
            newState(DEAD);
        }else{
            newState(HIT);
        }
    }
    public boolean isActive(){
        return this.active;
    }
    protected void checkPlayerHit(Rectangle2D.Float attackBox,Player player){
        if(attackBox.intersects(player.getHitBox())){
            player.changeHealth(-getEnemyDamage(enemyType));
        }
        
        attackChecked = true;
    }
    public void resetEnemy(){
        hitBox.x=x;
        hitBox.y=y-64;
        firstUpdate=true;
        currentHealth=maxHealth;
        newState(IDLE);
        active=true;
        airSpeed=0;
    }
   
}
