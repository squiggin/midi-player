package piano;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.Instrument;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import processing.core.PApplet;
import processing.core.PImage;

public class AudioManager {
    private Sequencer sequencer;
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

    public AudioManager() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channel = synth.getChannels()[0];

            sequencer = MidiSystem.getSequencer();
            sequencer.open();

            sequence = new Sequence(Sequence.PPQ, 4);
            sequencer.setTempoInBPM(120);
            track = sequence.createTrack();
            sequencer.setSequence(sequence);
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

    public InstrumentManager setup(PImage imgBack, PImage bFront, PImage pFront, PImage mFront, PImage sFront) {
        instrumentManager = new InstrumentManager(this, synth, imgBack, bFront, pFront, mFront, sFront);
        updateInstrument();
        return instrumentManager;
    }

    public void updateInstrument() {
        Instrument current = instrumentManager.getInstrument();
        setInstrument(current);
    }

    public void setInstrument(Instrument current) {
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

    public void clear() {
        try {
            sequence = new Sequence(Sequence.PPQ, 4);
            sequencer.setTempoInBPM(120);
            track = sequence.createTrack();
            sequencer.setSequence(sequence);
            isPlaying = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void addAllNotes() {
        sequence.deleteTrack(track);
        track = sequence.createTrack();
        for (HashMap<Integer, Block> row : grid.blocks.values()) {
            for (Block block : row.values()) {
                track.add(block.getEvent()[0]);
                track.add(block.getEvent()[1]);
            }
        }
    }

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

    public void removeNote(MidiEvent[] event) {
        track.remove(event[0]);
        track.remove(event[1]);
    }

    public void startPlaying() {
        isPlaying = true;
    }

    public void stopPlaying() {
        this.currentTick = -1;
        channel.allNotesOff();
        isPlaying = false;
    }

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

    public void reset() {
        isPlaying = true;
        this.currentTick = -1;
    }

    public static void main(String[] args) {
        AudioManager a = new AudioManager();
        a.addNote(60, 0);
        a.addNote(61, 4);
        a.addNote(60, 8);
        a.addNote(63, 12);
        try {
            a.sequencer.setSequence(a.sequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
        a.sequencer.setTempoInBPM(120);
        a.sequencer.start(); 

        while (true) { 
            // Exit the program when sequencer has stopped playing. 
            if (!a.sequencer.isRunning()) { 
                a.sequencer.close(); 
                break; 
            } 
        }
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void setPointer(Pointer pointer) {
        this.pointer = pointer;
    }
}