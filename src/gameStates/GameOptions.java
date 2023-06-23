
package gameStates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import superarturoprat.GameManager;
import ui.AudioOptions;
import ui.ChangeInputsButton;
import ui.ChangeKeyOverlay;
import ui.PauseButton;
import ui.URMButtons;
import static utility.Constants.UI.UrmButtons.URM_SIZE;
import utility.LoadSave;

public class GameOptions extends State implements StateMethods{
    private AudioOptions audioOptions;
    private BufferedImage backgroundImg,optionsBackground;
    private int bgX,bgY,bgW,bgH;
    private URMButtons menuB;
    private boolean isKeyChangeActive=false;
    private ChangeInputsButton changeKeyButton;
    private ChangeKeyOverlay changeKey = new ChangeKeyOverlay(this);
    public GameOptions(GameManager game) {
        super(game);
        audioOptions = game.getAudioOptions();
        loadImgs();
        loadButton();
        
    }

    @Override
    public void update() {
        if(!isKeyChangeActive){
            menuB.update();
            audioOptions.update();
            changeKeyButton.update();
        }else{
            changeKey.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        if(!isKeyChangeActive){
            g.drawImage(backgroundImg, 0, 0, GameManager.GAME_WIDTH, GameManager.GAME_HEIGHT, null);
            g.drawImage(optionsBackground, bgX, bgY, bgW, bgH, null);
            audioOptions.draw(g);
            menuB.draw(g);
            changeKeyButton.draw(g);
        }else{
            changeKey.draw(g);
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!isKeyChangeActive){
            if(isIn(e,menuB)){
                menuB.setMousePressed(true);
            }else if(isIn(e,changeKeyButton)){
                changeKeyButton.setMousePressed(true);
            }
            else{
                audioOptions.mousePressed(e);
            }
        }else{
            changeKey.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!isKeyChangeActive){
            if(isIn(e,menuB)){
                if(menuB.isMousePressed()){
                    GameStates.state=GameStates.MENU;
                }
            }else if(isIn(e,changeKeyButton)){
                isKeyChangeActive=true;
            }else{
                audioOptions.mouseReleased(e);
            }
            menuB.resetBools();
            changeKeyButton.resetBools();
        }else{
            changeKey.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!isKeyChangeActive){
            menuB.setMouseOver(false);
            changeKeyButton.setMouseOver(false);
            if(isIn(e,menuB)){
                menuB.setMouseOver(true);
            }else if(isIn(e,changeKeyButton)){
                changeKeyButton.setMouseOver(true);
            }else{
                audioOptions.mouseMoved(e);
            }
        }else{
            changeKey.mouseMoved(e);
        }
    }
    public void mouseDragged(MouseEvent e){
        if(!isKeyChangeActive){
            audioOptions.mouseDragged(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(isKeyChangeActive){
            changeKey.keyPressed(e);
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
        int ckX = (int)(GameManager.GAME_WIDTH/2-70);
        int ckY=(int)(480*GameManager.SCALE);
        changeKeyButton=new ChangeInputsButton(ckX,ckY,140,56,0);
    }
    private boolean isIn(MouseEvent e, PauseButton btn){
        return btn.getBounds().contains(e.getX(),e.getY());
    }
    public void setKeyChange(boolean isKeyChangeActive){
        this.isKeyChangeActive=isKeyChangeActive;
    }
}
