
package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import superarturoprat.GameManager;
import utility.LoadSave;


public class LevelManager {
    private GameManager game;
    private BufferedImage[] levelSprite;
    private Level levelOne;
    
    public LevelManager(GameManager game){
        this.game=game;
        importOutsideSprites();
        levelOne = new Level(LoadSave.getLevelData());
    }
    public void draw(Graphics g){
        for(int j= 0;j < 10;j++){
            for(int i=0; i< GameManager.TILES_WIDTH;i++){
                int index = levelOne.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index],i*GameManager.TILE_SIZE,j*GameManager.TILE_SIZE,GameManager.TILE_SIZE,GameManager.TILE_SIZE,null);
            }
        }
        
    }
    public void update(){
        
    }

    private void importOutsideSprites() {
        BufferedImage img =LoadSave.getSpriteAtlas(LoadSave.LEVELATLAS);
        levelSprite= new BufferedImage[21];
        for(int j=0;j<3;j++){
            for(int i=0;i<7;i++){
                int index= j*7 + i;
                levelSprite[index]=img.getSubimage(i*64, j*64, 64, 64);
            }
        }
       
    }
    public Level getCurrentLvl(){
        return levelOne;
    }
}
