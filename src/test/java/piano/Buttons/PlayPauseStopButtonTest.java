package piano.Buttons;

import org.junit.Test;
import static org.junit.Assert.*;

import piano.AudioManager;
import piano.Pointer;

public class PlayPauseStopButtonTest {
    AudioManager audioTrack;
    PlayButton play;
    StopButton stop;
    Pointer pointer;

    public PlayPauseStopButtonTest() {
        audioTrack = new AudioManager();
        pointer = new Pointer(null, audioTrack);
        play = new PlayButton(null);
        stop = new StopButton(null);
    }

    @Test
    public void playPauseToggleTest() {
        play.tick(pointer, audioTrack);
        assertTrue(audioTrack.isPlaying());
        play.tick(pointer, audioTrack);
        assertFalse(audioTrack.isPlaying());
    }

    @Test
    public void stopTest() {
        stop.tick(pointer, play, audioTrack);
        assertFalse(audioTrack.isPlaying());
        assertEquals(pointer.INIT_X, pointer.getX_COORD());
    }

}