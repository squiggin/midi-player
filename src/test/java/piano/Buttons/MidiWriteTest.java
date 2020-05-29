package piano.Buttons;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import piano.AudioManager;

public class MidiWriteTest {
    MidiWrite write;
    AudioManager audioTrack;

    public MidiWriteTest() {
        assertNotNull(audioTrack = new AudioManager());
        assertNotNull(write = new MidiWrite(audioTrack));
    }

    @Test
    public void midiWriteTest() {
        audioTrack.addNote(70, 0);
        write.tick();
        audioTrack.clear();
        ArrayList<Byte> emptySequence = new ArrayList<>();
        try {
            FileInputStream readMidi = new FileInputStream("Out.midi");
            byte readByte;
            while ((readByte = (byte) readMidi.read()) != -1) {
                emptySequence.add(readByte);
            }
            readMidi.close();
            audioTrack.addNote(60, 0);
            audioTrack.addNote(57, 2);
            audioTrack.addNote(61, 3);
            audioTrack.addNote(57, 4);

            write.tick();

            ArrayList<Byte> newSequence = new ArrayList<>();
            readMidi = new FileInputStream("Out.midi");
            while ((readByte = (byte) readMidi.read()) != -1) {
                newSequence.add(readByte);
            }
            readMidi.close();

            assertNotEquals(emptySequence.size(), newSequence.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}