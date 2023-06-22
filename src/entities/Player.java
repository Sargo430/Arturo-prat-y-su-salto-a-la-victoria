
package entities;

import audio.AudioPlayer;
import gameStates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import superarturoprat.GameManager;
import utility.Constants;
import static utility.Constants.ANIMATION_SPEED;
import static utility.Constants.GRAVITY;
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
    private Constants.PlayerAction playerAction= Constants.PlayerAction.IDLE;
    private boolean left,right,down,jump=false;
    private boolean moving,swordAttacking,gunAttacking = false; 
    private int[][] lvlData;
    private float xDrawOffset=18*2;
    private float yDrawOffset=6*2;
    private float jumpSpeed = -4.5f;
    private float fallSpeedAfterCollision=0.5f;
    private BufferedImage statusBarImg;
    
    private int statusBarWidth= (int)(238*GameManager.SCALE*1.25);
    private int statusBarHeight= (int)(63*GameManager.SCALE*1.25);
    private int statusBarX= (int)(10*GameManager.SCALE);
    private int statusBarY= (int)(10*GameManager.SCALE);
    
    private int healthBarWidth= (int)(172*GameManager.SCALE*1.25);
    private int healthBarHeight= (int)(6*GameManager.SCALE*1.25);
    private int healthBarX= (int)(34*GameManager.SCALE*1.25);
    private int healthBarY= (int)(14*GameManager.SCALE*1.25);
    private int tileY =0;
    private int maxBullets = 6;
    private int currentBullets = maxBullets;
    private int bulletOvalDiameter = (int)(8*GameManager.SCALE);
    private int xDrawBullet,yDrawBullet;

    private int healthWidth=healthBarWidth;
    private boolean attackChecked=false;
    private int flipX=0;
    private int flipW=1;
    private Playing playing;
    public Player(float x, float y,int width,int height,int entityType,Playing playing) {
        super(x, y,width,height, entityType);
        this.playing = playing;
        this.maxHealth=100;
        this.currentHealth=maxHealth;
        this.walkSpeed=3.0f*GameManager.SCALE;
        loadAnimations();
        initHitbox(x,y,(int)64,(int)116);
        initAttackbox();
        
    }
    public void setSpawn(Point spawn){
        this.x = spawn.x;
        this.y=spawn.y;
        hitBox.x=x;
        hitBox.y=y;
    }
    public void update(){
        updateHealthBar();
        if(currentHealth <=0){
            if(playerAction != Constants.PlayerAction.DEATH){
                playerAction= Constants.PlayerAction.DEATH;
                aniTick=0;
                aniIndex=0;
                playing.setPlayerDying(true);
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
            }else if(aniIndex==getSpriteAmount(Constants.PlayerAction.DEATH)-1 && aniTick >= ANIMATION_SPEED-1){
                playing.setGameOver(true);
                playing.getGame().getAudioPlayer().stopSong();
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
            }else{
                updateAnimationTick();
            }
            return;
        }
        
        updateAttackbox();
        updatePos();
        if(moving){
            checkPotionTouched();
            checkSpikesTouched();
            tileY =(int)(hitBox.y/GameManager.TILE_SIZE);
        }
        if(swordAttacking){
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();
        
    }
    public void render(Graphics g, int lvlOffset){
        g.drawImage(animations[playerAction.ordinal()][aniIndex]
                ,(int)(hitBox.x-xDrawOffset)-lvlOffset + flipX,
                (int)(hitBox.y-yDrawOffset)+1,
                128*getSpriteScale(playerAction)*flipW,
                128,null);
        //drawHitbox(g,lvlOffset);
        //drawAttackBox(g,lvlOffset);
        drawUI(g);
    }
    private void loadAnimations() {
        BufferedImage arturoPratt=LoadSave.getSpriteAtlas(LoadSave.PLAYERATLAS);
        animations = new BufferedImage[9][16];
        for(int j=0;j<animations.length;j++ ){
            if(j<7){
                for(int i =0; i<animations[j].length;i++){
                    animations[j][i]= arturoPratt.getSubimage(i*64,j*64,64,64);
                }
            }else{
                for(int i =0; i<2;i++){
                    animations[j][i]= arturoPratt.getSubimage(i*64*2,j*64,64*2,64);
                }
                
            }
    }statusBarImg= LoadSave.getSpriteAtlas(LoadSave.STATUS_BAR);
    }
    public void loadLvlData(int[][] lvlData){
        this.lvlData=lvlData;
        if(!isEntityOnFloor(hitBox,lvlData)){
            inAir=true;
        }
    }
    
   private void updateAnimationTick() {
        aniTick++;
        if (aniTick>=ANIMATION_SPEED){
            aniTick=0;
            aniIndex++;
            if(aniIndex>=getSpriteAmount(playerAction)){
                aniIndex=0;
                swordAttacking=false;
                gunAttacking=false;
                attackChecked=false;
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
            if(startAnim != Constants.PlayerAction.SWORD_ATTACK.ordinal()){
                aniIndex=0;
                aniTick=0;
            }
        }else if(gunAttacking){
             playerAction=Constants.PlayerAction.GUN_ATTACK;
             if(startAnim != Constants.PlayerAction.GUN_ATTACK.ordinal()){
                aniIndex=0;
                aniTick=0;
            }
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
            xSpeed -= walkSpeed;
            flipX=width;
            flipW=-1;
        }if(right){
            xSpeed += walkSpeed;
            flipX=0;
            flipW=1;            
        }
        if(!inAir){
            if(!isEntityOnFloor(hitBox,lvlData)){
                inAir = true; 
            }
        }
        if(inAir){
            if(canMoveHere(hitBox.x,hitBox.y+airSpeed,hitBox.width,hitBox.height,lvlData)){
                hitBox.y+=airSpeed;
                airSpeed +=GRAVITY;
                updateXPos(xSpeed);
                
            }else{
                hitBox.y=GetEntityYPosCollision(hitBox,airSpeed,0);
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
        right=false;
    }
    public void setSwordAttacking(boolean attacking){
        this.swordAttacking=attacking;
    }
    public void setGunAttacking(boolean attacking){
        this.gunAttacking=attacking;
    }

    private void resetAniTick() {
        aniTick=0;
        aniIndex=0;
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
        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
        inAir = true;
        airSpeed = jumpSpeed;
    }
    public void setJump(boolean jump){
        this.jump=jump;
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg,statusBarX,statusBarY,statusBarWidth,statusBarHeight,null);
        g.setColor(Color.red);
        g.fillRect(healthBarX + statusBarX,healthBarY + statusBarY,healthWidth,healthBarHeight);
        drawBulletBar(g);
    }

    private void updateHealthBar() {
        healthWidth=(int)((currentHealth/(float)maxHealth)*healthBarWidth);
    }
    private void drawBulletBar(Graphics g){
        g.setColor(new Color(196,163,0,255));
        if(currentBullets>0){
           for(int i =1; i<=currentBullets;i++){
            if(i==1){
                xDrawBullet=47;
                yDrawBullet=47;
            }else if(i==2){
                xDrawBullet=41;
                yDrawBullet=59;
            }
            else if(i==3){
                xDrawBullet=47;
                yDrawBullet=73;
            }else if(i==4){
                xDrawBullet=66;
                yDrawBullet=73;
            }
            else if(i==5){
                xDrawBullet=72;
                yDrawBullet=59;
            }
            else if(i==6){
                xDrawBullet=66;
                yDrawBullet=47;
            }
            g.fillOval(xDrawBullet, yDrawBullet, bulletOvalDiameter, bulletOvalDiameter);
        } 
        }
        
    }
    public void changeHealth(int value){
        currentHealth+=value;
        if(currentHealth <=0){
            currentHealth=0;
            //muelto
        }else if(currentHealth >= maxHealth){
            currentHealth = maxHealth;
        }
    }

    private void initAttackbox() {
        attackBox= new Rectangle2D.Float(x,y,(int)(58*GameManager.SCALE*2),(int)(58*GameManager.SCALE*2));
    }

    private void updateAttackbox() {
        if(right){
            attackBox.x=hitBox.x+hitBox.width+ (int)(15*GameManager.SCALE*2);
        }else if(left){
            attackBox.x=hitBox.x-hitBox.width - (int)(44*GameManager.SCALE*2);
        }
        attackBox.y=hitBox.y + (10 * GameManager.SCALE);
    }

    private void checkAttack() {
        if(attackChecked || aniIndex != 1){
            return;
        }
        else{
            attackChecked=true;
            playing.checkEnemyHit(attackBox);
            playing.checkObjectHit(attackBox);
            playing.getGame().getAudioPlayer().playAttackSound();
        }
    }

    public void resetAll() {
        resetDirBooleans();
        inAir=false;
        swordAttacking=false;
        gunAttacking=false;
        moving=false;
        playerAction=Constants.PlayerAction.IDLE;
        currentHealth=maxHealth;
        hitBox.x=x;
        hitBox.y=y;
        currentBullets=maxBullets;
        if(!isEntityOnFloor(hitBox,lvlData)){
            inAir=true;
        }
    }

    public void changeAmmo(int change) {
        currentBullets+=change;
        if(currentBullets>maxBullets){
            currentBullets=maxBullets;
        }
    }

    private void checkPotionTouched() {
        playing.checkPotionTouched(hitBox);
        
    }

    public void kill() {
        currentHealth=0;
    }

    private void checkSpikesTouched() {
        playing.checkSpikesTouched(this);
    }
    public int getTileY(){
        return tileY;
    }
    public int getDirection(){
        return flipW;
    }
    public int getCurrentAmmo(){
        return currentBullets;
    }

  
    
    
}
