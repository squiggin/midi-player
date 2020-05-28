package piano;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

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
    private int currentTick;
    private boolean isPlaying;
    private List<Integer> currentlyActive;

    public AudioManager() {
        try {
            synth = MidiSystem.getSynthesizer();
            channel = synth.getChannels()[0];

            sequencer = MidiSystem.getSequencer();
            sequencer.open();

            sequence = new Sequence(Sequence.PPQ, 4);
            sequencer.setTempoInBPM(120);
            track = sequence.createTrack();
            sequencer.setSequence(sequence);

            currentTick = 0;
            isPlaying = false;
            currentlyActive = new LinkedList<>();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setup(PImage imgBack, PImage bFront, PImage pFront, PImage mFront, PImage sFront) {
        instrumentManager = new InstrumentManager(synth, imgBack, bFront, pFront, mFront, sFront);
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

    /*private void addAllNotes() {
        sequence.deleteTrack(track);
        track = sequence.createTrack();
        for (HashMap<Integer, Block> row : grid.blocks.values()) {
            for (Block block : row.values()) {
                track.add(block.getEvent()[0]);
                track.add(block.getEvent()[1]);
            }
        }
    }*/

    public MidiEvent[] addNote(int noteNumber, int tick) {
        MidiEvent event1 = null;
        MidiEvent event2 = null;
        try {
            ShortMessage message = new ShortMessage(144, 1, noteNumber, 90);
            event1 = new MidiEvent(message, tick);
            message = new ShortMessage(128, 1, noteNumber, 90);
            event2 = new MidiEvent(message, tick + 4);
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
        isPlaying = false;
    }

    public void run() {
        if (isPlaying) {
            if(pointer.getCurrentTick() != this.currentTick) {
                if (grid.blocks.get(pointer.getCurrentTick()/2) != null) {
                    System.out.println(grid.blocks.get(pointer.getCurrentTick()/2));
                    for (int note: currentlyActive) {
                        channel.noteOff(note);
                    }
                    currentlyActive.clear();
                    for (int note: grid.blocks.get(pointer.getCurrentTick()/2).keySet()) {
                        currentlyActive.add(note);
                        channel.noteOn(note, 90);
                    }
                }
                currentTick = pointer.getCurrentTick()/2;
            }
        }
    }

    public void reset() {
        isPlaying = true;
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