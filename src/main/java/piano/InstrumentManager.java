package piano;

import javax.sound.midi.Instrument;
import javax.sound.midi.Synthesizer;

import piano.Instruments.*;
import processing.core.PApplet;
import processing.core.PImage;

/** InstrumentManager class that manages instrument switching and
 *  keeps track of the current instrument.
 */
public class InstrumentManager {

    private InstrumentButton[] instrumentButtons;
    private int currentIndex;
    private AudioManager audioTrack;
    private PianoButton piano;
    private MarimbaButton marimba;
    private BanjoButton banjo;
    private SaxButton sax;

    /**
     * 
     * @param audioTrack    AudioManager object associated with the session
     * @param synth         Synthesizer associated with the system
     * @param imgBack       The button background image
     * @param bFront        The front image for banjo
     * @param pFront        The front image for piano
     * @param mFront        The front image for marimba
     * @param sFront        The front image for saxophone
     */
    public InstrumentManager(AudioManager audioTrack, Synthesizer synth,
        PImage imgBack, PImage bFront, PImage pFront, PImage mFront, PImage sFront) {
        this.audioTrack = audioTrack;
        piano = new PianoButton(imgBack, pFront);
        marimba = new MarimbaButton(imgBack, mFront);
        banjo = new BanjoButton(imgBack, bFront);
        sax = new SaxButton(imgBack, sFront);

        // Get and assign the respective instruments to the intrument buttons
        for (Instrument in: synth.getAvailableInstruments()) {
            switch(in.getName().trim()) {
                case "Piano 1":
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
    
    /** Switch to the next instrument. */
    public void next() {
        currentIndex = (currentIndex + 1) % 4;
        audioTrack.updateInstrument();
    }

    /** Switch to the previous instrument. */
    public void prev() {
        if (currentIndex > 0) {
            currentIndex--;
        } else {
            currentIndex = 3;
        }
        audioTrack.updateInstrument();
    }

    
    /** Getter for the current Instrument
     * 
     * @return Instrument   The Instrument of the current InstrumentButton
     */
    public Instrument getInstrument() {
        return instrumentButtons[currentIndex].getInstrument();
    }

    
    /** Draws the current instrument button's image
     *  on the PApplet object given as a parameter.
     * 
     * @param app   PApplet object to render on
     */
    public void render(PApplet app) {
        instrumentButtons[currentIndex].render(app);
    }

    
    /** 
     * @return int Current Instrument index
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    
    /** Sets and updates the current Instrument index
     * 
     * @param index  The index to set the current index to
     */
    public void setCurrentIndex(int index) {
        this.currentIndex = index;
        audioTrack.updateInstrument();
    }

    
    /** 
     * @return PianoButton
     */
    public PianoButton getPiano() {
        return piano;
    }

    
    /** 
     * @return MarimbaButton
     */
    public MarimbaButton getMarimba() {
        return marimba;
    }

    
    /** 
     * @return BanjoButton
     */
    public BanjoButton getBanjo() {
        return banjo;
    }

    
    /** 
     * @return SaxButton
     */
    public SaxButton getSax() {
        return sax;
    }

}