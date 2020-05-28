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
    final int X_COORD = 100;
    final int Y_COORD = 5;

    public ResetButton(PImage imgBack, PImage imgFront) {
        this.imgBack = imgBack;
        this.imgFront = imgFront;
    }

    public int[] values() {
        return new int[] {X_COORD, Y_COORD};
    }

    public void tick(Grid grid, StopButton stop, Pointer point, PlayButton play, AudioManager audioTrack) {
        System.out.println("Reset");
        grid.clear();
        stop.tick(point, play, audioTrack);
        audioTrack.clear();
    }

    public void render(PApplet app) {
        app.image(imgBack, X_COORD, Y_COORD);
        app.image(imgFront, X_COORD, Y_COORD);
    }
}