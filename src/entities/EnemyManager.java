
package entities;

import gameStates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import levels.Level;
import static utility.Constants.EnemyConstants.*;
import utility.LoadSave;


public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] peruanoArr;
    private ArrayList<Peruano> peruanos = new ArrayList<>();
    public EnemyManager(Playing playing){
        this.playing=playing;
        loadEnemyImgs();
    }
    public void update(int[][]lvlData,Player player){
        boolean isAnyActive = false;
        
        for(Peruano p:peruanos){
            if(p.isActive()){
               p.update(lvlData,player); 
               isAnyActive=true;
            }
            
        }
        if(!isAnyActive){
            playing.setLvlCompleted(true);
        }
    }
    public void draw(Graphics g, int xLvlOffset){
        drawPeruanos(g, xLvlOffset);
        
    }

    private void loadEnemyImgs() {
        peruanoArr= new BufferedImage[5][16];
        BufferedImage temp= LoadSave.getSpriteAtlas(LoadSave.PERUANO_SPRITE);
        for(int j =0; j<peruanoArr.length;j++){
            if(j<4){
                for(int i=0;i<peruanoArr[j].length;i++){
                    peruanoArr[j][i]=temp.getSubimage(i*PERUANO_WIDTH_DEFAULT, j*PERUANO_HEIGHT_DEFAULT, PERUANO_WIDTH_DEFAULT, PERUANO_HEIGHT_DEFAULT);
                }
            }else{
                for(int i =0;i<2;i++){
                    peruanoArr[j][i]=temp.getSubimage(i*PERUANO_WIDTH_DEFAULT*2, j*PERUANO_HEIGHT_DEFAULT, PERUANO_WIDTH_DEFAULT*2, PERUANO_HEIGHT_DEFAULT);
                }
            }
        }
    }

    private void drawPeruanos(Graphics g,int xLvlOffset) {
        for(Peruano p:peruanos){
            if(p.isActive()){
                g.drawImage(peruanoArr[p.getEnemyState()][p.getAniIndex()],(int)(p.getHitBox().x-xLvlOffset-p.flipW()*PERUANO_DRAWOFFSET_X)+ p.flipX(),
                    (int)(p.getHitBox().y)-PERUANO_DRAWOFFSET_Y+1,PERUANO_WIDTH*2*getSpriteScale(0,p.getEnemyState())*p.flipW(),PERUANO_HEIGHT*2,null);
                drawHitbox(g,xLvlOffset,p);
                p.drawAttackBox(g,xLvlOffset);
            }
            
        }
    }

    public void loadEnemies(Level lvl) {
        peruanos=lvl.getPeruano();
    }

    private void drawHitbox(Graphics g, int xLvlOffset,Peruano p){
        g.setColor(Color.PINK);
        g.drawRect((int)p.hitBox.x-xLvlOffset,(int)p.hitBox.y,(int)p.hitBox.width,(int)p.hitBox.height);
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for(Peruano p:peruanos){
            if(p.isActive()){
                if(attackBox.intersects(p.getHitBox())){
                p.hurt(10);
                return;
            }
            }
            
        }
    }

    public void resetlAllEnemies() {
        for(Peruano p:peruanos){
            p.resetEnemy();
        }
            
    }
    
}
