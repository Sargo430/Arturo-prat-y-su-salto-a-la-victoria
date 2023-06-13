
package superarturoprat;

import InputManager.KeyboardInput;
import utility.Constants.*;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;


public class GamePanel extends JPanel {
    public int x;
    public int y;
    private GameManager game;
   public GamePanel(GameManager game){
       this.game=game;
       setPanelSize();
       addKeyListener(new KeyboardInput(this));
   } 
   public void setPanelSize(){
       Dimension size = new Dimension(1280,768);
       setMinimumSize(size);
       setPreferredSize(size);
       setMaximumSize(size);
   }
    @Override
   public void paintComponent(Graphics g){
       super.paintComponent(g);
       game.render(g);
      
   }

    public void updateGame() {
        
    }
    public GameManager getGame(){
        return game;
    }
}
