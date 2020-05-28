package piano;

import java.util.HashMap;

import javax.sound.midi.MidiEvent;

import processing.core.PApplet;
import processing.core.PImage;

public class Grid implements Drawable {
    private PImage img;
    private PImage blockImg;
    private AudioManager audioTrack;
    HashMap<Integer, HashMap<Integer, Block>> blocks;
    final int X_COORD = 60;
    final int Y_COORD = 75;

    public Grid(PImage img, PImage blockImg, AudioManager audioTrack) {
        this.img = img;
        this.blockImg = blockImg;
        this.audioTrack = audioTrack;
        this.audioTrack.setGrid(this);
        blocks = new HashMap<Integer, HashMap<Integer, Block>>();
    }

    public void clear() {
        blocks = new HashMap<Integer, HashMap<Integer, Block>>();
    }

    public void tick(int clickX, int clickY) {

        Integer[] blockKey = new Integer[] {(clickX - 60)/15, (clickY - 75)/20};
        if(blocks.containsKey(blockKey[0])) {
            if(blocks.get(blockKey[0]).containsKey(blockKey[1])) {
                Block removeBlock = blocks.get(blockKey[0]).remove(blockKey[1]);
                audioTrack.removeNote(removeBlock.getEvent());
                return;
            }
        }

        Block newBlock = new Block(this.blockImg);    
        MidiEvent[] events = audioTrack.addNote(60 + (12 - blockKey[1]), blockKey[0]*2);
        newBlock.setX(X_COORD + blockKey[0]*15 + 1);
        newBlock.setY(Y_COORD + blockKey[1]*20 + 1);
        newBlock.setEvent(events);
        if(!blocks.containsKey(blockKey[0])) {
            blocks.put(blockKey[0], new HashMap<Integer, Block>());
        }
        blocks.get(blockKey[0]).put(blockKey[1], newBlock);
    }

    public void render(PApplet app) {
        app.image(img, X_COORD, Y_COORD);
        for(HashMap<Integer, Block> column: blocks.values()) {
            for(Block block: column.values())
                block.render(app);
        }
    }

}