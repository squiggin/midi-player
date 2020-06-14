package piano;

import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.Instrument;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import processing.core.PApplet;
import processing.core.PImage;

/** AudioManager class for managing the sequence, synthesizer and all playback related stuff. */
public class AudioManager {
    private Sequence sequence;
    private Synthesizer synth;
    private MidiChannel channel;
    private Track track;
    private Pointer pointer;
    private Grid grid;
    private InstrumentManager instrumentManager;
    private MidiEvent currentInstrumentEvent;
    private MidiEvent tempoEvent;
    private int currentTick;
    private boolean isPlaying;
    private List<Integer> currentlyActive;

    /**
     * Constructor for AudioManager.
     * Sets up the synthesizer, playback channel and other variables.
     */
    public AudioManager() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channel = synth.getChannels()[0];

            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            setTempoTo120BPM();
            currentInstrumentEvent = null;

            currentTick = -1;
            isPlaying = false;
            currentlyActive = new LinkedList<>();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTempoTo120BPM() {
        // Byte array conatining the tempo in microseconds per quarter note
        // 120 bpm corresponds to 500,000 microseconds per quarter note
        // Also, quarter note is the same as a beat
        // Took me long enough to realise that
        // Hexadecimal(500,000) = 0x07A120

        byte[] data = new byte[] { (byte) 0x07, (byte) 0xA1, (byte) 0x20 };
        try {
            // MetaMessage type 0x51, or 81 in decimal, is set tempo
            // And the size is 3 because we're giving three bytes
            MetaMessage setTempo = new MetaMessage(81, data, 3);
            tempoEvent = new MidiEvent(setTempo, 0);
            track.add(tempoEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    /** Setup method. Initializes and returns an InstrumentManager object. 
     * Updates current instrument too.  
     * 
     * @param imgBack   Image for the button background.
     * @param bFront    Image for the banjo button front.
     * @param pFront    Image for the piano button front.
     * @param mFront    Image for the marimba button front.
     * @param sFront    Image for the saxophone button front.
     * @return InstrumentManager object.
     */
    public InstrumentManager setup(PImage imgBack, PImage bFront, PImage pFront, PImage mFront, PImage sFront) {
        instrumentManager = new InstrumentManager(this, synth, imgBack, bFront, pFront, mFront, sFront);
        updateInstrument();
        return instrumentManager;
    }

    /**
     * Gets the current instrument from the instrument manager and updates itself.
     */
    public void updateInstrument() {
        Instrument current = instrumentManager.getInstrument();
        setInstrument(current);
    }

    
    /** Sets the Instrument provided as the argument to the instrument of the sequence\
     *  and the channel.
     * @param current   The instrument to be set to the current instrument.
     */
    private void setInstrument(Instrument current) {
        if (currentInstrumentEvent != null) {
            track.remove(currentInstrumentEvent);
        }
        synth.loadInstrument(current);
        channel.programChange(current.getPatch().getBank(), current.getPatch().getProgram());

        // The following removal of events at tick 0 is to make the intrument change the first \
        // event of the sequence.
        List<MidiEvent> events = new LinkedList<>();
        try {
            try {
                // Keep removing events at tick 0 until IndexOutOfBoundsException encountered
                while (true) {
                    MidiEvent event = track.get(0);
                    track.remove(event);
                    events.add(event);
                }
            } catch (IndexOutOfBoundsException e) {
                ;
            }
            currentInstrumentEvent = new MidiEvent(
                new ShortMessage(ShortMessage.PROGRAM_CHANGE, 1, current.getPatch().getProgram(), 0),
                0);
            track.add(tempoEvent);
            track.add(currentInstrumentEvent);
            // Then add the events back after changing the instrument
            for (MidiEvent event: events) {
                if (event.equals(tempoEvent)) {
                    continue;
                }
                track.add(event);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /** Empties the sequence. */
    public void clear() {
        try {
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            isPlaying = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /** Adds a note of length 2 ticks corresponding to the given note number to the 
     *  sequence at the given tick.
     *  Returns the noteOn and noteOff events that were added, wrapped in an array. 
     *  
     * @param noteNumber    Note number of note to be added
     * @param tick          Tick position at which the note is to be added.
     * @return MidiEvent[]  The noteOn and noteOff events that were added, wrapped in an array
     */
    public MidiEvent[] addNote(int noteNumber, int tick) {
        MidiEvent event1 = null;
        MidiEvent event2 = null;
        try {
            ShortMessage message = new ShortMessage(144, 1, noteNumber, 90);
            event1 = new MidiEvent(message, tick);
            message = new ShortMessage(128, 1, noteNumber, 90);
            event2 = new MidiEvent(message, tick + 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        track.add(event1);
        track.add(event2);
        
        return new MidiEvent[] {event1, event2};
    }

    
    /** Removes the events given as arguments from the sequence. 
     * 
     * @param event     The noteOn and noteOff events to be removes, wrapped in an array
     */
    public void removeNote(MidiEvent[] event) {
        track.remove(event[0]);
        track.remove(event[1]);
    }

    /** Sets isPlaying to true. */
    public void startPlaying() {
        isPlaying = true;
    }

    /** Stops all playing notes. Sets isPlaying to false. */
    public void stopPlaying() {
        this.currentTick = -1;
        channel.allNotesOff();
        isPlaying = false;
    }

    
    /** Renders the instrument button. Plays notes if isPlaying is true. 
     * 
     * @param app   PApplet object
     */
    public void run(PApplet app) {
        instrumentManager.render(app);
        if (isPlaying) {
            if(pointer.getCurrentTick()/2 != this.currentTick) {
                for (int note: currentlyActive) {
                    channel.noteOff(note);
                }
                currentlyActive.clear();

                if (grid.blocks.get(pointer.getCurrentTick()/2) != null) {
                    for (int note: grid.blocks.get(pointer.getCurrentTick()/2).keySet()) {
                        currentlyActive.add(60 + (12 - note));
                        channel.noteOn(60 + (12 - note), 90);
                    }
                }
                currentTick = pointer.getCurrentTick()/2;
            }
        }
    }

    /** Sets isPlaying to true and reinitializes tick position to -1. */
    public void reset() {
        isPlaying = true;
        this.currentTick = -1;
    }

    
    /** Getter for the current Sequence.
     * 
     * @return The current Sequence.
     */
    public Sequence getSequence() {
        return sequence;
    }

    
    /** Sets grid to the Grid object given as an argument.
     * 
     * @param grid  The Grid object to set grid to.
     */
    void setGrid(Grid grid) {
        this.grid = grid;
    }

    
    /** Sets pointer to the Pointer object given as an argument.
     * 
     * @param pointer   The Pointer object to set pointer to.
     */
    public void setPointer(Pointer pointer) {
        this.pointer = pointer;
    }

    
    /** Getter for isPlaying.
     * 
     * @return boolean Whether sound playback is on or not.
     */
    public boolean isPlaying() {
        return isPlaying;
    }

}