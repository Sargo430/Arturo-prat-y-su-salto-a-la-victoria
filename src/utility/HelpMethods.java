
package utility;

import java.awt.geom.Rectangle2D;
import superarturoprat.GameManager;


public class HelpMethods {
    public static boolean canMoveHere(float x, float y,float width,float height,int[][] lvlData){
        if(!IsSolid(x,y,lvlData)){
            if(!IsSolid(x+width,y+height,lvlData)&&!IsSolid(x+width,y+height/2,lvlData)){
                 if(!IsSolid(x+width,y,lvlData)){
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
        int value = lvlData[(int)yIndex][(int)xIndex];
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
    public static float GetEntityYPosCollision(Rectangle2D.Float hitbox,float airSpeed){
        int currentTile = (int)(hitbox.y/64);
        if(airSpeed>0){
            int tileYpos= currentTile*64;
            int yOffset =(int)(64*3-hitbox.height);
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
}

