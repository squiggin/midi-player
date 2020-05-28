package piano.Buttons;

import piano.AudioManager;
import piano.Button;
import piano.Pointer;
import processing.core.PApplet;
import processing.core.PImage;

public class PlayButton extends Button {

    private PImage img;
    private boolean play;
    final int X_COORD = 5;
    final int Y_COORD = 5;
    
    public PlayButton(PImage img) {
        this.img = img;
        play = false;
    }

    
    public int[] values() {
        return new int[] {X_COORD, Y_COORD};
    }

    private void togglePlay(Pointer pointer, AudioManager audioTrack) {
        pointer.togglePlay();
        play = !play;
        if(play) {
            audioTrack.startPlaying();
        } else {
            audioTrack.stopPlaying();
        }
    }

    public void stop(AudioManager audioTrack) {
        play = false;
        audioTrack.stopPlaying();
    }

    public void tick(Pointer pointer, AudioManager audioTrack) {
        this.togglePlay(pointer, audioTrack);
    }

    public void render(PApplet app) {
        app.image(img, X_COORD, Y_COORD);
        app.noStroke();
        app.fill(100);
        if(!play) {
            app.triangle(X_COORD + 8, Y_COORD + 5, X_COORD + 32, Y_COORD + 20, X_COORD + 8, Y_COORD + 35);
        } else {
            app.rect(X_COORD + 8, Y_COORD + 5, 8, 30);
            app.rect(X_COORD + 24, Y_COORD + 5, 8, 30);
        }
    }
}