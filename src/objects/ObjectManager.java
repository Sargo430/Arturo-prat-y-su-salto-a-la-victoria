
package objects;

import entities.Player;
import gameStates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import levels.Level;
import superarturoprat.GameManager;
import static utility.Constants.ObjectConstants.BARREL;
import static utility.Constants.ObjectConstants.BLUE_POTION_VALUE;
import static utility.Constants.ObjectConstants.*;
import static utility.Constants.Projectiles.CANNON_BALL_HEIGHT;
import static utility.Constants.Projectiles.CANNON_BALL_WIDTH;
import static utility.Constants.Projectiles.SPEED;
import static utility.HelpMethods.canSeePlayer;
import static utility.HelpMethods.isProjectileHittingLevel;

import utility.LoadSave;


public class ObjectManager {
    private Playing playing;
    private BufferedImage[][] potionImgs,containerImgs;
    private BufferedImage[] cannonImgs;
    private BufferedImage spikeImg,cannonBallImg,bulletImg;
    private ArrayList<Potions> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;
    private ArrayList<Cannon> cannons;
    private ArrayList<CannonBall> cannonBalls= new ArrayList<>();
    private ArrayList<Bullet> bullets= new ArrayList<>();
    
    public ObjectManager(Playing playing){
       this.playing=playing;
       loadImgs();
       potions = new ArrayList<>();
       containers = new ArrayList<>();
       
   } 
    public void checkObjectTouched(Rectangle2D.Float hitbox){
        for(Potions p:potions){
            if(p.isActive()){
                if(hitbox.intersects(p.getHitbox())){
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
            }
        }
    }
    public void applyEffectToPlayer(Potions p){
        if(p.getObjectType()==RED_POTION){
            playing.getPlayer().changeHealth(RED_POTION_VALUE);
        }else{
            playing.getPlayer().changeAmmo(1);
        }
    }
    public void checkObjectHit(Rectangle2D.Float attackBox){
        for(GameContainer c:containers){
            if(c.isActive() && !c.doAnimation){
                if(c.getHitbox().intersects(attackBox)){
                    c.setAnimation(true);
                    int type = 0;
                    if(c.getObjectType()==BARREL){
                        type=1;
                        potions.add(new Potions((int)(c.getHitbox().x+c.getHitbox().width*1.5),
                            (int)(c.getHitbox().y +c.getHitbox().height+10),type));
                        return;
                    }else{
                      potions.add(new Potions((int)(c.getHitbox().x+c.getHitbox().width*0.75),
                            (int)(c.getHitbox().y +c.getHitbox().height),type));  
                      return;
                    }
                }
            }
        }
    }

    private void loadImgs() {
        BufferedImage potionSprite = LoadSave.getSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImgs = new BufferedImage[2][7];
        for(int j =0;j<potionImgs.length;j++){
            for(int i =0;i<potionImgs[j].length;i++){
                potionImgs[j][i]= potionSprite.getSubimage(i*12, 16*j, 12, 16);
            }
        }
        BufferedImage containerSprite = LoadSave.getSpriteAtlas(LoadSave.OBJECTS_ATLAS);
        containerImgs = new BufferedImage[2][8];
        for(int j =0;j<containerImgs.length;j++){
            for(int i =0;i<containerImgs[j].length;i++){
                containerImgs[j][i]= containerSprite.getSubimage(i*40, 30*j, 40, 30);
            }
        }
        spikeImg=LoadSave.getSpriteAtlas(LoadSave.TRAP_ATLAS);
        cannonImgs = new BufferedImage[7];
        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.CANNON_ATLAS);
        for(int i = 0;i<cannonImgs.length;i++){
            cannonImgs[i]=temp.getSubimage(i*40, 0, 40, 26);
        }
        cannonBallImg=LoadSave.getSpriteAtlas(LoadSave.CANNON_BALL);
        bulletImg=LoadSave.getSpriteAtlas(LoadSave.BULLET);
    }
    public void update(int[][] lvlData,Player player){
        for(Potions p:potions){
             if(p.isActive()){
                 p.update();
             }
        }
        for(GameContainer c:containers){
            if(c.isActive()){
                c.update();
            }
        }
        for(Spike s:spikes){
            s.updateCdTick();
        }
        updateCannons(lvlData,player);
        updateProjectiles(lvlData,player);
    }
    public void draw(Graphics g,int xLvlOffset){
        drawPotions(g,xLvlOffset);
        drawContainers(g,xLvlOffset);
        drawTraps(g,xLvlOffset);
        drawCannons(g,xLvlOffset);
        drawCannonBalls(g,xLvlOffset);
        drawBullets(g,xLvlOffset);
    }
    public void checkSpikesTouched(Player player){
        for(Spike s:spikes){
            if(s.getHitbox().intersects(player.getHitBox())&&s.cooldown(100)){
                player.changeHealth(-20);
                s.resetCdTick();
            }
        }
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        for(Potions p:potions){
             if(p.isActive()){
                 int type = 0;
                 if(p.getObjectType()== RED_POTION){
                     type=1;
                 }
                 g.drawImage(potionImgs[type][p.getAniIndex()],(int)(p.getHitbox().x-p.getxDrawOffset()-xLvlOffset)
                            ,(int)(p.getHitbox().y-p.getyDrawOffset())
                            ,POTION_WIDTH*2
                            ,POTION_HEIGHT*2,null);
             }
        }
        
    }

    private void drawContainers(Graphics g, int xLvlOffset) {
        for(GameContainer c:containers){
            if(c.isActive()){
                int value = 0;
                if(c.getObjectType()== BARREL){
                    value =1;
                }
                g.drawImage(containerImgs[value][c.getAniIndex()],(int)(c.getHitbox().x-c.getxDrawOffset()-xLvlOffset)
                            ,(int)(c.getHitbox().y-c.getyDrawOffset())
                            ,CONTAINER_WIDTH*3
                            ,CONTAINER_HEIGHT*3,null);
            }
        }
    }

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        spikes = newLevel.getSpikes();
        cannons = newLevel.getCannons();
        cannonBalls.clear();
        bullets.clear();
    }
    public void drawBullets(Graphics g, int xLvlOffset){
        for(Bullet b:bullets){
            if(b.isActive()){
                int xDrawOffset=-15;
                if(b.getDirection()==-1){
                    xDrawOffset=118;
                }
              g.drawImage(bulletImg, (int)(b.getHitbox().x-xLvlOffset-xDrawOffset), (int)(b.getHitbox().y), 12*b.getDirection(), 6, null);  
            }
            
        }
    }
    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLvl());
        for(Potions p:potions){
            p.reset();
        }
        for(GameContainer c:containers){
            c.reset();
        }
        for(Cannon c:cannons){
            c.reset();
        }
        
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for(Spike s:spikes){
            g.drawImage(spikeImg,(int)(s.getHitbox().x-xLvlOffset), (int)(s.getHitbox().y-s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }
    }

    private void updateCannons(int[][] lvlData,Player player) {
        for(Cannon c:cannons){
            if(!c.doAnimation){
                if(c.getTileY()-1==player.getTileY()){
                    if(isPlayerInRange(c,player)){
                        if(isPlayerInFront(c,player)){
                            if(canSeePlayer(lvlData,player.getHitBox(),c.getHitbox(),c.getTileY())){
                                if(c.cooldown(500)){
                                    c.setAnimation(true);
                                    c.resetCdTick();
                                }  
                            }
                        }
                    }
                }
            }
            c.update();
            c.updateCdTick();
            if(c.getAniIndex()==4 &&c.getAniTick()==0){
                shootCannon(c);
            }
        }
    }

    private void drawCannons(Graphics g, int xLvlOffset) {
        for(Cannon c:cannons){
            int x=(int)(c.getHitbox().x - xLvlOffset);
            int width = CANNON_WIDTH;
            if(c.getObjectType()==CANNON_RIGHT){
                x += width;
                width*=-1;
            }
            g.drawImage(cannonImgs[c.getAniIndex()],
                    x, 
                    (int)(c.getHitbox().y-c.getyDrawOffset()), 
                    width, CANNON_HEIGHT, null);
        }
    }

    private boolean isPlayerInRange(Cannon c, Player player) {
        int absValue = (int)(Math.abs(player.getHitBox().x-c.getHitbox().x));
        return absValue <=GameManager.TILE_SIZE*10;
    }

    private boolean isPlayerInFront(Cannon c, Player player) {
        if(c.getObjectType()==CANNON_LEFT){
            if(c.getHitbox().x > player.getHitBox().x){
                return true;
            }
        }else if(c.getObjectType()==CANNON_RIGHT){
            if(c.getHitbox().x < player.getHitBox().x){
                return true;
            }
        }
        return false;
    }

    private void shootCannon(Cannon c) {
        
        int dir =1;
        if(c.getObjectType()==CANNON_LEFT){
            dir=-1;
        }
        cannonBalls.add(new CannonBall((int)c.getHitbox().x,(int)c.getHitbox().y,dir,SPEED*2));
    }
    public void shootBullets(Player player){
        int bulletX = (int)(player.getHitBox().x +35);
        int bulletY=(int)(player.getHitBox().y +10);
        int direction=player.getDirection();
        bullets.add(new Bullet(bulletX,bulletY,direction,SPEED*4));
    }

    private void updateProjectiles(int[][] lvlData, Player player) {
        for(CannonBall c:cannonBalls){
            if(c.isActive()){
                c.updatePos();
                if(c.getHitbox().intersects(player.getHitBox())){
                    player.changeHealth(-30);
                    c.setActive(false);
                }else if (isProjectileHittingLevel(c,lvlData)){
                    c.setActive(false);
                }
            }
        }
        for(Bullet b:bullets){
            if(b.isActive()){
                b.updatePos();
                playing.checkEnemyShoot(b.getHitbox());
                if (isProjectileHittingLevel(b,lvlData)){
                    b.setActive(false);
                }
            }
        }
    }

    private void drawCannonBalls(Graphics g, int xLvlOffset) {
        for(CannonBall c:cannonBalls){
            if(c.isActive()){
                g.drawImage(cannonBallImg, (int)(c.getHitbox().x-xLvlOffset),
                        (int)c.getHitbox().y, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);
            }
        }
    }
    

    
}
