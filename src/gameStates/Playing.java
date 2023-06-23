
package gameStates;

import static InputManager.KeyboardInput.*;
import audio.AudioPlayer;
import entities.EnemyManager;
import entities.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import levels.LevelManager;
import objects.ObjectManager;
import superarturoprat.GameManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utility.LoadSave;
import static utility.Constants.Enviroment.*;


public class Playing extends State implements StateMethods{
    private Player player;
    private LevelManager levelManager;
    private ObjectManager objectManager;
    private boolean paused=false;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private int xLvlOffset;
    private int leftBorder=(int)(0.2*GameManager.GAME_WIDTH);
    private int rightBorder=(int)(0.8*GameManager.GAME_WIDTH);
    private int maxLvlOffsetX;
    private BufferedImage[] background_img,secondLayer,thirdLayer;
    private int[] smallCloudsPos;
    private EnemyManager enemyManager;
    private Random rnd= new Random();
    private boolean gameOver=false;
    private boolean lvlCompleted = false;
    private boolean playerDying=false;
    private int winX;
    public Playing(GameManager game) {
        super(game);
        initClasses();
        
    }
    private void initClasses() {
        levelManager=new LevelManager(game);
        enemyManager=new EnemyManager(this);
        objectManager= new ObjectManager(this); 
        player= new Player(200,100,128,128,0,this);
        player.loadLvlData(levelManager.getCurrentLvl().getLvlData());
        player.setSpawn(levelManager.getCurrentLvl().getPlayerSpawn());
        pauseOverlay= new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
        
        getBackGroundImgs();
        smallCloudsPos= new int[8];
        for(int i = 0; i< smallCloudsPos.length;i++){
            smallCloudsPos[i]=(int)(90*GameManager.SCALE)+rnd.nextInt((int)(170*GameManager.SCALE));
            System.out.println(smallCloudsPos[i]);
        }
        calcLvlOffset();
        loadStartLevel();
        
        
    }
    public Player getPlayer(){
        return player;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    @Override
    public void update() {
        if(paused){
            pauseOverlay.update(); 
        }else if(lvlCompleted){
            levelCompletedOverlay.update();
        }else if(gameOver){
            gameOverOverlay.update();
        }else if(playerDying){
            player.update();
        }
        else{
            levelManager.update();
            objectManager.update(levelManager.getCurrentLvl().getLvlData(),player);
            player.update();
            if(player.getHitBox().x>winX){
                lvlCompleted=true;
            }
            enemyManager.update(levelManager.getCurrentLvl().getLvlData(),player);
            checkCloseToBorder();
        }
        
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(background_img[levelManager.getLvlIndex()],0,0,GameManager.GAME_WIDTH,GameManager.GAME_HEIGHT,null);
        drawClouds(g);
        levelManager.draw(g,xLvlOffset);
        player.render(g,xLvlOffset);
        enemyManager.draw(g,xLvlOffset);
        objectManager.draw(g,xLvlOffset);
        if(paused){
            g.setColor(new Color(0,0,0,150));
            g.fillRect(0,0,GameManager.GAME_WIDTH,GameManager.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }else if(gameOver){
            gameOverOverlay.draw(g);
        }else if(lvlCompleted){
            levelCompletedOverlay.draw(g);
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!gameOver){
            if(e.getButton()==MouseEvent.BUTTON1){
            player.setSwordAttacking(true);
            }else if(e.getButton()==MouseEvent.BUTTON3){
                gunAttack();
                
            }
            else if(lvlCompleted){
               levelCompletedOverlay.mousePressed(e);
               
           }
        }
        
    }
    private void gunAttack(){
        player.setGunAttacking(true);
                if(player.getCurrentAmmo()>0){
                    game.getAudioPlayer().playEffect(AudioPlayer.GUN_SHOT);
                    objectManager.shootBullets(player);
                    player.changeAmmo(-1); 
                }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver){
           if(paused){
            pauseOverlay.mousePressed(e);
            } 
           else if(lvlCompleted){
               levelCompletedOverlay.mousePressed(e);
           }
        }else{
            gameOverOverlay.mousePressed(e);
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver){
            if(paused){
                pauseOverlay.mouseReleased(e);
            }
            else if(lvlCompleted){
               levelCompletedOverlay.mouseReleased(e);
           }
        }else{
            gameOverOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver){
            if(paused){
                pauseOverlay.mouseMoved(e);
            }
            else if(lvlCompleted){
               levelCompletedOverlay.mouseMoved(e);
           }
        }else{
            gameOverOverlay.mouseMoved(e);
        }
    }
    public void mouseDragged(MouseEvent e){
        if(!gameOver){
           if(paused){
            pauseOverlay.mouseDragged(e);
            } 
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver){
            gameOverOverlay.keyPressed(e);
        }
        else{
            
            if(e.getKeyCode()==MOVE_LEFT){
                player.setLeft(true);
            }else if(e.getKeyCode()==MOVE_RIGHT){
                player.setRight(true);
            }
            else if(e.getKeyCode()==JUMP_KEY){
                player.setJump(true);
            }else if(e.getKeyCode()==PAUSE_KEY){
                paused=true;
            }else if(e.getKeyCode()==SWORD_ATTACK_KEY){
                player.setSwordAttacking(true);
            }else if(e.getKeyCode()==GUN_ATTACK_KEY){
                gunAttack();
            }
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver){
            if(e.getKeyCode()==MOVE_LEFT){
                player.setLeft(false);
            }else if(e.getKeyCode()==MOVE_RIGHT){
                player.setRight(false);
            }
            else if(e.getKeyCode()==JUMP_KEY){
                player.setJump(false);
            }
 
        }
        
    }
    public void unpauseGame(){
        paused=false;
    }

    private void checkCloseToBorder() {
        int playerX =(int)(player.getHitBox().x);
        int diff = playerX-xLvlOffset;
        if(diff > rightBorder){
            xLvlOffset += diff - rightBorder;
        }else if(diff < leftBorder){
            xLvlOffset += diff - leftBorder;
        }if(xLvlOffset > maxLvlOffsetX){
            xLvlOffset = maxLvlOffsetX;
        }else if(xLvlOffset <0){
            xLvlOffset=0;
    
        }
    }

    private void drawClouds(Graphics g) {
        for(int i=0;i<4;i++){
           g.drawImage(secondLayer[levelManager.getLvlIndex()],0+i* BIGCLOUD_WIDTH*2-(int)(xLvlOffset*0.2),(int)(348*GameManager.SCALE),BIGCLOUD_WIDTH*2,BIGCLOUD_HEIGHT*2,null);
        }
        for(int i=0; i< smallCloudsPos.length;i++){
            g.drawImage(thirdLayer[levelManager.getLvlIndex()],SMALLCLOUD_WIDTH*4*i-(int)(xLvlOffset*0.5),smallCloudsPos[i],SMALLCLOUD_WIDTH,SMALLCLOUD_HEIGHT,null);
        }
        
    }
    public void resetAll(){
        gameOver=false;
        paused=false;
        lvlCompleted=false;
        playerDying=false;
        player.resetAll();
        enemyManager.resetlAllEnemies();
        objectManager.resetAllObjects();
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox){
        enemyManager.checkEnemyHit(attackBox);
    }
    public void checkEnemyShoot(Rectangle2D.Float attackBox){
        enemyManager.checkEnemyShoot(attackBox);
    }
    public void setGameOver(boolean gameOver){
        this.gameOver=gameOver;
    }

    private void calcLvlOffset() {
        maxLvlOffsetX= levelManager.getCurrentLvl().getLvlOffset();
        winX= levelManager.getCurrentLvl().getLvlTilesWide()*64 -96;
        
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLvl());
        objectManager.loadObjects(levelManager.getCurrentLvl());
    }
    public void loadNextLvl(){
        resetAll();
        levelManager.loadNextLvl();
        calcLvlOffset();
        player.setSpawn(levelManager.getCurrentLvl().getPlayerSpawn());
    }
    public EnemyManager getEnemyManager(){
        return enemyManager;
    }
    public void setLvlMaxOffset(int lvlOffset){
        this.maxLvlOffsetX=lvlOffset;
    }
    public void setLvlCompleted(boolean lvlCompleted){
        this.lvlCompleted = lvlCompleted;
        if(lvlCompleted){
            game.getAudioPlayer().stopSong();
            game.getAudioPlayer().playEffect(AudioPlayer.LVL_COMPLETED);
        }
    }
    public ObjectManager getObjectManager(){
        return objectManager;
    }

    public void checkPotionTouched(Rectangle2D.Float hitBox) {
        objectManager.checkObjectTouched(hitBox);
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectManager.checkObjectHit(attackBox);
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void checkSpikesTouched(Player player) {
        objectManager.checkSpikesTouched(player);
    }

    public void setPlayerDying(boolean b) {
        this.playerDying=b;
    }

    private void getBackGroundImgs() {
        background_img =new BufferedImage[5];
        secondLayer= new BufferedImage[5];
        thirdLayer=new BufferedImage[5];
        background_img[0]=LoadSave.getSpriteAtlas(LoadSave.LEVEL01_BACKGROUND);
        
        background_img[1]=LoadSave.getSpriteAtlas(LoadSave.LEVEL02_BACKGROUND);
        secondLayer[1]=LoadSave.getSpriteAtlas(LoadSave.LEVEL02_SECOND_LAYER);
        thirdLayer[1]=LoadSave.getSpriteAtlas(LoadSave.LEVEL02_SMALLCLOUDS);
        
        background_img[2]=LoadSave.getSpriteAtlas(LoadSave.LEVEL03_BACKGROUND);
        secondLayer[2]=LoadSave.getSpriteAtlas(LoadSave.LEVEL03_SECOND_LAYER);
        thirdLayer[2]=LoadSave.getSpriteAtlas(LoadSave.LEVEL02_SMALLCLOUDS);
        
        background_img[3]=LoadSave.getSpriteAtlas(LoadSave.LEVEL03_BACKGROUND);
        secondLayer[3]=LoadSave.getSpriteAtlas(LoadSave.LEVEL02_BIGCLOUDS);
        thirdLayer[3]=LoadSave.getSpriteAtlas(LoadSave.LEVEL02_SMALLCLOUDS);
        
        background_img[4]=LoadSave.getSpriteAtlas(LoadSave.LEVEL04_BACKGROUND);
        secondLayer[4]=LoadSave.getSpriteAtlas(LoadSave.LEVEL02_BIGCLOUDS);
        thirdLayer[4]=LoadSave.getSpriteAtlas(LoadSave.LEVEL02_SMALLCLOUDS);
        
    }
    
}
