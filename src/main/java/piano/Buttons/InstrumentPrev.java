package piano.Buttons;

import piano.Button;
import piano.InstrumentManager;
import processing.core.PApplet;
import processing.core.PImage;

public class InstrumentPrev extends Button {
    
    private PImage imgBack;
    private PImage imgFront;
    private InstrumentManager instManager;
    public final int X_COORD = 275;
    public final int Y_COORD = 5;

    public InstrumentPrev(PImage imgBack, PImage imgFront) {
        this.imgBack = imgBack;
        this.imgFront = imgFront;
    }

    @Override
    public int[] values() {
        return new int[] {X_COORD, Y_COORD};
    }
    
    public void tick() {
        instManager.prev();
        System.out.println("prev");
    }

    public void render(PApplet app) {
        app.image(imgBack, X_COORD, Y_COORD);
        app.image(imgFront, X_COORD, Y_COORD);
    }

    public void setInstManager(InstrumentManager instManager) {
        this.instManager = instManager;
    }

}