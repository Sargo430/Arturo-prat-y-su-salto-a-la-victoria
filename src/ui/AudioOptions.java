
package ui;

import gameStates.GameStates;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import superarturoprat.GameManager;
import static utility.Constants.UI.PauseButtons.SOUND_SIZE;
import static utility.Constants.UI.VolumeButtons.SLIDER_WIDTH;
import static utility.Constants.UI.VolumeButtons.VOLUME_HEIGHT;


public class AudioOptions {
    private SoundButton musicButton, sfxButton;
    private VolumeButton volumeB;
    private GameManager game;
    public AudioOptions(GameManager game){
        this.game = game;
        createSoundButtons();
        createVolumeButton();
    }
    private void createVolumeButton() {
       int vX=(int)(532*GameManager.SCALE);
       int vY=(int)(350*GameManager.SCALE);
       volumeB = new VolumeButton(vX,vY,SLIDER_WIDTH,VOLUME_HEIGHT);
    }
    private void createSoundButtons() {
        int soundX=(int)(672*GameManager.SCALE);
        int musicY= (int)(212* GameManager.SCALE);
        int sfxY =(int)(260 * GameManager.SCALE);
        musicButton = new SoundButton(soundX,musicY,SOUND_SIZE,SOUND_SIZE);
        sfxButton = new SoundButton(soundX,sfxY,SOUND_SIZE,SOUND_SIZE);
    }
    public void update(){
      musicButton.update();
      sfxButton.update(); 
      volumeB.update();
    }
    public void draw(Graphics g){
        musicButton.draw(g);
        sfxButton.draw(g);
        volumeB.draw(g);
    }
    public void mousePressed(MouseEvent e) {
        if(isIn(e,musicButton)){
            musicButton.setMousePressed(true);
        }else if(isIn(e,sfxButton)){
            sfxButton.setMousePressed(true);
        }
        
        else if(isIn(e,volumeB)){
            volumeB.setMousePressed(true); 
        }
    }


    public void mouseReleased(MouseEvent e) {
        if(isIn(e,musicButton)){
            if(musicButton.isMousePressed()){
                musicButton.setMuted(!musicButton.isMuted());
                game.getAudioPlayer().toggleSongMute();
            }
            
        }else if(isIn(e,sfxButton)){
            if(sfxButton.isMousePressed()){
                sfxButton.setMuted(!sfxButton.isMuted());
                game.getAudioPlayer().toggleSFXMUTE();
            }    
        }
        
        musicButton.resetBools();
        sfxButton.resetBools();
        
        volumeB.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        
        volumeB.setMouseOver(false);
        if(isIn(e,musicButton)){
            musicButton.setMouseOver(true);
        }else if(isIn(e,sfxButton)){
            sfxButton.setMouseOver(true);
        }
       
        else if(isIn(e,volumeB)){
            volumeB.setMouseOver(true);
        }
    }
    public void mouseDragged(MouseEvent e){
        if(volumeB.isMousePressed()){
            float valueBefore = volumeB.getFloatValue();
            volumeB.changeX(e.getX());
            float valueAfter= volumeB.getFloatValue();
            if(valueBefore != valueAfter){
                game.getAudioPlayer().setVolume(valueAfter);
            }
        }
    }
    private boolean isIn(MouseEvent e, PauseButton btn){
        return btn.getBounds().contains(e.getX(),e.getY());
    }
    
}
