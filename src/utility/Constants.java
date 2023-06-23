
package utility;

import  superarturoprat.GameManager;


public class Constants {
    public static class Projectiles{
        public static final int CANNON_BALL_DEFAULT_WIDTH= 15;
        public static final int CANNON_BALL_DEFAULT_HEIGHT=15;
        
        public static final int CANNON_BALL_WIDTH= (int)(GameManager.SCALE * CANNON_BALL_DEFAULT_WIDTH*3);
        public static final int CANNON_BALL_HEIGHT=(int)(GameManager.SCALE * CANNON_BALL_DEFAULT_HEIGHT*3);
        public static final float SPEED =0.75f *GameManager.SCALE;
    }
    public static class ObjectConstants{
        public static final int RED_POTION = 0;
        public static final int BLUE_POTION = 1;
        public static final int BARREL = 2;
        public static final int BOX = 3;
        public static final int SPIKE = 4;
        public static final int CANNON_LEFT= 5;
        public static final int CANNON_RIGHT= 6;

        public static final int RED_POTION_VALUE = 15;
        public static final int BLUE_POTION_VALUE = 10;

        public static final int CONTAINER_WIDTH_DEFAULT = 40;
        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
        public static final int CONTAINER_WIDTH = (int) (GameManager.SCALE * CONTAINER_WIDTH_DEFAULT);
        public static final int CONTAINER_HEIGHT = (int) (GameManager.SCALE * CONTAINER_HEIGHT_DEFAULT);

        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (GameManager.SCALE * POTION_WIDTH_DEFAULT);
        public static final int POTION_HEIGHT = (int) (GameManager.SCALE * POTION_HEIGHT_DEFAULT);

        public static final int SPIKE_WIDTH_DEFAULT = 64;
        public static final int SPIKE_HEIGHT_DEFAULT = 64;
        public static final int SPIKE_WIDTH = (int) (GameManager.SCALE * SPIKE_WIDTH_DEFAULT);
        public static final int SPIKE_HEIGHT = (int) (GameManager.SCALE * SPIKE_HEIGHT_DEFAULT);
        
        public static final int CANNON_WIDTH_DEFAULT=40;
        public static final int CANNON_HEIGHT_DEFAULT=26;
        public static final int CANNON_WIDTH=(int)(CANNON_WIDTH_DEFAULT*3*GameManager.SCALE);
        public static final int CANNON_HEIGHT=(int)(CANNON_HEIGHT_DEFAULT*3*GameManager.SCALE);

        public static int getSpriteAmount(int object_type) {
                switch (object_type) {
                case RED_POTION, BLUE_POTION:
                        return 7;
                case BARREL, BOX:
                        return 8;
                case CANNON_LEFT,CANNON_RIGHT:
                    return 7;
                }
                return 1;
        }
    }
    public static final float GRAVITY=0.04f*GameManager.SCALE;
    public static final int ANIMATION_SPEED=7;
    public static class UtilityConstants{
        public static final int PLAYER_Y_HITBOX_FIX=2;
        public static final int PERUANO_Y_HITBOX_FIX=2;
        public static int getYhitboxFix(int entityType){
            switch(entityType){
                case 0:
                    return PLAYER_Y_HITBOX_FIX;
                case 1:
                    return PERUANO_Y_HITBOX_FIX;
            }
            return 2;
        }
    }
    public static class EnemyConstants{
        public static final int PERUANO=0;
        public static final int IDLE=0;
        public static final int RUNNING=1;
        public static final int HIT=2;
        public static final int DEAD=3;
        public static final int ATTACKING=4;
        public static final int PERUANO_WIDTH_DEFAULT=64;
        public static final int PERUANO_HEIGHT_DEFAULT=64;
        public static final int PERUANO_WIDTH=(int)(PERUANO_WIDTH_DEFAULT*GameManager.SCALE);
        public static final int PERUANO_HEIGHT=(int)(PERUANO_HEIGHT_DEFAULT*GameManager.SCALE);
        public static final int PERUANO_DRAWOFFSET_X=(int)(25*GameManager.SCALE*2);
        public static final int PERUANO_DRAWOFFSET_Y=(int)(6*GameManager.SCALE*2);
        public static int getMaxHealth(int enemyType){
            switch(enemyType){
                case PERUANO:
                    return 10;
            }
            return 0;
        }
        public static int getEnemyDamage(int enemyType){
            switch(enemyType){
                case PERUANO:
                    return 20;
            }
            return 0;
        }
        public static int getSpriteAmount(int enemyType,int enemyState){
            switch(enemyType){
                case PERUANO:
                    switch(enemyState){
                        case IDLE:
                            return 1;
                        case RUNNING:
                            return 16;                     
                        case HIT:
                            return 2;
                        case DEAD:
                            return 5;
                        case ATTACKING:
                            return 2;
                    }
                    break;
            }
            return 0;
        }
        public static int getSpriteScale(int enemyType, int enemyState){
            switch(enemyType){
                case PERUANO:
                    switch(enemyState){
                        case IDLE:
                            return 1;
                        case RUNNING:
                            return 1;
                        case HIT:
                            return 1;
                        case DEAD:
                            return 1;
                        case ATTACKING:
                        return 2;
                    }
                    break;
            }
            return 1;
        }
    }
    public static class Enviroment{
        public static final int BIGCLOUD_WIDTH_DEFAULT=448;
        public static final int BIGCLOUD_HEIGHT_DEFAULT=101;
        public static final int  BIGCLOUD_WIDTH=(int)(BIGCLOUD_WIDTH_DEFAULT*GameManager.SCALE);
        public static final int  BIGCLOUD_HEIGHT=(int)(BIGCLOUD_HEIGHT_DEFAULT*GameManager.SCALE);
        public static final int SMALLCLOUD_WIDTH_DEFAULT=74;
        public static final int SMALLCLOUD_HEIGHT_DEFAULT=24;
        public static final int  SMALLCLOUD_WIDTH=(int)(SMALLCLOUD_WIDTH_DEFAULT*GameManager.SCALE);
        public static final int  SMALLCLOUD_HEIGHT=(int)(SMALLCLOUD_HEIGHT_DEFAULT*GameManager.SCALE);
    }
    public static class Directions{
        public static final int LEFT=0;
        public static final int UP=1;
        public static final int RIGHT=2;
        public static final int DOWN=3;
    } public static class UI{
        public static class Buttons{
            public static final int b_Width_default =140;
            public static final int b_heigth_default =56;
            public static final int b_width = (int)(b_Width_default*GameManager.SCALE);
            public static final int b_heigth = (int)(b_heigth_default*GameManager.SCALE);
        }
        public static class PauseButtons{
            public static final int SOUND_SIZE_DEFAULT=42;
            public static final int SOUND_SIZE=(int)(SOUND_SIZE_DEFAULT*GameManager.SCALE);
        }
        public static class UrmButtons{
            public static final int URM_DEFAULT_SIZE=56;
            public static final int URM_SIZE=(int)(URM_DEFAULT_SIZE*GameManager.SCALE);
        }
        public static class VolumeButtons{
            public static final int VOLUME_DEFAULT_WIDTH=28;
            public static final int VOLUME_DEFAULT_HEIGHT=44;
            public static final int SLIDER_DEFAULT_WIDTH =215;
            public static final int SLIDER_WIDTH =(int)(215*GameManager.SCALE);;
            public static final int VOLUME_WIDTH=(int)(VOLUME_DEFAULT_WIDTH*GameManager.SCALE);
            public static final int VOLUME_HEIGHT=(int)(VOLUME_DEFAULT_HEIGHT*GameManager.SCALE);
        }
    }
    
    public static class PlayerConstants{
        
        public static int getSpriteAmount(PlayerAction player_action){
            int framesAnim=0;
            switch(player_action){
                case IDLE:
                    framesAnim=1;
                    break;
                case RUNNING:
                    framesAnim=16;
                    break;
                case JUMPING:
                    framesAnim=2;
                    break;
                case FALLING:
                    framesAnim=3;
                    break;
                case CROUNCHING:
                    framesAnim=4;
                    break;
                case SWORD_ATTACK:
                    framesAnim=2;
                    break;
                case GUN_ATTACK:
                    framesAnim=2;
                    break;
                case RECEIVE_DAMAGE:
                    framesAnim=2;
                    break;
                case DEATH:
                    framesAnim=5;
                    break;
            }
            return framesAnim;
        }
        public static int getSpriteScale(PlayerAction player_action){
            int scale=0;
            switch(player_action){
                case IDLE:
                    scale=1;
                    break;
                case RUNNING:
                    scale=1;
                    break;
                case JUMPING:
                    scale=1;
                    break;
                case FALLING:
                    scale=1;
                    break;
                case CROUNCHING:
                    scale=1;
                    break;
                case SWORD_ATTACK:
                    scale=2;
                    break;
                case GUN_ATTACK:
                    scale=2;
                    break;
                case RECEIVE_DAMAGE:
                    scale=1;
                    break;
                case DEATH:
                    scale=1;
                    break;
            }
            return scale;
        }
    }
    public enum PlayerAction{
        IDLE,
        RUNNING,
        JUMPING,
        FALLING,
        CROUNCHING,
        RECEIVE_DAMAGE,
        DEATH,
        SWORD_ATTACK,
        GUN_ATTACK
        }
    
}


 