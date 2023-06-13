
package superarturoprat;

import entities.Player;
import java.awt.Graphics;


public class GameManager implements Runnable {
   private GamePanel gamePanel; 
   private GameFrame gameFrame;
   private Thread gameThread;
   private final int FPS_set=120;
   private final int UPS_set=200;
   private Player player;
   public GameManager(){
       initClasses();
       gamePanel = new GamePanel(this);
       gameFrame = new GameFrame(gamePanel);
       gamePanel.requestFocus();
       
      
       
       startGameLoop();
   }
   private void startGameLoop(){
       gameThread = new Thread(this);
       gameThread.start();
   }
   public void update(){
       player.update();
   }
   public void render(Graphics g){
       player.render(g);
   }

    @Override
    public void run() {
        double timePerUpdate= 1000000000.0/UPS_set;
        double timePerFrame= 1000000000.0/FPS_set;
        long previousTime = System.nanoTime();
        long currentTime =System.nanoTime();
        long lastCheck=System.currentTimeMillis();
        int updates=0;
        int frames=0;
        double deltaU=0;
        double deltaF=0;
        while(true){
            currentTime =System.nanoTime();
            deltaU+=(currentTime-previousTime)/timePerUpdate;
            deltaF+=(currentTime-previousTime)/timePerFrame;
            previousTime=currentTime;
            if(deltaU>=1){
                update();
                updates++;
                deltaU--;
            }
            if(deltaF>=1){
                gamePanel.repaint();
                frames++;
                deltaF--;
            }
            if(System.currentTimeMillis()-lastCheck>=1000){
                lastCheck=System.currentTimeMillis();
                System.out.println(frames+"|"+updates);
                updates=0;
                frames=0;
            }
            
        }
    }

    private void initClasses() {
        player= new Player(200,200);
    }
    public Player getPlayer(){
        return player;
    }
}
