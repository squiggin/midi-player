package piano.Buttons;

import piano.AudioManager;
import piano.Button;
import piano.Pointer;
import processing.core.PApplet;
import processing.core.PImage;

public class StopButton extends Button {

    private PImage img;
    public final int X_COORD = 50;
    public final int Y_COORD = 5;

    public StopButton(PImage img) {
        this.img = img;
    }

    /**
     * 
     * @return  The x coordinate and y coordinate associated with the button,
     *          wrapped in an array.
     */
    public int[] values() {
        return new int[] {X_COORD, Y_COORD};
    }

    
    /** Stops playback and resets pointer to start.
     * 
     * @param pointer       Pointer object associated with the current session
     * @param playButton    Play button associated with the current session
     * @param audioTrack    AudioManager object associated with the current session
     */
    public void tick(Pointer pointer, PlayButton playButton, AudioManager audioTrack) {
        pointer.stop();
        audioTrack.reset();
        playButton.stop(audioTrack);
    }

    
    /** Draws button image on the PApplet object
     * @param app
     */
    public void render(PApplet app) {
        app.image(img, X_COORD, Y_COORD);
        app.noStroke();
        app.fill(100);
        app.rect(X_COORD + 5, Y_COORD + 5, 30, 30);
    }
}