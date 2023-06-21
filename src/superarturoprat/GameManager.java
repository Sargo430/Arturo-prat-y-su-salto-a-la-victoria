
package superarturoprat;

import audio.AudioPlayer;
import entities.Player;
import gameStates.GameOptions;
import gameStates.GameStates;
import static gameStates.GameStates.MENU;
import static gameStates.GameStates.PLAYING;
import static gameStates.GameStates.QUIT;
import gameStates.Menu;
import gameStates.Playing;
import java.awt.Graphics;
import levels.LevelManager;
import ui.AudioOptions;


public class GameManager implements Runnable {
   private GamePanel gamePanel; 
   private GameFrame gameFrame;
   private AudioOptions audioOptions;
   private Thread gameThread;
   private final int FPS_set=120;
   private final int UPS_set=200;
   public final static int TILE_ORIGINAL_SIZE=64;
   public final static int SCALE=1;
   public final static int TILES_WIDTH=20;
   public final static int TILES_HEIGHT=12;
   public final static int TILE_SIZE=TILE_ORIGINAL_SIZE*SCALE;
   public final static int GAME_WIDTH= TILE_SIZE*TILES_WIDTH;
   public final static int GAME_HEIGHT= TILE_SIZE*TILES_HEIGHT;
   private Playing playing;
   private Menu menu;
   private GameOptions gameOptions;
   private AudioPlayer audioPlayer;
   public GameManager(){
       initClasses();
       gamePanel = new GamePanel(this);
       gameFrame = new GameFrame(gamePanel);
       gamePanel.setFocusable(true);
       gamePanel.requestFocus();
       
      
       
       startGameLoop();
   }
   private void startGameLoop(){
       gameThread = new Thread(this);
       gameThread.start();
   }
   public void update(){
      
       switch(GameStates.state){
           case PLAYING:
               playing.update();
               break;
               
           case MENU:
               menu.update();
               break;
           case OPTIONS:
               gameOptions.update();
               break;
            case QUIT:
               System.exit(0);
               break;
           default:
               break;
       }
   }
   public void render(Graphics g){
       switch(GameStates.state){
           case PLAYING:
               playing.draw(g);
               break;
               
           case MENU:
               menu.draw(g);
               break;
           case OPTIONS:
               gameOptions.draw(g);
               break;
           default:
               break;
       }
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
        audioOptions = new AudioOptions(this);
        audioPlayer=new AudioPlayer();
        menu = new Menu(this);
        playing = new Playing(this);
        gameOptions = new GameOptions(this);
        
    }
   
    public void windowFocusLost() {
        if(GameStates.state == GameStates.PLAYING){
           playing.getPlayer().resetDirBooleans();
        }
    }
    public Menu getMenu(){
        return menu;
    }
    public Playing getPlaying(){
        return playing;
    }
    public AudioOptions getAudioOptions(){
        return audioOptions;
    }

    public GameOptions getGameOptions() {
        return gameOptions;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }
}
