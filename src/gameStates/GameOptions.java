
package gameStates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import superarturoprat.GameManager;
import ui.AudioOptions;
import ui.PauseButton;
import ui.URMButtons;
import static utility.Constants.UI.UrmButtons.URM_SIZE;
import utility.LoadSave;

public class GameOptions extends State implements StateMethods{
    private AudioOptions audioOptions;
    private BufferedImage backgroundImg,optionsBackground;
    private int bgX,bgY,bgW,bgH;
    private URMButtons menuB;
    public GameOptions(GameManager game) {
        super(game);
        audioOptions = game.getAudioOptions();
        loadImgs();
        loadButton();
        
    }

    @Override
    public void update() {
        menuB.update();
        audioOptions.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, GameManager.GAME_WIDTH, GameManager.GAME_HEIGHT, null);
        g.drawImage(optionsBackground, bgX, bgY, bgW, bgH, null);
        audioOptions.draw(g);
        menuB.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(isIn(e,menuB)){
            menuB.setMousePressed(true);
        }else{
            audioOptions.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isIn(e,menuB)){
            if(menuB.isMousePressed()){
                GameStates.state=GameStates.MENU;
            }
        }else{
            audioOptions.mouseReleased(e);
        }
        menuB.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        if(isIn(e,menuB)){
            menuB.setMouseOver(true);
        }else{
            audioOptions.mouseMoved(e);
        }
    }
    public void mouseDragged(MouseEvent e){
        audioOptions.mouseDragged(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    private void loadImgs() {
        backgroundImg=LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        optionsBackground=LoadSave.getSpriteAtlas(LoadSave.OPTIONS_BACKGROUND);
        bgW = (int) (optionsBackground.getWidth() * GameManager.SCALE);
        bgH = (int) (optionsBackground.getHeight() * GameManager.SCALE);
        bgX = GameManager.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (100 * GameManager.SCALE);
    }

    private void loadButton() {
        int menuX=(int)(612*GameManager.SCALE);
        int menuY=(int)(GameManager.SCALE*400);
        menuB = new URMButtons(menuX,menuY,URM_SIZE,URM_SIZE,2);
    }
    private boolean isIn(MouseEvent e, PauseButton btn){
        return btn.getBounds().contains(e.getX(),e.getY());
    }
    
}
