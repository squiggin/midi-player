package piano.Buttons;

import org.junit.Test;
import static org.junit.Assert.*;

import piano.AudioManager;
import piano.Grid;
import piano.Pointer;

public class ResetTest {
    ResetButton reset;
    // Grid grid;

    public ResetTest() {
        reset = new ResetButton(null, null);
    }
    
    @Test
    public void resetClickedTest() {
        AudioManager audioTrack = new AudioManager();
        Grid grid = new Grid(null, null, audioTrack);
        Pointer pointer = new Pointer(null, audioTrack);
        PlayButton play = new PlayButton(null);
        StopButton stop = new StopButton(null);
        
        grid.addRemoveNotes(new int[] {0, 12});
        grid.addRemoveNotes(new int[] {1, 10});
        assertNotNull(grid.blocks);
        assertNotEquals(0, grid.blocks.size());

        play.tick(pointer, audioTrack);
        assertTrue(audioTrack.isPlaying());
        reset.tick(grid, stop, pointer, play, audioTrack);
        assertFalse(audioTrack.isPlaying());
        assertEquals(0, grid.blocks.size());
    }

}