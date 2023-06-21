
package superarturoprat;

import InputManager.KeyboardInput;
import InputManager.MouseInputs;
import utility.Constants.*;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import static superarturoprat.GameManager.GAME_HEIGHT;
import static superarturoprat.GameManager.GAME_WIDTH;


public class GamePanel extends JPanel {
    public int x;
    public int y;
    private GameManager game;
    private MouseInputs mouseInputs;
   public GamePanel(GameManager game){
       this.game=game;
       mouseInputs= new MouseInputs(this);
       setPanelSize();
       addKeyListener(new KeyboardInput(this));
       addMouseListener(mouseInputs);
       addMouseMotionListener(mouseInputs);
   } 
   public void setPanelSize(){
       Dimension size = new Dimension(GameManager.GAME_WIDTH,GameManager.GAME_HEIGHT);
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
