package piano;

import javax.sound.midi.Instrument;
import javax.sound.midi.Synthesizer;

import piano.Instruments.*;
import processing.core.PImage;

public class InstrumentManager {
    PianoButton piano;
    MarimbaButton marimba;
    BanjoButton banjo;
    SaxButton sax;

    public InstrumentManager(Synthesizer synth,
        PImage imgBack, PImage bFront, PImage pFront, PImage mFront, PImage sFront) {
        piano = new PianoButton(imgBack, pFront);
        marimba = new MarimbaButton(imgBack, mFront);
        banjo = new BanjoButton(imgBack, bFront);
        sax = new SaxButton(imgBack, sFront);
        for (Instrument in: synth.getAvailableInstruments()) {
            switch(in.getName().trim()) {
                case "Piano 2":
                    if (piano.getInstrument() == null) {
                        piano.setInstrument(in);
                    }
                    break;

                case "Marimba":
                    if (marimba.getInstrument() == null) {
                        marimba.setInstrument(in);
                    }
                    break;
                
                case "Banjo":
                    if (banjo.getInstrument() == null) {
                        banjo.setInstrument(in);
                    }
                    break;
                
                case "Alto Sax":
                    if (sax.getInstrument() == null) {
                        sax.setInstrument(in);
                    }
                    break;
                
                default:
                    break;
            }
        }
        if (piano.getInstrument() == null ||
            marimba.getInstrument() == null ||
            banjo.getInstrument() == null ||
            sax.getInstrument() == null) {
            System.out.println("An instrument was not found.");
        }

    }

}