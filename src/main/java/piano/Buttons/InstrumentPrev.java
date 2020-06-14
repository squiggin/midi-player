package piano.Buttons;

import piano.Button;
import piano.InstrumentManager;
import processing.core.PApplet;
import processing.core.PImage;

/** InstrumentPrev class. */
public class InstrumentPrev extends Button {
    
    private PImage imgBack;
    private PImage imgFront;
    private InstrumentManager instManager;
    public final int X_COORD = 275;
    public final int Y_COORD = 5;

    /**
     * 
     * @param imgBack   PImage object containing the image for the button background
     * @param imgFront  PImage object containing the front Instrument Prev image
     */
    public InstrumentPrev(PImage imgBack, PImage imgFront) {
        this.imgBack = imgBack;
        this.imgFront = imgFront;
    }

    
    /**
     * 
     * @return  The x coordinate and y coordinate associated with the button,
     *          wrapped in an array.
     */
    @Override
    public int[] values() {
        return new int[] {X_COORD, Y_COORD};
    }
    
    /** Changes the instrument to the previous one. */
    public void tick() {
        instManager.prev();
    }

    
    /** Draws button image on the PApplet object
     * @param app
     */
    public void render(PApplet app) {
        app.image(imgBack, X_COORD, Y_COORD);
        app.image(imgFront, X_COORD, Y_COORD);
    }

    
    /** 
     * @param instManager   InstrumentManager object associated with the session
     */
    public void setInstManager(InstrumentManager instManager) {
        this.instManager = instManager;
    }

}