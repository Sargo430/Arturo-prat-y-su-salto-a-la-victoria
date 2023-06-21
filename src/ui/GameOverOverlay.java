
package ui;

import gameStates.GameStates;
import gameStates.Playing;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import superarturoprat.GameManager;
import static utility.Constants.UI.UrmButtons.URM_SIZE;
import utility.LoadSave;

/**
 *
 * @author Sebastian
 */
public class GameOverOverlay {
    private Playing playing;
    private BufferedImage img;
    private int imgX,imgY,imgW,imgH;
    private URMButtons menu,play;
    public GameOverOverlay(Playing playing){
        this.playing=playing;
        loadImg();
        createButtons();
    }
    public void draw(Graphics g){
        g.setColor(new Color(0,0,0,100));
        g.fillRect(0,0,GameManager.GAME_WIDTH,GameManager.GAME_HEIGHT);
        g.drawImage(img, imgX, imgY, imgH, imgH, null);
        menu.draw(g);
        play.draw(g);
        
        
        
//        g.setColor(Color.white);
//        g.drawString("Game Over", GameManager.GAME_WIDTH/2,150);
//        g.drawString("Press esc to enter menu", GameManager.GAME_WIDTH/2,300);
    }
    public void keyPressed(KeyEvent e){
       
    }

    private void loadImg() {
        img = LoadSave.getSpriteAtlas(LoadSave.DEATH_SCREEN);
        imgW=(int)(img.getWidth()*GameManager.SCALE);
        imgH=(int)(img.getHeight()*GameManager.SCALE);
        imgY=(int)(100*GameManager.SCALE);
        imgX=(int)(GameManager.GAME_WIDTH/2-imgW/2);
    }

    private void createButtons() {
        int menuX = (int) (555 * GameManager.SCALE);
        int playX = (int) (660* GameManager.SCALE);
        int y = (int) (195 * GameManager.SCALE);
        play = new URMButtons(playX, y, URM_SIZE, URM_SIZE, 0);
        menu = new URMButtons(menuX, y, URM_SIZE, URM_SIZE, 2);
    }
    public void mouseMoved(MouseEvent e){
        play.setMouseOver(false);
        menu.setMouseOver(false);
        if(isIn(menu,e)){
            menu.setMouseOver(true);
        }else if(isIn(play,e)){
            play.setMouseOver(true);
        }
    }
    public void mouseReleased(MouseEvent e){
        if(isIn(menu,e)){
            if(menu.isMousePressed()){
                playing.resetAll();
                GameStates.state=GameStates.MENU;
                playing.setGameState(GameStates.MENU);
            }
        }else if(isIn(play,e)){
            if(play.isMousePressed()){
                playing.resetAll();
                playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLvlIndex());
            }
        }
        menu.resetBools();
        play.resetBools();
    }
    public void mousePressed(MouseEvent e){
        if(isIn(menu,e)){
            menu.setMousePressed(true);
        }else if(isIn(play,e)){
            play.setMousePressed(true);
        }
    }
    private boolean isIn(URMButtons b, MouseEvent e){
        return b.getBounds().contains(e.getX(),e.getY());
    }
    public void update(){
        menu.update();
        play.update();
    }
}

