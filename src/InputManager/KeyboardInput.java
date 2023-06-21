
package InputManager;

import gameStates.GameStates;
import static gameStates.GameStates.MENU;
import static gameStates.GameStates.OPTIONS;
import static gameStates.GameStates.PLAYING;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import superarturoprat.GamePanel;
import static utility.Constants.Directions.*;


public class KeyboardInput implements KeyListener{
    GamePanel gamePanel;
    public KeyboardInput(GamePanel gamePanel){
        this.gamePanel=gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
      switch(GameStates.state){
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getGameOptions().keyPressed(e);
                break;
        } 
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(GameStates.state){
            case MENU:
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            
        }
    }
    
}
