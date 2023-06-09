
package superarturoprat;

import InputManager.KeyboardInput;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    public int x;
    public int y;
   public GamePanel(){
       addKeyListener(new KeyboardInput(this));
   } 
   public void moveY(int value){
       this.y+=value;
       repaint();
   }
   public void moveX(int value){
       this.x+=value;
       repaint();
   }
    @Override
   public void paintComponent(Graphics g){
       super.paintComponent(g);
       g.fillOval(x, y, 20, 20);
       g.setColor(Color.red);
   }
}
