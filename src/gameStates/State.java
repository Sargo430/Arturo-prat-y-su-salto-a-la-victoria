
package gameStates;

import java.awt.event.MouseEvent;
import superarturoprat.GameManager;
import ui.MenuButton;


public class State {
    protected GameManager game;
    public State(GameManager game){
         this.game=game;
    }
    public boolean isIn(MouseEvent e,MenuButton mb){
        return mb.getBounds().contains(e.getX(),e.getY());
    }
    public GameManager getGame(){
        return this.game;
    }
}
