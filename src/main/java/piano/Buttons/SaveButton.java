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
     * 
     * @return  The x coordinate and y coordinate associated with the button,
     *          wrapped in an array.
     */
    @Override
    public int[] values() {
        return new int[] { X_COORD, Y_COORD };
    }

    
    /** Saves current sequence and instrument to the savedata.sav file in the project root directory
     * 
     * @param audioTrack    AudioManager object associated with the current session
     * @param instManager   InstrumentManager object associated with the current session
     * @param grid          Grid object associated with the current session
     */
    public void tick(AudioManager audioTrack, InstrumentManager instManager, Grid grid) {
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

    /** Draws button image on the PApplet object
     * @param app
     */
    public void render(PApplet app) {
        app.image(imgBack, X_COORD, Y_COORD);
        app.image(imgFront, X_COORD, Y_COORD);
    }

}