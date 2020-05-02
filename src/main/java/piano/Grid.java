package piano;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class Grid implements Drawable {
    private PImage img;
    private PImage blockImg;
    private HashMap<Integer, HashMap<Integer, Block>> blocks;
    final static int X_COORD = 60;
    final static int Y_COORD = 75;
    public Grid(PImage img, PImage blockImg) {
        this.img = img;
        this.blockImg = blockImg;
        blocks = new HashMap<Integer, HashMap<Integer, Block>>();
    }

    public void tick(int clickX, int clickY) {

        Integer[] blockKey = new Integer[] {(clickX - 60)/15, (clickY - 75)/20};
        if(blocks.containsKey(blockKey[0])) {
            if(blocks.get(blockKey[0]).containsKey(blockKey[1])) {
                blocks.get(blockKey[0]).remove(blockKey[1]);
                return;
            }
        }
        Block newBlock = new Block(this.blockImg);
        newBlock.setX(X_COORD + blockKey[0]*15 + 1);
        newBlock.setY(Y_COORD + blockKey[1]*20 + 1);
        if(!blocks.containsKey(blockKey[0])) {
            blocks.put(blockKey[0], new HashMap<Integer, Block>());
        }
        blocks.get(blockKey[0]).put(blockKey[1], newBlock);
    }

    public void render(PApplet app) {
        app.image(img, X_COORD, Y_COORD);
        for(HashMap<Integer, Block> row: blocks.values()) {
            for(Block block: row.values())
                block.render(app);
        }
    }

}