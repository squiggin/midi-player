package piano.Buttons;

import piano.Button;
import processing.core.PApplet;
import processing.core.PImage;

public class SaveButton extends Button {
    
    private PImage imgBack;
    private PImage imgFront;
    final int X_COORD = 140;
    final int Y_COORD = 5;

    public SaveButton(PImage imgBack, PImage imgFront) {
        this.imgBack = imgBack;
        this.imgFront = imgFront;
    }

    @Override
    public int[] values() {
        return new int[] {X_COORD, Y_COORD};
    }
    
    public void tick() {
        System.out.println("save");
    }

    public void render(PApplet app) {
        app.image(imgBack, X_COORD, Y_COORD);
        app.image(imgFront, X_COORD, Y_COORD);
    }

}