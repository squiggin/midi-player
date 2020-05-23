package piano;

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
    private boolean isPlaying;

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

    public MidiEvent[] addNote(int noteNumber, int tick) {
        System.out.println(isPlaying);
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

}