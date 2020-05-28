package piano.Instruments;

import javax.sound.midi.Instrument;

import processing.core.PApplet;
import processing.core.PImage;

public class PianoButton extends InstrumentButton {
    private Instrument instrument;
    
    PImage imgBack;
    PImage imgFront;
    
    public PianoButton(PImage imgBack, PImage imgFront) {
        this.imgBack = imgBack;
        this.imgFront = imgFront;
    }

    @Override
    public int[] values() {
        return new int[] {X_COORD, Y_COORD};
    }

    public void render(PApplet app) {
        app.image(imgBack, X_COORD, Y_COORD);
        app.image(imgFront, X_COORD, Y_COORD);
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
}