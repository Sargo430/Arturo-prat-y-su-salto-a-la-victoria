
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
    private SoundButton musicButton, sfxButton;
    private URMButtons menuB,replayB,unpauseB;
    private Playing playing;
    private VolumeButton volumeB;
    public PauseOverlay(Playing playing){
        this.playing=playing;
        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButton();
    }
    public void update(){
        musicButton.update();
        sfxButton.update();
        menuB.update();
        replayB.update();
        unpauseB.update();
        volumeB.update();
        
    }
    public void draw(Graphics g){
        g.drawImage(backgroundImg,bgX,bgY,bgW,bgH,null );
        //sound
        musicButton.draw(g);
        sfxButton.draw(g);
        ///urm
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);
        volumeB.draw(g);
    }
    public void mousePressed(MouseEvent e) {
        if(isIn(e,musicButton)){
            musicButton.setMousePressed(true);
        }else if(isIn(e,sfxButton)){
            sfxButton.setMousePressed(true);
        }
        else if(isIn(e,menuB)){
            menuB.setMousePressed(true); 
        }
        else if(isIn(e,replayB)){
            replayB.setMousePressed(true); 
        }
        else if(isIn(e,unpauseB)){
            unpauseB.setMousePressed(true); 
        }
        else if(isIn(e,volumeB)){
            volumeB.setMousePressed(true); 
        }
    }


    public void mouseReleased(MouseEvent e) {
        if(isIn(e,musicButton)){
            if(musicButton.isMousePressed()){
                musicButton.setMuted(!musicButton.isMuted());
            }
            
        }else if(isIn(e,sfxButton)){
            if(sfxButton.isMousePressed()){
                sfxButton.setMuted(!sfxButton.isMuted());
            }    
        }
        else if(isIn(e,menuB)){
            if(menuB.isMousePressed()){
                GameStates.state=GameStates.MENU;
                playing.unpauseGame();
            }
        }
        else if(isIn(e,replayB)){
            if(replayB.isMousePressed()){
                System.out.println("replay");
            }
        }
        else if(isIn(e,unpauseB)){
            if(unpauseB.isMousePressed()){
                playing.unpauseGame();
            }
        }
        musicButton.resetBools();
        sfxButton.resetBools();
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
        volumeB.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        volumeB.setMouseOver(false);
        if(isIn(e,musicButton)){
            musicButton.setMouseOver(true);
        }else if(isIn(e,sfxButton)){
            sfxButton.setMouseOver(true);
        }
        else if(isIn(e,menuB)){
            menuB.setMouseOver(true);
        }else if(isIn(e,replayB)){
            replayB.setMouseOver(true);
        }else if(isIn(e,unpauseB)){
            unpauseB.setMouseOver(true);
        }
        else if(isIn(e,volumeB)){
            volumeB.setMouseOver(true);
        }
    }
    public void mouseDragged(MouseEvent e){
        if(volumeB.isMousePressed()){
            volumeB.changeX(e.getX());
        }
    }

    private void loadBackground() {
        backgroundImg= LoadSave.getSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgW=backgroundImg.getWidth()*GameManager.SCALE;
        bgH=backgroundImg.getHeight()*GameManager.SCALE;
        bgX= GameManager.GAME_WIDTH/2 - bgW/2;
        bgY=100;
        
    }

    private void createSoundButtons() {
        int soundX=(int)(800*GameManager.SCALE);
        int musicY= (int)(212* GameManager.SCALE);
        int sfxY =(int)(260 * GameManager.SCALE);
        musicButton = new SoundButton(soundX,musicY,SOUND_SIZE,SOUND_SIZE);
        sfxButton = new SoundButton(soundX,sfxY,SOUND_SIZE,SOUND_SIZE);
    }
    private boolean isIn(MouseEvent e, PauseButton btn){
        return btn.getBounds().contains(e.getX(),e.getY());
    }

    private void createUrmButtons() {
        int menuX= (int)(675*GameManager.SCALE);
        int replayX=(int)(740*GameManager.SCALE);
        int unPauseX=(int)(805*GameManager.SCALE);
        int by=(int)(400*GameManager.SCALE);
        menuB = new URMButtons(menuX,by,URM_SIZE,URM_SIZE,2);
        replayB = new URMButtons(replayX,by,URM_SIZE,URM_SIZE,1);
        unpauseB = new URMButtons(unPauseX,by,URM_SIZE,URM_SIZE,0);
    }

    private void createVolumeButton() {
       int vX=(int)(660*GameManager.SCALE);
       int vY=(int)(350*GameManager.SCALE);
       volumeB = new VolumeButton(vX,vY,SLIDER_WIDTH,VOLUME_HEIGHT);
    }
    
}
