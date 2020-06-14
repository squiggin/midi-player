package piano;

import java.util.HashMap;

import javax.sound.midi.MidiEvent;

import processing.core.PApplet;
import processing.core.PImage;

/** Grid class.
 *  Implements the Drawable interface.
 */
public class Grid implements Drawable {
    private PImage img;
    private PImage blockImg;
    private AudioManager audioTrack;
    /**
     * HashMap mapping Integer to HashMaps that map Integers to Blocks
     * Maps the tick positions to HashMaps which, in turn, map the note position
     * to Block objects at those coordinates.
     */
    public HashMap<Integer, HashMap<Integer, Block>> blocks;
    final int X_COORD = 60;
    final int Y_COORD = 75;

    /**
     * 
     * @param img           PImage object containing the grid image to display.
     * @param blockImg      PImage object containing the image for Block objects.
     * @param audioTrack    AudioManager object audioTrack being used by the session.
     */
    public Grid(PImage img, PImage blockImg, AudioManager audioTrack) {
        this.img = img;
        this.blockImg = blockImg;
        this.audioTrack = audioTrack;
        blocks = new HashMap<Integer, HashMap<Integer, Block>>();
    }

    /** Clears all the blocks in the grid. */
    public void clear() {
        blocks = new HashMap<Integer, HashMap<Integer, Block>>();
    }

    
    /** Handles click at coordinates given as parameters. 
     * Adds/removes notes as required.
     * 
     * @param clickX    The x coordinate of the click.
     * @param clickY    The y coordinate of the click.
     */
    public void tick(int clickX, int clickY) {

        int[] blockKey = new int[] {(clickX - 60)/15, (clickY - 75)/20};
        addRemoveNotes(blockKey);
    }

    
    /** If there is a block at the given position, removes it. Else, adds a new block there.
     * 
     * @param blockKey  Array wrapping the tick position and the note position in that order.
     */
    public void addRemoveNotes(int[] blockKey) {
        
        // If there is a block at the given position, remove it
        if(blocks.containsKey(blockKey[0])) {
            if(blocks.get(blockKey[0]).containsKey(blockKey[1])) {
                Block removeBlock = blocks.get(blockKey[0]).remove(blockKey[1]);
                // Remove the block's associated notes too
                audioTrack.removeNote(removeBlock.getEvent());
                return;
            }
        }

        Block newBlock = new Block(this.blockImg);    
        // Create and get noteOn and noteOff events
        MidiEvent[] events = audioTrack.addNote(60 + (12 - blockKey[1]), blockKey[0]*2);
        // Create and set up a new block
        newBlock.setX(X_COORD + blockKey[0]*15 + 1);
        newBlock.setY(Y_COORD + blockKey[1]*20 + 1);
        newBlock.setEvent(events);

        // Add it to blocks
        if(!blocks.containsKey(blockKey[0])) {
            blocks.put(blockKey[0], new HashMap<Integer, Block>());
        }
        blocks.get(blockKey[0]).put(blockKey[1], newBlock);
    }

    
    /** Draws the grid's image and all blocks on the PApplet object given as a parameter.
     * 
     * @param app   PApplet object to render on.
     */
    public void render(PApplet app) {
        app.image(img, X_COORD, Y_COORD);
        for(HashMap<Integer, Block> column: blocks.values()) {
            for(Block block: column.values())
                block.render(app);
        }
    }

}