
package ui;

import gameStates.GameStates;
import gameStates.Playing;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import superarturoprat.GameManager;
import static utility.Constants.UI.PauseButtons.SOUND_SIZE;
import static utility.Constants.UI.UrmButtons.URM_SIZE;
import static utility.Constants.UI.VolumeButtons.*;
import utility.LoadSave;


public class PauseOverlay {
    private BufferedImage backgroundImg;
    private int bgX,bgY,bgW,bgH;
    
    private URMButtons menuB,replayB,unpauseB;
    private Playing playing;
    private AudioOptions audioOptions;
    public PauseOverlay(Playing playing){
        this.playing=playing;
        loadBackground();
        audioOptions=playing.getGame().getAudioOptions();
        createUrmButtons();
        
    }
    public void update(){
        
        menuB.update();
        replayB.update();
        unpauseB.update();
        audioOptions.update();
        
        
    }
    public void draw(Graphics g){
        g.drawImage(backgroundImg,bgX,bgY,bgW,bgH,null );
        //sound
        
        ///urm
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);
        audioOptions.draw(g);
        
    }
    public void mousePressed(MouseEvent e) {
        if(isIn(e,menuB)){
            menuB.setMousePressed(true); 
        }
        else if(isIn(e,replayB)){
            replayB.setMousePressed(true); 
        }
        else if(isIn(e,unpauseB)){
            unpauseB.setMousePressed(true); 
        }else{
            audioOptions.mousePressed(e);
        }
       
    }


    public void mouseReleased(MouseEvent e) {
        
        if(isIn(e,menuB)){
            if(menuB.isMousePressed()){
                GameStates.state=GameStates.MENU;
                playing.setGameState(GameStates.MENU);
                playing.unpauseGame();
            }
        }
        else if(isIn(e,replayB)){
            if(replayB.isMousePressed()){
                playing.resetAll();
                playing.unpauseGame();
            }
        }
        else if(isIn(e,unpauseB)){
            if(unpauseB.isMousePressed()){
                playing.unpauseGame();
            }
        }else{
            audioOptions.mouseReleased(e);
        }
        
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
        
    }

    public void mouseMoved(MouseEvent e) {
        
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        
        
        if(isIn(e,menuB)){
            menuB.setMouseOver(true);
        }else if(isIn(e,replayB)){
            replayB.setMouseOver(true);
        }else if(isIn(e,unpauseB)){
            unpauseB.setMouseOver(true);
        }else{
            audioOptions.mouseMoved(e);
        }
        
    }
    public void mouseDragged(MouseEvent e){
        audioOptions.mouseDragged(e);
    }

    private void loadBackground() {
        backgroundImg= LoadSave.getSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgW=backgroundImg.getWidth()*GameManager.SCALE;
        bgH=backgroundImg.getHeight()*GameManager.SCALE;
        bgX= GameManager.GAME_WIDTH/2 - bgW/2;
        bgY=100;
        
    }

    
    private boolean isIn(MouseEvent e, PauseButton btn){
        return btn.getBounds().contains(e.getX(),e.getY());
    }

    private void createUrmButtons() {
        int menuX= (int)(547*GameManager.SCALE);
        int replayX=(int)(612*GameManager.SCALE);
        int unPauseX=(int)(677*GameManager.SCALE);
        int by=(int)(400*GameManager.SCALE);
        menuB = new URMButtons(menuX,by,URM_SIZE,URM_SIZE,2);
        replayB = new URMButtons(replayX,by,URM_SIZE,URM_SIZE,1);
        unpauseB = new URMButtons(unPauseX,by,URM_SIZE,URM_SIZE,0);
    }

    
    
}
