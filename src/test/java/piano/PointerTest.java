package piano;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PointerTest {
    Pointer pointer;
    AudioManager audioTrack;

    public PointerTest() {
        audioTrack = new AudioManager();
        pointer = new Pointer(null, audioTrack);
    }

    @Test
    public void pointerTickTest() {
        pointer.tick();
        assertEquals(0, pointer.getCurrentTick());
        for (int i = 0; i < 60; i++) {
            pointer.tick();
        }
        assertEquals(0, pointer.getCurrentTick());
        pointer.togglePlay();
        assertEquals(0, pointer.getCurrentTick());

        for (int i = 0; i < 15; i++) {
            pointer.tick();
        }
        assertEquals(2, pointer.getCurrentTick());
        
        // After almost completing an entire loop
        for (int i = 0; i < 479; i++) {
            pointer.tick();
        }
        assertEquals(1, pointer.getCurrentTick());
        pointer.tick();
        assertEquals(2, pointer.getCurrentTick());

    }

}