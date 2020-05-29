package piano;

import javax.sound.midi.Instrument;
import javax.sound.midi.Synthesizer;

import piano.Instruments.*;
import processing.core.PApplet;
import processing.core.PImage;

public class InstrumentManager {

    private InstrumentButton[] instrumentButtons;
    private int currentIndex;
    private AudioManager audioTrack;
    private PianoButton piano;
    private MarimbaButton marimba;
    private BanjoButton banjo;
    private SaxButton sax;

    public InstrumentManager(AudioManager audioTrack, Synthesizer synth,
        PImage imgBack, PImage bFront, PImage pFront, PImage mFront, PImage sFront) {
        this.audioTrack = audioTrack;
        piano = new PianoButton(imgBack, pFront);
        marimba = new MarimbaButton(imgBack, mFront);
        banjo = new BanjoButton(imgBack, bFront);
        sax = new SaxButton(imgBack, sFront);
        for (Instrument in: synth.getAvailableInstruments()) {
            switch(in.getName().trim()) {
                case "Piano 3":
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

        instrumentButtons = new InstrumentButton[] {piano, marimba, banjo, sax};
        currentIndex = 0;
    }

    public void next() {
        if (currentIndex < 3)
            currentIndex++;
        audioTrack.updateInstrument();
    }

    public void prev() {
        if (currentIndex > 0)
            currentIndex--;
        audioTrack.updateInstrument();
    }

    public Instrument getInstrument() {
        return instrumentButtons[currentIndex].getInstrument();
    }

    public void render(PApplet app) {
        instrumentButtons[currentIndex].render(app);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
        audioTrack.updateInstrument();
    }

    public PianoButton getPiano() {
        return piano;
    }

    public MarimbaButton getMarimba() {
        return marimba;
    }

    public BanjoButton getBanjo() {
        return banjo;
    }

    public SaxButton getSax() {
        return sax;
    }

}