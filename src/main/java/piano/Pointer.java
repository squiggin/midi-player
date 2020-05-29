package piano;

import processing.core.PApplet;
import processing.core.PImage;

public class Pointer implements Drawable {
    private PImage img;
    private AudioManager audioTrack;
    public final int Y_COORD = 59;
    public final int INIT_X = 48;
    private int X_COORD;
    private long lastTime;
    private boolean playing;

    public Pointer(PImage img, AudioManager audioTrack) {
        this.img = img;
        this.audioTrack = audioTrack;
        X_COORD = INIT_X;
        lastTime = System.currentTimeMillis();
        playing = false;
    }

    public void togglePlay() {
        playing = !playing;
    }

    public void stop() {
        playing = false;
        X_COORD = INIT_X;
    }

    public int getCurrentTick() {
        return (int)((this.X_COORD + 12 - 60)/7.5);
    }

    public void tick() {
        if(playing == false)
            return;
        // Can't use time difference because it would require Thread.sleep
        // to test.
        //if(System.currentTimeMillis() - lastTime >= (1/6) ) {
        X_COORD += 1;
        lastTime = System.currentTimeMillis();
        //}
        // App width(540) - 1/2 of green cursor width(12)
        if(X_COORD >= 540 - 12) {
            X_COORD = INIT_X;
            audioTrack.reset();
        }
    }

    public void render(PApplet app) {
        app.image(this.img, X_COORD, Y_COORD);
        app.stroke(255,0,0);
        app.line(X_COORD + 12, Y_COORD + 16, X_COORD + 12, app.height);
    }

    public int getX_COORD() {
        return X_COORD;
    }

}