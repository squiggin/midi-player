package piano;

import javax.sound.midi.MidiEvent;

import processing.core.PApplet;
import processing.core.PImage;

/** Block class for the blocks on the grid. */
public class Block implements Drawable {
    private PImage img;
    private int x;
    private int y;
    private MidiEvent[] event;

    /** 
     * @param img Image to draw onto the PApplet.
     */
    public Block(PImage img) {
        this.img = img;
    }

    
    /** Setter for the block's x coordinate.
     * @param x
     */
    void setX(int x) {
        this.x = x;
    }

    
    /** Setter for the block's y coordinate.
     * @param y
     */
    void setY(int y) {
        this.y = y;
    }

    
    /** Getter for the MidiEvents associated with the block.
     * 
     * @return MidiEvent[]  The noteOn and noteOff events associated with the block,
     *                      wrapped in an array.
     */
    public MidiEvent[] getEvent() {
        return event;
    }

    
    /** Setter for the MidiEvents associated with the block.
     * 
     * @param event The noteOn and noteOff events to be associated with the block,
     *              wrapped in an array.
     */
    void setEvent(MidiEvent[] event) {
        this.event = event;
    }

    
    /** Draws the image on the PApplet object given as a parameter.
     * 
     * @param app   PApplet object to render on.
     */
    public void render(PApplet app) {
        app.image(img, x, y);
    }
}