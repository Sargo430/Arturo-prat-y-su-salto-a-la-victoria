
package levels;

import entities.Peruano;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import objects.Cannon;
import objects.GameContainer;
import objects.Potions;
import objects.Spike;
import superarturoprat.GameManager;
import utility.HelpMethods;
import static utility.HelpMethods.getLevelData;
import static utility.HelpMethods.getPeruanos;
import static utility.HelpMethods.getPlayerSpawnPoint;

public class Level {
    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Peruano> peruanos;
    private ArrayList<Potions> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;
    private ArrayList<Cannon> cannons;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;
    public Level(BufferedImage img){
        this.img=img;
        createLvlData();
        createEnemies();
        createPotions();
        createContainers();
        createSpikes();
        createCannons();
        calcLvlOffset();
        calcPlayerSpawn();
        
    }
    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }
    public int[][] getLvlData(){
        return this.lvlData;
    }

    private void createLvlData() {
        lvlData=getLevelData(img);
    }

    private void createEnemies() {
        peruanos= getPeruanos(img);
    }

    private void calcLvlOffset() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide-GameManager.TILES_WIDTH;
        maxLvlOffsetX= GameManager.TILE_SIZE*maxTilesOffset;
    }
    public int getLvlOffset(){
        return maxLvlOffsetX;
    }
    public ArrayList<Peruano> getPeruano(){
        return peruanos;
    }

    private void calcPlayerSpawn() {
        playerSpawn = getPlayerSpawnPoint(img);
    }
    public Point getPlayerSpawn(){
        return playerSpawn;
    }

    private void createPotions() {
        potions = HelpMethods.getPotions(img);
    }

    private void createContainers() {
        containers= HelpMethods.getContainers(img);
    }

    public ArrayList<Potions> getPotions() {
        return potions;
    }

    public ArrayList<GameContainer> getContainers() {
        return containers;
    }

    private void createSpikes() {
        spikes= HelpMethods.getSpikes(img);
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }

    private void createCannons() {
        cannons = HelpMethods.getCannons(img);
    }

    public ArrayList<Cannon> getCannons() {
        return cannons;
    }
    
    
}
