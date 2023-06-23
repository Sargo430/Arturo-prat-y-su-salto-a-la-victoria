
package utility;

import entities.Peruano;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import objects.Cannon;
import objects.GameContainer;
import objects.Potions;
import objects.Projectile;
import objects.Spike;
import superarturoprat.GameManager;
import static utility.Constants.EnemyConstants.PERUANO;
import static utility.Constants.ObjectConstants.BARREL;
import static utility.Constants.ObjectConstants.BLUE_POTION;
import static utility.Constants.ObjectConstants.BOX;
import static utility.Constants.ObjectConstants.CANNON_LEFT;
import static utility.Constants.ObjectConstants.CANNON_RIGHT;
import static utility.Constants.ObjectConstants.RED_POTION;
import static utility.Constants.ObjectConstants.SPIKE;
import static utility.Constants.UtilityConstants.getYhitboxFix;
import static utility.LoadSave.getSpriteAtlas;


public class HelpMethods {
    public static boolean canMoveHere(float x, float y,float width,float height,int[][] lvlData){
        if(!IsSolid(x,y,lvlData)){
            if(!IsSolid(x+width,y+height,lvlData)&&!IsSolid(x+width,y+height/2,lvlData)){
                 if(!IsSolid(x+width,y,lvlData )){
                     if(!IsSolid(x,y+height,lvlData)){
                         if(!IsSolid(x,y+height/2,lvlData)&&!IsSolid(x,y+height/3,lvlData)&&!IsSolid(x,y+height*3/4,lvlData)){
                            return true; 
                         }
                         
                     }
                 }   
            } 
        }
        return false;
    }
    private static boolean IsSolid(float x,float y,int[][]lvlData){
        int maxWidth = lvlData[0].length*GameManager.TILE_SIZE;
        if(x <0 || x >= maxWidth)
            {
                return true;
            }
        if(y < 0|| y >= 64*10)
        {
            return true;
        } 
        float xIndex= x/64;
        float yIndex= y/64;
        
        return IsTileSolid((int)(xIndex),(int)(yIndex),lvlData);
    }
    public static boolean IsTileSolid(int xTile,int yTile,int[][]lvlData){
        int value = lvlData[yTile][xTile];
        if(value>=21 || value<0 || value!=7){
           return true; 
        }  
        return false;
    }
    public static float getEntityXPosNextToWall(Rectangle2D.Float hitbox,float xSpeed){
        int currentTile = (int)(hitbox.x/64);
        
        if(xSpeed>0){
            int tileXposition= currentTile*64;
            int xOffset= (int)(64*2-(hitbox.width));
            return tileXposition + xOffset -1;
        }else{
            return currentTile*64;
        }
    }
    public static float GetEntityYPosCollision(Rectangle2D.Float hitbox,float airSpeed,int entityType){
        int currentTile = (int)(hitbox.y/64);
        if(airSpeed>0){
            int tileYpos= currentTile*64;
            int yOffset =(int)(64*getYhitboxFix(entityType)-hitbox.height);
            return tileYpos + yOffset-1;
        }else{
            return currentTile*64;
        }
    }
    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox,int[][] lvlData){
        if(!IsSolid(hitbox.x,hitbox.y+hitbox.height+1,lvlData)){
            if(!IsSolid(hitbox.x+hitbox.width,hitbox.y+hitbox.height+1,lvlData)){
                return false;
            }
        }
        return true;
    }
    public static boolean isFloor(Rectangle2D.Float hitbox,float xSpeed,int[][]lvlData){
        if (xSpeed>0) {
            return IsSolid(hitbox.x+xSpeed+hitbox.width,hitbox.y+hitbox.height+1,lvlData);   
        }
        else{
           return IsSolid(hitbox.x+xSpeed,hitbox.y+hitbox.height+1,lvlData); 
        }
    }
    public static boolean isSightClear(int[][]lvlData,Rectangle2D.Float firstHitbox,Rectangle2D.Float secondHitbox,int yTile){
        int firstXtile = (int)(firstHitbox.x/GameManager.TILE_SIZE);
        int secondXtile;
        if(IsSolid(secondHitbox.x,secondHitbox.y+secondHitbox.height+1,lvlData)){
            secondXtile = (int)(secondHitbox.x/GameManager.TILE_SIZE);
        }else{
           secondXtile = (int)((secondHitbox.x+secondHitbox.width)/GameManager.TILE_SIZE); 
        }
        if(firstXtile>secondXtile){
            return isAllTilesWalkable(secondXtile,firstXtile,yTile,lvlData);
            
        }else{
            return isAllTilesWalkable(firstXtile,secondXtile,yTile,lvlData);
        }

    }
    public static boolean isAllTilesWalkable(int xStart,int xEnd,int yTile,int[][]lvlData){
        if(IsAllTilesClear(xStart,xEnd,yTile,lvlData)){
                    for(int i=0;i< xEnd-xStart;i++){
                
                if(!IsTileSolid(xStart+1,yTile+1,lvlData)){
                    return false;
                }
            }
        }

        return true;
    }
    public static int[][] getLevelData(BufferedImage image){
        int[][] lvlData = new int[image.getHeight()][image.getWidth()];
        for(int j=0;j< image.getHeight();j++){
            for(int i=0; i< image.getWidth();i++){
                Color color = new Color(image.getRGB(i, j));
                int value= color.getRed();
                if(value>35){
                    value=7;
                }
                lvlData[j][i]= value;
            }
        }
        return lvlData;
    }
    public static ArrayList<Peruano> getPeruanos(BufferedImage image){
        ArrayList<Peruano> list = new ArrayList<>();
        for(int j=0;j< image.getHeight();j++){
            for(int i=0; i< image.getWidth();i++){
                Color color = new Color(image.getRGB(i, j));
                int value= color.getGreen();
                if(value == PERUANO){
                    list.add(new Peruano(i*GameManager.TILE_SIZE,j*GameManager.TILE_SIZE));
                }
                
            }
        }
        return list;
    }
    public static Point getPlayerSpawnPoint(BufferedImage image){
        for(int j=0;j< image.getHeight();j++){
            for(int i=0; i< image.getWidth();i++){
                Color color = new Color(image.getRGB(i, j));
                int value= color.getGreen();
                if(value == 100){
                    return new Point(i*GameManager.TILE_SIZE,j*GameManager.TILE_SIZE);
                }
                
            }
        }
        return new Point(1*GameManager.TILE_SIZE,1*GameManager.TILE_SIZE);
    }
    public static ArrayList<Potions> getPotions(BufferedImage image){
        ArrayList<Potions> list = new ArrayList<>();
        for(int j=0;j< image.getHeight();j++){
            for(int i=0; i< image.getWidth();i++){
                Color color = new Color(image.getRGB(i, j));
                int value= color.getBlue();
                if(value == RED_POTION || value ==BLUE_POTION){
                    list.add(new Potions(i*GameManager.TILE_SIZE,j*GameManager.TILE_SIZE,value));
                    
                }
            }
        }
        return list;
    }
    public static ArrayList<GameContainer> getContainers(BufferedImage image){
        ArrayList<GameContainer> list = new ArrayList<>();
        for(int j=0;j< image.getHeight();j++){
            for(int i=0; i< image.getWidth();i++){
                Color color = new Color(image.getRGB(i, j));
                int value= color.getBlue();
                if(value == BOX || value ==BARREL){
                    list.add(new GameContainer(i*GameManager.TILE_SIZE,j*GameManager.TILE_SIZE,value));
                    
                }
                
            }
        }
        return list;
    }

    public static ArrayList<Spike> getSpikes(BufferedImage img) {
        ArrayList<Spike> list = new ArrayList<>();
        for(int j=0;j< img.getHeight();j++){
            for(int i=0; i< img.getWidth();i++){
                Color color = new Color(img.getRGB(i, j));
                int value= color.getBlue();
                if(value == SPIKE){
                    list.add(new Spike(i*GameManager.TILE_SIZE,j*GameManager.TILE_SIZE,SPIKE));
                    
                }
                
            }
        }
        return list;
    }
    public static ArrayList<Cannon> getCannons(BufferedImage img) {
            ArrayList<Cannon> list = new ArrayList<>();
        for(int j=0;j< img.getHeight();j++){
            for(int i=0; i< img.getWidth();i++){
                Color color = new Color(img.getRGB(i, j));
                int value= color.getBlue();
                if(value == CANNON_LEFT|| value==CANNON_RIGHT){
                    list.add(new Cannon(i*GameManager.TILE_SIZE,j*GameManager.TILE_SIZE,value));
                    
                }
                
            }
        }
        return list;
    }
    public static boolean canSeePlayer(int[][]lvlData,Rectangle2D.Float firstHitbox,Rectangle2D.Float secondHitbox,int yTile){
        int firstXtile = (int)(firstHitbox.x/GameManager.TILE_SIZE);
        int secondXtile = (int)(secondHitbox.x/GameManager.TILE_SIZE);
        if(firstXtile>secondXtile){
            return IsAllTilesClear(secondXtile,firstXtile,yTile,lvlData);
            
        }else{
            return IsAllTilesClear(firstXtile,secondXtile,yTile,lvlData);
        }
    }
    public static boolean IsAllTilesClear (int xStart,int xEnd,int yTile,int[][]lvlData){
        for(int i=0;i< xEnd-xStart;i++){
                if(IsTileSolid(xStart+i,yTile,lvlData)){
                    return false; 
                }
                else{
                    return true;
                }
                    
        }
        return true;
    }
    public static boolean isProjectileHittingLevel(Projectile p,int[][] lvlData){
        return IsSolid(p.getHitbox().x+p.getHitbox().width/2,p.getHitbox().y+p.getHitbox().height/2,lvlData);
    }
}

