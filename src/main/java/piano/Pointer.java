package piano;

import processing.core.PApplet;
import processing.core.PImage;

public class Pointer implements Drawable {
    private PImage img;
    private AudioManager audioTrack;
    private final int Y_COORD = 59;
    private final int INIT_X = 48;
    int X_COORD;
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

    public void tick(PApplet app) {
        if(playing == false)
            return;
        if(System.currentTimeMillis() - lastTime >= (1/6) ) {
            X_COORD += 1;
            lastTime = System.currentTimeMillis();
        }
        if(X_COORD >= app.width - 12) {
            X_COORD = INIT_X;
            audioTrack.reset();
        }
    }

    public void render(PApplet app) {
        app.image(this.img, X_COORD, Y_COORD);
        app.stroke(255,0,0);
        app.line(X_COORD + 12, Y_COORD + 16, X_COORD + 12, app.height);
    }

}