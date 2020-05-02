package piano;

import processing.core.PApplet;
import processing.core.PImage;

public class StopButton implements Drawable {

    private PImage img;
    final static int X_COORD = 50;
    final static int Y_COORD = 5;
    public StopButton(PImage img) {
        this.img = img;
    }

    private void stop(Pointer pointer, PlayButton playButton) {
        pointer.stop();
        playButton.stop();
    }

    public void tick(Pointer pointer, PlayButton playButton) {
        this.stop(pointer, playButton);
    }

    public void render(PApplet app) {
        app.image(img, X_COORD, Y_COORD);
        app.noStroke();
        app.fill(100);
        app.rect(X_COORD + 5, Y_COORD + 5, 30, 30);
    }
}