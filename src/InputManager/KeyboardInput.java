
package InputManager;

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
       switch(e.getKeyCode()){
           case KeyEvent.VK_W:
               System.out.println("a");
                gamePanel.getGame().getPlayer().setDirection(UP);
               break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setDirection(LEFT);
               break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDirection(DOWN);
               break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setDirection(RIGHT);
               break;
       } 
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setMoving(false);
               break;
       }
    }
    
}
