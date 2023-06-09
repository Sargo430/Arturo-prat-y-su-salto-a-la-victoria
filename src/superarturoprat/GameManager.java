
package superarturoprat;


public class GameManager {
   private GamePanel gamePanel; 
   private GameFrame gameFrame;
   public GameManager(){
       gamePanel = new GamePanel();
       gameFrame = new GameFrame(gamePanel);
       gamePanel.requestFocus();
       String a ="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";
   }
}
