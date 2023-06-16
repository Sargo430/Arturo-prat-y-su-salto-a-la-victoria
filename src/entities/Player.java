
package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import superarturoprat.GameManager;
import utility.Constants;
import utility.HelpMethods.*;

import static utility.Constants.PlayerConstants.getSpriteAmount;
import static utility.Constants.PlayerConstants.getSpriteScale;
import static utility.HelpMethods.GetEntityYPosCollision;
import static utility.HelpMethods.canMoveHere;
import static utility.HelpMethods.getEntityXPosNextToWall;
import static utility.HelpMethods.isEntityOnFloor;
import utility.LoadSave;

/**
 *
 * @author Sebastian
 */
public class Player extends Entity{
    private BufferedImage[][] animations;
    private int animationTick,animationIndex=0;
    private int animationSpeed=7;
    private Constants.PlayerAction playerAction= Constants.PlayerAction.IDLE;
    private boolean left,up,right,down,jump=false;
    private boolean moving,swordAttacking,gunAttacking = false;
    private float playerSpeed=3.0f*GameManager.SCALE;   
    private int[][] lvlData;
    private float xDrawOffset=18*2;
    private float yDrawOffset=0*2;
    private float airSpeed=0;
    private float gravity= 0.04f;
    private float jumpSpeed = -4.5f;
    private float fallSpeedAfterCollision=0.5f;
    private boolean inAir=false;
        
    public Player(float x, float y,int width,int height) {
        super(x, y,width,height);
        loadAnimations();
        initHitbox(x,y,(int)64,(int)128);
        
    }
    public void update(){
        updatePos();
        updateAnimationTick();
        setAnimation();
        
    }
    public void render(Graphics g, int lvlOffset){
        g.drawImage(animations[playerAction.ordinal()][animationIndex],(int)(hitBox.x-xDrawOffset)-lvlOffset,(int)(hitBox.y-yDrawOffset)+1,128*getSpriteScale(playerAction),128,null);
        //drawHitbox(g);
    }
    private void loadAnimations() {
        BufferedImage arturoPratt=LoadSave.getSpriteAtlas(LoadSave.PLAYERATLAS);
        animations = new BufferedImage[7][16];
        for(int j=0;j<animations.length;j++ ){
            if(j<5){
                for(int i =0; i<animations[j].length;i++){
                    animations[j][i]= arturoPratt.getSubimage(i*64,j*64,64,64);
                }
            }else{
                for(int i =0; i<2;i++){
                    animations[j][i]= arturoPratt.getSubimage(i*64*2,j*64,64*2,64);
                }
            }
    }}
    public void loadLvlData(int[][] lvlData){
        this.lvlData=lvlData;
        if(!isEntityOnFloor(hitBox,lvlData)){
            inAir=true;
        }
    }
    
   private void updateAnimationTick() {
        animationTick++;
        if (animationTick>=animationSpeed){
            animationTick=0;
            animationIndex++;
            if(animationIndex>=getSpriteAmount(playerAction)){
                animationIndex=0;
                swordAttacking=false;
                gunAttacking=false;
            }
        }
    }

    private void setAnimation() {
        int startAnim = playerAction.ordinal();
        if(moving){
            playerAction=Constants.PlayerAction.RUNNING;
        }
        else{
            playerAction=Constants.PlayerAction.IDLE;
        }if(inAir){
            if(airSpeed<0){
                playerAction=Constants.PlayerAction.JUMPING;
            }else{
               playerAction=Constants.PlayerAction.FALLING; 
            }
        }
        if(swordAttacking){
            playerAction=Constants.PlayerAction.SWORD_ATTACK;
        }else if(gunAttacking){
             playerAction=Constants.PlayerAction.GUN_ATTACK;
        }
        if(startAnim!=playerAction.ordinal()){
            resetAniTick();
            
        }
    }

    private void updatePos() {
        moving=false;
        if(jump){
            jump();
        }
//        if(!left && !right && !inAir){
//            return;
//        }
        if(!inAir){
            if((!left && !right) || (right && left)){
                return;
            }
        }
        float xSpeed=0;
        
        if(left){
            xSpeed -= playerSpeed;
        }if(right){
            xSpeed += playerSpeed;
        }
        if(!inAir){
            if(!isEntityOnFloor(hitBox,lvlData)){
                inAir = true; 
            }
        }
        if(inAir){
            if(canMoveHere(hitBox.x,hitBox.y+airSpeed,hitBox.width,hitBox.height,lvlData)){
                hitBox.y+=airSpeed;
                airSpeed +=gravity;
                updateXPos(xSpeed);
                
            }else{
                hitBox.y=GetEntityYPosCollision(hitBox,airSpeed);
                if(airSpeed>0){
                    resetInAir();
                }else{
                    airSpeed=fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        }else{
            updateXPos(xSpeed);
        }
        moving=true;
            
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void resetDirBooleans() {
        left=false;
        down=false;
        up=false;
        right=false;
    }
    public void setSwordAttacking(boolean attacking){
        this.swordAttacking=attacking;
    }
    public void setGunAttacking(boolean attacking){
        this.gunAttacking=attacking;
    }

    private void resetAniTick() {
        animationTick=0;
        animationIndex=0;
    }

    private void updateXPos(float xSpeed) {
        if(canMoveHere(hitBox.x+xSpeed,hitBox.y,hitBox.width,hitBox.height,lvlData)){
            hitBox.x +=xSpeed;
        }else{
            hitBox.x=getEntityXPosNextToWall(hitBox,xSpeed);
        }
    }

    private void resetInAir() {
        inAir= false;
        airSpeed=0;
    }

    private void jump() {
        if(inAir){
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }
    public void setJump(boolean jump){
        this.jump=jump;
    }
    
    
}
