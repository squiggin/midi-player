package piano;

import javax.sound.midi.MidiEvent;

import processing.core.PApplet;
import processing.core.PImage;

public class Block implements Drawable {
    private PImage img;
    private int x;
    private int y;
    private MidiEvent[] event;

    public Block(PImage img) {
        this.img = img;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public MidiEvent[] getEvent() {
        return event;
    }

    public void setEvent(MidiEvent[] event) {
        this.event = event;
    }

    public void render(PApplet app) {
        app.image(img, x, y);
    }
}