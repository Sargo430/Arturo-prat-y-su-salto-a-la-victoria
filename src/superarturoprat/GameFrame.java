
package superarturoprat;

import javax.swing.JFrame;
public class GameFrame extends JFrame{
    JFrame window;
    public GameFrame(GamePanel gamePanel){
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setSize(400,400);
        window.add(gamePanel);
        window.setVisible(true);
    }
    
}
