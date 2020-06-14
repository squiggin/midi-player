package piano.Buttons;

import piano.AudioManager;
import piano.Button;
import piano.Pointer;
import processing.core.PApplet;
import processing.core.PImage;

public class PlayButton extends Button {

    private PImage img;
    private boolean play;
    public final int X_COORD = 5;
    public final int Y_COORD = 5;
    
    public PlayButton(PImage img) {
        this.img = img;
        play = false;
    }

    
    
    /**
     * 
     * @return  The x coordinate and y coordinate associated with the button,
     *          wrapped in an array.
     */
    public int[] values() {
        return new int[] {X_COORD, Y_COORD};
    }

    
    /** Switches play to true if its false, otherwise sets it to true.
     * Starts/stops playback accordingly.
     * 
     * @param pointer       Pointer object associated with the current session
     * @param audioTrack    AudioManager object associated with the current session
     */
    private void togglePlay(Pointer pointer, AudioManager audioTrack) {
        pointer.togglePlay();
        play = !play;
        if(play) {
            audioTrack.startPlaying();
        } else {
            audioTrack.stopPlaying();
        }
    }

    
    /** Stops audio playback
     * 
     * @param audioTrack    AudioManager object associated with the current session
     */
    public void stop(AudioManager audioTrack) {
        play = false;
        audioTrack.stopPlaying();
    }

    
    /** Handles clicks on this button
     * Toggles between play and pause.
     * 
     * @param pointer       Pointer object associated with the current session
     * @param audioTrack    AudioManager object associated with the current session
     */
    public void tick(Pointer pointer, AudioManager audioTrack) {
        this.togglePlay(pointer, audioTrack);
    }

    
    /** 
     * @param app
     */
    public void render(PApplet app) {
        app.image(img, X_COORD, Y_COORD);
        app.noStroke();
        app.fill(100);
        if(!play) {
            app.triangle(X_COORD + 8, Y_COORD + 5, X_COORD + 32, Y_COORD + 20, X_COORD + 8, Y_COORD + 35);
        } else {
            app.rect(X_COORD + 8, Y_COORD + 5, 8, 30);
            app.rect(X_COORD + 24, Y_COORD + 5, 8, 30);
        }
    }
}