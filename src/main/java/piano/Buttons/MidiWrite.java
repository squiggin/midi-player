package piano.Buttons;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import piano.AudioManager;
import piano.Button;
import processing.core.PApplet;
import processing.core.PConstants;

public class MidiWrite extends Button {
    public final int X_COORD = 440;
    public final int Y_COORD = 5;
    private AudioManager audioTrack;

    public MidiWrite(AudioManager audioTrack) {
        this.audioTrack = audioTrack;
    }
    
    /**
     * 
     * @return  The x coordinate and y coordinate associated with the button,
     *          wrapped in an array.
     */
    public int[] values() {
        return new int[] {X_COORD, Y_COORD};
    }

    /** Writes the current sequence to the Out.midi file in the project root directory. */
    public void tick() {
        File midiFile = new File("Out.midi");
        System.out.println(midiFile.getAbsolutePath());
        Sequence writeSequence = audioTrack.getSequence();
        int[] midiFileTypes = MidiSystem.getMidiFileTypes(writeSequence);
        try {
            MidiSystem.write(writeSequence, midiFileTypes[midiFileTypes.length - 1], midiFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** Draws button image on the PApplet object
     * @param app
     */
    public void render(PApplet app) {
        app.noStroke();
        app.fill(130, 130, 200);
        app.rect(X_COORD, Y_COORD, 70, 40);
        app.fill(240);
        app.textSize(20);
        app.textAlign(PConstants.CENTER, PConstants.CENTER);
        app.text("Export", X_COORD + 35, Y_COORD + 7);
        app.textSize(18);
        app.text("MIDI", X_COORD + 35, Y_COORD + 27);
    }

}