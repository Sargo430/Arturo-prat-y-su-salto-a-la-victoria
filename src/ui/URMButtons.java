
package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utility.LoadSave;
import static utility.Constants.UI.UrmButtons.*;


public class URMButtons extends PauseButton {
    private BufferedImage[] imgs;
    protected int rowIndex,index;
    
    protected boolean mouseOver,mousePressed;
    public URMButtons(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex=rowIndex; 
        loadImgs();
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
    public void draw(Graphics g){
        g.drawImage(imgs[index],x,y,URM_SIZE,URM_SIZE,null);
    }

    private void loadImgs() {
        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.URM_BUTTONS);
        imgs = new BufferedImage[3];
        for(int i =0;i<imgs.length;i++){
            imgs[i]=temp.getSubimage(i*URM_DEFAULT_SIZE, rowIndex*URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
        }
    
    }
    public void resetBools(){
        mouseOver=false;
        mousePressed=false;
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
    
}
