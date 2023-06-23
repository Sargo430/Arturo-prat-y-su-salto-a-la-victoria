
package ui;

import java.awt.event.KeyEvent;
import static InputManager.KeyboardInput.*;
import gameStates.GameOptions;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import superarturoprat.GameManager;
import static utility.Constants.UI.UrmButtons.URM_SIZE;
import utility.LoadSave;

public class ChangeKeyOverlay {
    private BufferedImage background,theBackground;
    private ArrayList<ChangeInputsButton> newKeyBtns;
    private URMButtons returnB;
    private boolean changingKey=false;
    private int nextKeyIndex;
    private GameOptions gameOptions;
    public ChangeKeyOverlay(GameOptions gameOptions){
        loadImages();
        createBtn();
        this.gameOptions=gameOptions;
    }
    public void changeKey(int newKey){
        switch(nextKeyIndex){
            case 0:
                MOVE_LEFT=newKey;
                break;
            case 1:
                MOVE_RIGHT=newKey;   
                break;
            case 2:
                JUMP_KEY=newKey;
                break;
            case 3:
                PAUSE_KEY=newKey;
                break;
            case 4:
                SWORD_ATTACK_KEY=newKey;
                break;
            case 5:
                GUN_ATTACK_KEY=newKey;
                break;
        }      
        
    }
    public void draw(Graphics g){
        g.drawImage(theBackground,0,0,GameManager.GAME_WIDTH,GameManager.GAME_HEIGHT,null);
        g.drawImage(background, GameManager.GAME_WIDTH/2-background.getWidth()/2, 100,background.getWidth(),background.getHeight(), null);
        drawKeys(g);
        for(ChangeInputsButton b: newKeyBtns){
            b.draw(g);
        }
        if(changingKey){
            g.setColor(new Color(0,0,0,150));
            g.fillRect(0,0,GameManager.GAME_WIDTH,GameManager.GAME_HEIGHT);
            g.setColor(Color.WHITE);
            g.drawString("Press a key",GameManager.GAME_WIDTH/2-30,GameManager.GAME_HEIGHT/2);
        }
        returnB.draw(g);
    }
    public void update(){
        for(ChangeInputsButton b: newKeyBtns){
            b.update();
            
        }
        returnB.update();
    }
    private void loadImages(){
        theBackground=LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        background=LoadSave.getSpriteAtlas(LoadSave.CHANGE_KEY_BACKGROUND);
        
    }private void drawKeys(Graphics g){
        g.setFont(new Font("TimeRoman",Font.PLAIN,22));
        g.drawString("Move left: " + KeyEvent.getKeyText(MOVE_LEFT), 465, 170);
        g.drawString("Move right: " + KeyEvent.getKeyText(MOVE_RIGHT), 465, 210);
        g.drawString("Jump: " + KeyEvent.getKeyText(JUMP_KEY), 465, 250);
        g.drawString("Pause: " + KeyEvent.getKeyText(PAUSE_KEY), 465, 290);
        g.drawString("Sword attack: " + KeyEvent.getKeyText(SWORD_ATTACK_KEY), 465, 330);
        g.drawString("Gun attack: " + KeyEvent.getKeyText(GUN_ATTACK_KEY), 465, 370);
        
    }

    private void createBtn() {
        newKeyBtns= new ArrayList<>();
        newKeyBtns.add(new ChangeInputsButton(760,140,42,42,1));
        newKeyBtns.add(new ChangeInputsButton(760,180,42,42,1));
        newKeyBtns.add(new ChangeInputsButton(760,220,42,42,1));
        newKeyBtns.add(new ChangeInputsButton(760,260,42,42,1));
        newKeyBtns.add(new ChangeInputsButton(760,300,42,42,1));
        newKeyBtns.add(new ChangeInputsButton(760,340,42,42,1));
        returnB= new URMButtons(465,475,URM_SIZE,URM_SIZE,1);
    }
    public void mouseMoved(MouseEvent e){
        returnB.setMouseOver(false);
        for(ChangeInputsButton b: newKeyBtns){
            b.setMouseOver(false);
            if(isIn(b,e)){
                b.setMouseOver(true);
            }
        }
        if(isIn(returnB,e)){
            returnB.setMouseOver(true);
        }
    }
    public void mouseReleased(MouseEvent e){
        //accion
        
        for(ChangeInputsButton b: newKeyBtns){
            if(isIn(b,e)){
                changingKey=true;
                nextKeyIndex=newKeyBtns.indexOf(b);
                b.resetBools();
            }
        }
        if(isIn(returnB,e)){
            gameOptions.setKeyChange(false);
        }
        returnB.resetBools();
    }
    public void mousePressed(MouseEvent e){
        for(ChangeInputsButton b: newKeyBtns){
            if(isIn(b,e)){
                b.setMousePressed(true);
            }
        }
        if(isIn(returnB,e)){
            returnB.setMousePressed(true);
        }
    }
    private boolean isIn(URMButtons b, MouseEvent e){
        return b.getBounds().contains(e.getX(),e.getY());
    }
    
    public void keyPressed(KeyEvent e) {
        if(changingKey){
            changeKey(e.getKeyCode());
            changingKey=false;
        }
    }
    
}
