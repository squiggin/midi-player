package piano.Buttons;

import piano.AudioManager;
import piano.Button;
import piano.Grid;
import piano.InstrumentManager;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveButton extends Button {

    private PImage imgBack;
    private PImage imgFront;
    public final int X_COORD = 140;
    public final int Y_COORD = 5;

    public SaveButton(PImage imgBack, PImage imgFront) {
        this.imgBack = imgBack;
        this.imgFront = imgFront;
    }

    
    /** 
     * @return int[]
     */
    @Override
    public int[] values() {
        return new int[] { X_COORD, Y_COORD };
    }

    
    /** 
     * @param audioTrack
     * @param instManager
     * @param grid
     */
    public void tick(AudioManager audioTrack, InstrumentManager instManager, Grid grid) {
        System.out.println("save");
        File saveFile = new File("savedata.sav");
        try {
            FileOutputStream saveStream = new FileOutputStream(saveFile);
            saveStream.write((byte) instManager.getCurrentIndex());
            for (int tick: grid.blocks.keySet()) {
                saveStream.write((byte) tick);
                saveStream.write((byte) grid.blocks.get(tick).size());
                for (int note: grid.blocks.get(tick).keySet()) {
                    saveStream.write((byte) note);
                }
            }
            saveStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    /** */
    public void render(PApplet app) {
        app.image(imgBack, X_COORD, Y_COORD);
        app.image(imgFront, X_COORD, Y_COORD);
    }

}