package piano;

import processing.core.PApplet;
import processing.core.PImage;

/** Pointer (Cursor) class.
 *  Implements the Drawable interface.
 */
public class Pointer implements Drawable {
    private PImage img;
    private AudioManager audioTrack;
    public final int Y_COORD = 59;
    public final int INIT_X = 48;
    private int X_COORD;
    private boolean playing;

    /**
     * 
     * @param img       PImage object containing the image for the pointer.
     * @param audioTrack    AudioManager object associated with the session.
     */
    public Pointer(PImage img, AudioManager audioTrack) {
        this.img = img;
        this.audioTrack = audioTrack;
        X_COORD = INIT_X;
        playing = false;
    }

    /** Sets playing to false if its true, else sets it to false. */
    public void togglePlay() {
        playing = !playing;
    }

    /** Sets playing to false. Sets the x coordinate to the initial value. */
    public void stop() {
        playing = false;
        X_COORD = INIT_X;
    }

    
    /** 
     * @return int The current tick position.
     */
    public int getCurrentTick() {
        return (int)((this.X_COORD + 12 - 60)/7.5);
    }

    /** Moves the cursor if playing. */
    public void tick() {
        if(playing == false)
            return;
        X_COORD += 1;
        // App width(540) - 1/2 of green cursor width(12)
        if(X_COORD >= 540 - 12) {
            X_COORD = INIT_X;
            audioTrack.reset();
        }
    }

    
    /** Draw the pointer image and the red line. 
     * 
     * @param app   PApplet object to draw render on.
     */
    public void render(PApplet app) {
        app.image(this.img, X_COORD, Y_COORD);
        app.stroke(255,0,0);
        app.line(X_COORD + 12, Y_COORD + 16, X_COORD + 12, app.height);
    }

    
    /** 
     * @return int  The pointers x coordinate.
     */
    public int getX_COORD() {
        return X_COORD;
    }

}