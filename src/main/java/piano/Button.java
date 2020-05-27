package piano;

import processing.core.PApplet;

public abstract class Button implements Drawable{
    int X_COORD;
    int Y_COORD;

    Button() {
        int[] values = values();
        X_COORD = values[0];
        Y_COORD = values[1];
    }

    public abstract int[] values();

    public abstract void render(PApplet app);
}