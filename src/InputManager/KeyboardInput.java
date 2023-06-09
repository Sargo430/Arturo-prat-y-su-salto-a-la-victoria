
package InputManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import superarturoprat.GamePanel;


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
                gamePanel.moveY(-5);
               break;
            case KeyEvent.VK_A:
                gamePanel.moveX(-5);
               break;
            case KeyEvent.VK_S:
                gamePanel.moveY(5);
               break;
            case KeyEvent.VK_D:
                gamePanel.moveX(5);
               break;
       } 
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
