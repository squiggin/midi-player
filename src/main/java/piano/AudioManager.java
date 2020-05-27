package piano;

import java.util.HashMap;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class AudioManager {
    private Sequencer sequencer;
    private Sequence sequence;
    private Track track;
    private Pointer pointer;
    private boolean isPlaying;
    private Grid grid;

    public AudioManager() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            sequencer.setTempoInBPM(120);
            track = sequence.createTrack();
            sequencer.setSequence(sequence);
            isPlaying = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void clear() {
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

    private void addAllNotes() {
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
            event2 = new MidiEvent(message, tick + 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // if (!sequencer.isRunning() && isPlaying) {
        //     System.out.println("Error");
        //     addAllNotes();
        //     try {
        //         sequencer.setSequence(sequence);
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        //     System.out.println(track.size());
        // }
        track.add(event1);
        track.add(event2);
        try {
            sequencer.setSequence(sequence);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(pointer.getCurrentTick() != sequencer.getTickPosition())
            System.out.println("......................");
        // if(sequencer.getTickPosition() == 0)
        // System.out.println(sequencer.getTickPosition() + " " + sequencer.isRunning());
        // System.out.println(pointer.getCurrentTick());
        if(sequencer.getTickPosition() == 0)
        sequencer.setTickPosition(pointer.getCurrentTick());
        // System.out.println(sequencer.getTickPosition() + " " + sequencer.isRunning());
        if(!sequencer.isRunning() && isPlaying)
            sequencer.start();
        
        return new MidiEvent[] {event1, event2};
    }

    public void removeNote(MidiEvent[] event) {
        track.remove(event[0]);
        track.remove(event[1]);
    }

    public void startPlaying() {
        float tempo = sequencer.getTempoInBPM();
        try {
            sequencer.setSequence(sequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sequencer.setTempoInBPM(tempo);
        sequencer.start();
        isPlaying = true;
    }

    public void stopPlaying() {
        sequencer.stop();
        isPlaying = false;
    }

    public void reset() {
        this.stopPlaying();
        this.sequencer.setTickPosition(0);
        this.sequencer.start();
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