package piano;

import static org.junit.Assert.*;
import org.junit.Test;

import piano.Buttons.InstrumentNext;
import piano.Buttons.InstrumentPrev;
import piano.Buttons.LoadButton;
import piano.Buttons.MidiWrite;
import piano.Buttons.PlayButton;
import piano.Buttons.ResetButton;
import piano.Buttons.SaveButton;
import piano.Buttons.StopButton;
import processing.core.PApplet;

public class SessionManagerTest {

    @Test
    public void sessionClickTest() {
        
        App app = new App();
        PApplet.runSketch(new String[] {""}, app);
        app.setup();
        app.draw();
        SessionManager session = app.getSession();

        PlayButton play = session.getPlay();
        StopButton stop = session.getStop();
        MidiWrite write = session.getWrite();
        ResetButton reset = session.getReset();
        SaveButton save = session.getSave();
        LoadButton load = session.getLoad();
        InstrumentNext nextInst = session.getNextInst();
        InstrumentPrev prevInst = session.getPrevInst();
        Grid grid = session.getGrid();
        AudioManager audioTrack = session.getAudioTrack();
        InstrumentManager instManager = session.getInstManager();
        
        session.clickHandler(play.X_COORD + 1, play.Y_COORD + 1);
        assertTrue(audioTrack.isPlaying());
        session.clickHandler(stop.X_COORD + 1, stop.Y_COORD + 1);
        assertFalse(audioTrack.isPlaying());
        session.clickHandler(write.X_COORD + 1, write.Y_COORD + 1);
        session.clickHandler(play.X_COORD + 1, play.Y_COORD + 1);
        assertTrue(audioTrack.isPlaying());
        session.clickHandler(reset.X_COORD + 1, reset.Y_COORD + 1);
        assertFalse(audioTrack.isPlaying());
        assertEquals(0, grid.blocks.size());
        session.clickHandler(save.X_COORD + 1, save.Y_COORD + 1);
        session.clickHandler(load.X_COORD + 1, load.Y_COORD + 1);
        session.clickHandler(nextInst.X_COORD + 1, nextInst.Y_COORD + 1);
        assertEquals(1, instManager.getCurrentIndex());
        session.clickHandler(prevInst.X_COORD + 1, prevInst.Y_COORD + 1);
        assertEquals(0, instManager.getCurrentIndex());
        session.clickHandler(grid.X_COORD + 1, grid.Y_COORD + 1);
        assertNotNull(grid.blocks.get(0));
        assertNotNull(grid.blocks.get(0).get(0));
        
    }
}