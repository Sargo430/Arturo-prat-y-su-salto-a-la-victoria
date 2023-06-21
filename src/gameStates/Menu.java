
package gameStates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Set;
import superarturoprat.GameManager;
import ui.MenuButton;
import utility.LoadSave;

public class Menu  extends State implements StateMethods{
    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg; 
    private BufferedImage menuBackground;
    private int menuX,menuY,menuWidth,menuHeigth;
    public Menu(GameManager game) {
        super(game);
        loadButtons();
        loadBackground();
    }

    @Override
    public void update() {
        for(MenuButton mb:buttons){
            mb.update();
        }
    }  

    @Override
    public void draw(Graphics g) {
        g.drawImage(menuBackground,0,0,GameManager.GAME_WIDTH,GameManager.GAME_HEIGHT,null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeigth, null);
        for(MenuButton mb:buttons){
            mb.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb:buttons){
            if(isIn(e,mb)){
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButton mb:buttons){
            if(isIn(e,mb)){
                if(mb.isMousePressed()){
                    mb.applyGameState();
                }
                if(mb.getState()==GameStates.PLAYING){
                    game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLvlIndex());
                }
            }
        }  
        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton mb:buttons){
            mb.setMouseOver(false);
        }for(MenuButton mb:buttons){
            if(isIn(e,mb)){
                mb.setMouseOver(true);
                break;
                
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_ENTER){
            GameStates.state = GameStates.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    private void loadButtons() {
        buttons[0]= new MenuButton(game.GAME_WIDTH/2,150*GameManager.SCALE,0,GameStates.PLAYING);
        buttons[1]= new MenuButton(game.GAME_WIDTH/2,220*GameManager.SCALE,1,GameStates.OPTIONS);
        buttons[2]= new MenuButton(game.GAME_WIDTH/2,290*GameManager.SCALE,2,GameStates.QUIT);
    }

    private void resetButtons() {
        for(MenuButton mb:buttons){
            mb.resetBools();
        }
    }

    private void loadBackground() {
     backgroundImg=LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND);   
     menuWidth = backgroundImg.getWidth()*GameManager.SCALE;
     menuHeigth = backgroundImg.getHeight();
     menuX=game.GAME_WIDTH/2 - menuWidth/2;
     menuY=45*GameManager.SCALE;
     menuBackground=LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
     
    }
    
}
