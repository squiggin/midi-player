package piano;

import static org.junit.Assert.*;

import org.junit.Test;

public class GridTest {
    Grid grid;
    AudioManager audioTrack;

    public GridTest() {
        audioTrack = new AudioManager();
        grid = new Grid(null, null, audioTrack);
    }

    @Test
    public void gridClickedTest() {
        assertNull(grid.blocks.get(2));
        grid.tick(90, 220);
        assertNotNull(grid.blocks.get(2));
        assertNotNull(grid.blocks.get(2).get(7));

        grid.tick(93, 224);
        assertTrue(grid.blocks.get(2) == null ||
                   grid.blocks.get(2).get(7) == null);
    }
}