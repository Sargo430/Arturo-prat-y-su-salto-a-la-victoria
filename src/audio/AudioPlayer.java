package audio;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioPlayer {
    public static int MENU = 0;
    public static int LEVEL_1=1;
    public static int LEVEL_2=2;
    public static int LEVEL_3=3;
    
    public static int DIE=0;
    public static int JUMP =1;
    public static int GAMEOVER=2;
    public static int LVL_COMPLETED=3;
    public static int SWORD_ATTACK1=4;
    public static int SWORD_ATTACK2=5;
    public static int GUN_SHOT=6;
    private Clip[] songs,effects;
    private int currentSongId;
    private float volume = 0.5f;
    private boolean musicMute,sfxMute;
    private Random rand = new Random();
    public AudioPlayer(){
        loadSongs();
        loadSFX();
        playSong(MENU);
    }
    private void loadSongs(){
        String[] names = {"menu","level1","level2","level3"};
        songs = new Clip[names.length];
        for(int i = 0;i<songs.length;i++){
            songs[i] = getClip(names[i]);
        }
        
    }
    private void loadSFX(){
        String[] names = {"die","jump","gameOver","level_completed","attack01","attack02","gun_shot"};
        effects = new Clip[names.length];
        for(int i = 0;i<effects.length;i++){
            effects[i] = getClip(names[i]);
        }
        updateSFXVolume();
    }
    private Clip getClip(String name){
        URL url = getClass().getResource("/audio/" + name + ".wav");
        AudioInputStream audio;
        try {
            audio = AudioSystem.getAudioInputStream(url);
            Clip c= AudioSystem.getClip();
            c.open(audio);
            return c;
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    private void updateSongVolume(){
        FloatControl gainControl =(FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum()-gainControl.getMinimum();
        float gain = (range* volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
        
    }
    private void updateSFXVolume(){
        for(Clip c:effects){
           FloatControl gainControl =(FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
           float range = gainControl.getMaximum()-gainControl.getMinimum();
           float gain = (range* volume) + gainControl.getMinimum();
           gainControl.setValue(gain); 
        }
        
    }
    public void toggleSongMute(){
        this.musicMute=!musicMute;
        for(Clip c:songs){
            BooleanControl booleanControl = (BooleanControl)(c.getControl(BooleanControl.Type.MUTE));
            booleanControl.setValue(musicMute);
        }
    }
    public void toggleSFXMUTE(){
        this.sfxMute=!sfxMute;
        for(Clip c:effects){
            BooleanControl booleanControl = (BooleanControl)(c.getControl(BooleanControl.Type.MUTE));
            booleanControl.setValue(musicMute);
        }
        if(!sfxMute){
            playEffect(LVL_COMPLETED);
            
        }
        
    }
    public void playAttackSound(){
        int start=4;
        start += rand.nextInt(2);
        playEffect(start);
    }
    public void setVolume(float volume){
        this.volume=volume;
        updateSongVolume();
        updateSFXVolume();
    }
    public void stopSong(){
        if(songs[currentSongId].isActive()){
            songs[currentSongId].stop();
        }
    }
    public void setLevelSong(int levelIndex){
        playSong(levelIndex+1);
    }
    public void levelCompleted(){
        stopSong();
        playEffect(LVL_COMPLETED);
    }
    
    public void playSong(int song){
        stopSong();
        currentSongId=song;
        updateSongVolume();
        songs[currentSongId].setMicrosecondPosition(0);
        songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
        
    }
    public void playEffect(int sfx){
        effects[sfx].setMicrosecondPosition(0);
        effects[sfx].start();
    }
    public void stopAllEffects(){
        for(Clip c:effects){
            c.stop();
        }
    }
    
    
}
