package piano.Instruments;

import org.junit.Test;

import piano.AudioManager;
import piano.InstrumentManager;
import piano.Buttons.InstrumentNext;
import piano.Buttons.InstrumentPrev;

import static org.junit.Assert.*;

public class InstrumentChangeTest {
    InstrumentManager instManager;
    InstrumentNext next;
    InstrumentPrev prev;
    InstrumentButton piano;
    InstrumentButton banjo;
    InstrumentButton marimba;
    InstrumentButton sax;
    public InstrumentChangeTest() {
        AudioManager audioTrack = new AudioManager();
        
        instManager = audioTrack.setup( null, null, null, null, null);
        piano = instManager.getPiano();
        banjo = instManager.getBanjo();
        marimba = instManager.getMarimba();
        sax = instManager.getSax();

        next = new InstrumentNext(null, null);
        prev = new InstrumentPrev(null, null);
        next.setInstManager(instManager);
        prev.setInstManager(instManager);
    }

    @Test
    public void nextAndPrevClickedTest() {
        int current = instManager.getCurrentIndex();
        
        while (current <= 3) {
            assertEquals(current, instManager.getCurrentIndex());
            next.tick();
            current += 1;
        }
        
        assertNotEquals(current, instManager.getCurrentIndex());
        assertEquals(3, instManager.getCurrentIndex());

        // Now for prev
        current = instManager.getCurrentIndex();
        
        while (current >= 0) {
            assertEquals(current, instManager.getCurrentIndex());
            prev.tick();
            current -= 1;
        }
        
        assertNotEquals(current, instManager.getCurrentIndex());
        assertEquals(0, instManager.getCurrentIndex());

        // And a bit of both
        current = instManager.getCurrentIndex();
        next.tick();
        prev.tick();
        assertEquals(instManager.getCurrentIndex(), current);

        next.tick();
        next.tick();
        next.tick();
        prev.tick();
        // 0 + 3 - 1 = 0 + 2
        assertEquals(current + 2, instManager.getCurrentIndex());
    }

    @Test
    public void instrumentIndexTest() {
        while (instManager.getCurrentIndex() != 0) {
            prev.tick();
        }

        assertEquals(piano.getInstrument(), instManager.getInstrument());
        next.tick();
        assertEquals(marimba.getInstrument(), instManager.getInstrument());
        next.tick();
        assertEquals(banjo.getInstrument(), instManager.getInstrument());
        next.tick();
        assertEquals(sax.getInstrument(), instManager.getInstrument());
    }

}