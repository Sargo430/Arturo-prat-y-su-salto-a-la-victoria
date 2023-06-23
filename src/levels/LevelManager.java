
package levels;

import gameStates.GameStates;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import superarturoprat.GameManager;
import utility.LoadSave;


public class LevelManager {
    private GameManager game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex=0;
    
    public LevelManager(GameManager game){
        this.game=game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }
    public void draw(Graphics g, int lvlOffset){
        for(int j= 0;j < GameManager.TILES_HEIGHT;j++){
            for(int i=0; i< levels.get(lvlIndex).getLvlData()[0].length;i++){
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index],i*GameManager.TILE_SIZE-lvlOffset,j*GameManager.TILE_SIZE,GameManager.TILE_SIZE,GameManager.TILE_SIZE,null);
            }
        }
        
    }
    public void update(){
        
    }

    private void importOutsideSprites() {
        BufferedImage img =LoadSave.getSpriteAtlas(LoadSave.LEVELATLAS);
        levelSprite= new BufferedImage[35];
        for(int j=0;j<5;j++){
            for(int i=0;i<7;i++){
                int index= j*7 + i;
                levelSprite[index]=img.getSubimage(i*64, j*64, 64, 64);
            }
        }
       
    }
    public Level getCurrentLvl(){
        return levels.get(lvlIndex);
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.getAllLvLS();
        for (BufferedImage img: allLevels){
            levels.add(new Level(img));
        }
    }
    public int getAmountOfLevels(){
        return levels.size();
    }
    public void loadNextLvl(){
        lvlIndex++;
        if(lvlIndex >= levels.size()){
            lvlIndex=0;
            GameStates.state=GameStates.MENU;
        }
        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLvlData());
        game.getPlaying().setLvlMaxOffset(newLevel.getLvlOffset());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }

    public int getLvlIndex() {
        return lvlIndex;
    }
    
}
