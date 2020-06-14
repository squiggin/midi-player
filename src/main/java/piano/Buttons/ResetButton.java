package piano.Buttons;

import piano.AudioManager;
import piano.Button;
import piano.Grid;
import piano.Pointer;
import processing.core.PApplet;
import processing.core.PImage;

public class ResetButton extends Button {

    private PImage imgBack;
    private PImage imgFront;
    public final int X_COORD = 95;
    public final int Y_COORD = 5;

    public ResetButton(PImage imgBack, PImage imgFront) {
        this.imgBack = imgBack;
        this.imgFront = imgFront;
    }

    /**
     * 
     * @return  The x coordinate and y coordinate associated with the button,
     *          wrapped in an array.
     */
    public int[] values() {
        return new int[] {X_COORD, Y_COORD};
    }

    
    /** Clears the current notes, stops playback and resets the pointer to the start
     * 
     * @param grid          Grid object associated with the current session
     * @param stop          Stop Button associated with the current session
     * @param pointer       Pointer object associated with the current session
     * @param play          Play Button associated with the current session
     * @param audioTrack    AudioManager object associated with the current session
     */
    public void tick(Grid grid, StopButton stop, Pointer pointer, PlayButton play, AudioManager audioTrack) {
        grid.clear();
        stop.tick(pointer, play, audioTrack);
        audioTrack.clear();
    }

    
    /** Draws button image on the PApplet object
     * @param app
     */
    public void render(PApplet app) {
        app.image(imgBack, X_COORD, Y_COORD);
        app.image(imgFront, X_COORD, Y_COORD);
    }
}