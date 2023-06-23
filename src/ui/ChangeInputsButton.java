
package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utility.LoadSave;


public class ChangeInputsButton extends URMButtons{
    private BufferedImage[][] buttonImg;
    public ChangeInputsButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height, rowIndex);
        loadImgs();
    }
    private void loadImgs(){
        buttonImg=new BufferedImage[2][3];
        BufferedImage temp =LoadSave.getSpriteAtlas(LoadSave.INPUTS_BUTTON);
        for(int i=0;i<buttonImg[0].length;i++){
             buttonImg[0][i]=temp.getSubimage(i*140, 0, 140, 56); 
        } 
        temp=LoadSave.getSpriteAtlas(LoadSave.EMPTY_BUTTON);
        for(int i=0;i<buttonImg[1].length;i++){
             buttonImg[1][i]=temp.getSubimage(i*42, 0, 42, 42); 
        }
        
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(buttonImg[rowIndex][index], x, y, width, height, null);
    }
    
    
}
