package piano;

import javax.sound.midi.InvalidMidiDataException;
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

    public AudioManager() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNote(int noteNumber, int tick) {
        MidiEvent event1 = null;
        MidiEvent event2 = null;
        try {
            ShortMessage message = new ShortMessage(144, 1, noteNumber, 90);
            event1 = new MidiEvent(message, tick);
            message = new ShortMessage(128, 1, noteNumber, 90);
            event2 = new MidiEvent(message, tick+4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        track.add(event1);
        track.add(event2);
    }

    public static void main(String[] args) {
        AudioManager a = new AudioManager();
        a.addNote(60, 0);
        a.addNote(61, 4);
        a.addNote(60, 8);
        a.addNote(63, 12);
        try {
            a.sequencer.setSequence(a.sequence);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        a.sequencer.setTempoInBPM(120);
        a.sequencer.start(); 

        while (true) { 
            // Exit the program when sequencer has stopped playing. 
            if (!a.sequencer.isRunning()) { 
                a.sequencer.close(); 
                return; 
            } 
        }
    }
}