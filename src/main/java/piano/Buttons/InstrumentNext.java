package piano.Buttons;

import piano.Button;
import piano.InstrumentManager;
import processing.core.PApplet;
import processing.core.PImage;

public class InstrumentNext extends Button{
    
    private PImage imgBack;
    private PImage imgFront;
    private InstrumentManager instManager;
    final int X_COORD = 365;
    final int Y_COORD = 5;

    public InstrumentNext(PImage imgBack, PImage imgFront) {
        this.imgBack = imgBack;
        this.imgFront = imgFront;
    }

    @Override
    public int[] values() {
        return new int[] {X_COORD, Y_COORD};
    }
    
    public void tick() {
        instManager.next();
        System.out.println("next");
    }

    public void render(PApplet app) {
        app.image(imgBack, X_COORD, Y_COORD);
        app.image(imgFront, X_COORD, Y_COORD);
    }

    public void setInstManager(InstrumentManager instManager) {
        this.instManager = instManager;
    }

}