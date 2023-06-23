
package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import superarturoprat.GameManager;


public  abstract class Entity {
    protected float airSpeed = 0.0f;
    protected boolean inAir = false;
    protected int aniTick,aniIndex;
    protected float x,y;
    protected int width,height,entityType;
    protected int maxHealth;
    protected int currentHealth;
    protected float walkSpeed;
    protected Rectangle2D.Float attackBox;
    protected Rectangle2D.Float hitBox;
    public Entity(float x, float y,int width,int height,int entityType){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.entityType=entityType;
        
    }

    protected void initHitbox(float x, float y, int width, int height) {
        hitBox = new Rectangle2D.Float(x,y,(int)(width*GameManager.SCALE),(int)(height*GameManager.SCALE));
    }
//    protected void updateHitBox(){
//        hitBox.x=x;
//        hitBox.y=y;
//        
//    }
    
    protected void drawHitbox(Graphics g, int xLvlOffset){
        g.setColor(Color.PINK);
        g.drawRect((int)hitBox.x-xLvlOffset,(int)hitBox.y,(int)hitBox.width,(int)hitBox.height);
    }
    public Rectangle2D.Float getHitBox(){
        return hitBox; 
    }
    protected void drawAttackBox(Graphics g, int xLvlOffset){
        g.setColor(Color.red);
        g.drawRect((int)(attackBox.x-xLvlOffset),(int)(attackBox.y),(int)attackBox.width,(int)attackBox.height);
    }
    public int getAniIndex(){
        return aniIndex;
    }
}
