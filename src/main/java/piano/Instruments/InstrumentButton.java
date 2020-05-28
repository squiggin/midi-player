package piano.Instruments;

import javax.sound.midi.Instrument;

import piano.Button;
import processing.core.PApplet;

public abstract class InstrumentButton extends Button {
    final int X_COORD = 320;
    final int Y_COORD = 5;
    
    public abstract int[] values();

    public abstract void render(PApplet app);

    public abstract void setInstrument(Instrument instrument);

    public abstract Instrument getInstrument();

}