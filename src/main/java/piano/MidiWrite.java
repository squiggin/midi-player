package piano;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import processing.core.PApplet;
import processing.core.PConstants;

public class MidiWrite implements Drawable {
    final int X_COORD = 100;
    final int Y_COORD = 5;
    private AudioManager audioTrack;

    public MidiWrite(AudioManager audioTrack) {
        this.audioTrack = audioTrack;
    }

    public void tick() {
        System.out.println("tick");
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

    public void render(PApplet app) {
        app.noStroke();
        app.fill(130, 130, 200);
        app.rect(X_COORD, Y_COORD, 70, 40);
        app.fill(240);
        app.textSize(22);
        app.textAlign(PConstants.CENTER, PConstants.CENTER);
        app.text("Write", X_COORD + 35, Y_COORD + 20);
    }

}