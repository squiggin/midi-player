package piano.Instruments;

import javax.sound.midi.Instrument;

import processing.core.PApplet;
import processing.core.PImage;

public class MarimbaButton extends InstrumentButton {
    private Instrument instrument;
    
    PImage imgBack;
    PImage imgFront;

    public MarimbaButton(PImage imgBack, PImage imgFront) {
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

    
    /** Draws button image on the PApplet object
     * @param app
     */
    public void render(PApplet app) {
        app.image(imgBack, X_COORD, Y_COORD);
        app.image(imgFront, X_COORD, Y_COORD);
    }

    
    /** 
     * @return Instrument
     */
    public Instrument getInstrument() {
        return instrument;
    }

    
    /** 
     * @param instrument    Instrument object to set self's instrument too
     */
    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
}