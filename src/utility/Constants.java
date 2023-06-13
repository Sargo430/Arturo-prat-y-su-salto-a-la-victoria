
package utility;


public class Constants {
    public static class Directions{
        public static final int LEFT=0;
        public static final int UP=1;
        public static final int RIGHT=2;
        public static final int DOWN=3;
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
        SWORD_ATTACK,
        GUN_ATTACK
        }
    
}


 