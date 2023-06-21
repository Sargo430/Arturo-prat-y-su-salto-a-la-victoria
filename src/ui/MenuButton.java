
package ui;

import gameStates.GameStates;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import utility.LoadSave;

import static utility.Constants.UI.Buttons.*;

public class MenuButton {
    private int xPos,yPos,rowIndex,index;
    private GameStates state;
    private BufferedImage[] images;
    private int xOffsetCenter = b_width/2;
    private boolean mouseOver,mousePressed;
    private Rectangle bounds;
    public MenuButton(int xPos, int yPos, int rowIndex, GameStates state){
        this.xPos=xPos;
        this.yPos=yPos;
        this.rowIndex=rowIndex;
        this.state=state;
        loadImages();
        initBounds();
    }

    private void loadImages() {
        images= new BufferedImage[3];
        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.MENU_BUTTONS);
        for(int i = 0;i<images.length;i++){
            images[i]=temp.getSubimage(i*b_Width_default,rowIndex* b_heigth_default,b_Width_default,b_heigth_default);
        }
    }
    public void draw(Graphics g){
        g.drawImage(images[index],xPos-xOffsetCenter,yPos,b_width,b_heigth,null);
    }
    public void update(){
        index=0;
        if(mouseOver){
            index=1;
        }
        if(mousePressed){
            index=2;
        }
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    private void initBounds() {
        bounds = new Rectangle(xPos-xOffsetCenter,yPos,b_width,b_heigth);
    }
    public void applyGameState(){
        GameStates.state=state;
        
    }
    public void resetBools(){
        mouseOver=false;
        mousePressed=false;
        
    }
    public Rectangle getBounds(){
        return bounds;
    }

    public GameStates getState() {
        return state;
    }
    
    
}
