
package gameStates;

import entities.Player;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import levels.LevelManager;
import superarturoprat.GameManager;


public class Playing extends State implements StateMethods{
    private Player player;
    private LevelManager levelManager;

    public Playing(GameManager game) {
        super(game);
        initClasses();
    }
    private void initClasses() {
        levelManager=new LevelManager(game);
        player= new Player(200,100,128,128);
        player.loadLvlData(levelManager.getCurrentLvl().getLvlData());
    }
    public Player getPlayer(){
        return player;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    @Override
    public void update() {
        levelManager.update();
        player.update();
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        player.render(g);
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
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
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
    
}
