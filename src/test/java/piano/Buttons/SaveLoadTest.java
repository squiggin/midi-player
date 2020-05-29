package piano.Buttons;

import org.junit.Test;

import piano.AudioManager;
import piano.Block;
import piano.Grid;
import piano.InstrumentManager;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class SaveLoadTest {
    SaveButton save;
    LoadButton load;

    public SaveLoadTest() {
        save = new SaveButton(null, null);
        load = new LoadButton(null, null);
    }

    @Test
    public void saveLoadTest() {
        AudioManager audioTrack = new AudioManager();
        InstrumentManager instManager = audioTrack.setup(null, null, null, null, null);
        Grid grid = new Grid(null, null, audioTrack);
        grid.addRemoveNotes(new int[] { 0, 12 });
        grid.addRemoveNotes(new int[] { 1, 10 });
        grid.addRemoveNotes(new int[] { 3, 7 });
        HashMap<Integer, HashMap<Integer, Block>> initBlocks;
        initBlocks = grid.blocks;
        assertNotNull(initBlocks);

        for (int tick : initBlocks.keySet()) {
            assertEquals(initBlocks.get(tick), grid.blocks.get(tick));
        }

        save.tick(audioTrack, instManager, grid);
        grid.clear();
        assertNotEquals(grid.blocks, initBlocks);

        load.tick(instManager, grid);

        assertEquals(initBlocks.keySet(), grid.blocks.keySet());
        for (int tick : initBlocks.keySet()) {
            assertEquals(initBlocks.get(tick).keySet(), grid.blocks.get(tick).keySet());
        }
    }

    public void badFileLoadedTest() {
        AudioManager audioTrack = new AudioManager();
        InstrumentManager instManager = audioTrack.setup(null, null, null, null, null);
        Grid grid = new Grid(null, null, audioTrack);
        grid.addRemoveNotes(new int[] { 0, 12 });
        grid.addRemoveNotes(new int[] { 1, 10 });
        grid.addRemoveNotes(new int[] { 3, 7 });
        HashMap<Integer, HashMap<Integer, Block>> initBlocks;
        initBlocks = grid.blocks;
        assertNotNull(initBlocks);

        File saveFile = new File("savefile.sav");
        try {
            FileOutputStream saveStream = new FileOutputStream(saveFile);
            saveStream.write(9);
            saveStream.write(5);
            saveStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        load.tick(instManager, grid);
        assertEquals(initBlocks, grid.blocks);

    }
}