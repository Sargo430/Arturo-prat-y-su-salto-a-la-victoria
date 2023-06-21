
package ui;

import gameStates.GameStates;
import gameStates.Playing;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import superarturoprat.GameManager;
import static utility.Constants.UI.UrmButtons.URM_SIZE;
import utility.LoadSave;

public class LevelCompletedOverlay {
    private Playing playing;
    private URMButtons menu,next;
    private BufferedImage image;
    private int bgX,bgY,bgWidth,bgHeight;
    
    public LevelCompletedOverlay(Playing playing){
        this.playing=playing;
        initImage();
        initButtons();
    }

    private void initImage() {
        image =LoadSave.getSpriteAtlas(LoadSave.COMPLETED_IMG);
        bgWidth=(int)(image.getWidth()*GameManager.SCALE);
        bgHeight=(int)(image.getHeight()*GameManager.SCALE);
        bgX=(int)(GameManager.GAME_WIDTH/2 - bgWidth/2);
        bgY=(int)(GameManager.SCALE*75);
    }

    private void initButtons() {
        int menuX = (int) (555 * GameManager.SCALE);
        int nextX = (int) (670 * GameManager.SCALE);
        int y = (int) (195 * GameManager.SCALE);
        next = new URMButtons(nextX, y, URM_SIZE, URM_SIZE, 0);
        menu = new URMButtons(menuX, y, URM_SIZE, URM_SIZE, 2);
    }
    public void update(){
        menu.update();
        next.update();
    }
    public void draw(Graphics g){
        g.drawImage(image,bgX,bgY,bgWidth,bgHeight,null);
        next.draw(g);
        menu.draw(g);
    }
    public void mouseMoved(MouseEvent e){
        next.setMouseOver(false);
        menu.setMouseOver(false);
        if(isIn(menu,e)){
            menu.setMouseOver(true);
        }else if(isIn(next,e)){
            next.setMouseOver(true);
        }
    }
    public void mouseReleased(MouseEvent e){
        if(isIn(menu,e)){
            if(menu.isMousePressed()){
                playing.resetAll();
                GameStates.state=GameStates.MENU;
                playing.setGameState(GameStates.MENU);
            }
        }else if(isIn(next,e)){
            if(next.isMousePressed()){
                playing.loadNextLvl();
                playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLvlIndex());
            }
        }
        menu.resetBools();
        next.resetBools();
    }
    public void mousePressed(MouseEvent e){
        if(isIn(menu,e)){
            menu.setMousePressed(true);
        }else if(isIn(next,e)){
            next.setMousePressed(true);
        }
    }
    private boolean isIn(URMButtons b, MouseEvent e){
        return b.getBounds().contains(e.getX(),e.getY());
    }
}
