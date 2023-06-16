
package gameStates;

import entities.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import levels.LevelManager;
import superarturoprat.GameManager;
import ui.PauseOverlay;
import utility.LoadSave;


public class Playing extends State implements StateMethods{
    private Player player;
    private LevelManager levelManager;
    private boolean paused=false;
    private PauseOverlay pauseOverlay;
    private int xLvlOffset;
    private int leftBorder=(int)(0.2*GameManager.GAME_WIDTH);
    private int rightBorder=(int)(0.8*GameManager.GAME_WIDTH);
    private int lvlTilesWide=LoadSave.getLevelData()[0].length;
    private int maxTilesOffset= lvlTilesWide - GameManager.TILES_WIDTH;
    private int maxLvlOffsetX= maxTilesOffset * GameManager.TILE_SIZE;
    

    public Playing(GameManager game) {
        super(game);
        initClasses();
    }
    private void initClasses() {
        levelManager=new LevelManager(game);
        player= new Player(200,100,128,128);
        player.loadLvlData(levelManager.getCurrentLvl().getLvlData());
        pauseOverlay= new PauseOverlay(this);
    }
    public Player getPlayer(){
        return player;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    @Override
    public void update() {
        if(!paused){
            levelManager.update();
            player.update();
            checkCloseToBorder();
        }else{
         pauseOverlay.update();   
        }
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g,xLvlOffset);
        player.render(g,xLvlOffset);
        if(paused){
            g.setColor(new Color(0,0,0,150));
            g.fillRect(0,0,GameManager.GAME_WIDTH,GameManager.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton()==MouseEvent.BUTTON1){
            player.setSwordAttacking(true);
        }else if(e.getButton()==MouseEvent.BUTTON3){
            player.setGunAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(paused){
            pauseOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(paused){
            pauseOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(paused){
            pauseOverlay.mouseMoved(e);
        }
    }
    public void mouseDragged(MouseEvent e){
        if(paused){
            pauseOverlay.mouseDragged(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
         switch(e.getKeyCode()){
           case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
               player.setRight(true);
               break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
               break;
            case KeyEvent.VK_BACK_SPACE:
                GameStates.state=GameStates.MENU;
                break;
            case KeyEvent.VK_ESCAPE:
                paused=true;
                break;
       }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){

            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
               player.setRight(false);
               break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
               break;
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
    
}
