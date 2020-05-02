package piano;

import processing.core.PApplet;
import processing.core.PImage;

public class Block implements Drawable {
    private PImage img;
    private int x;
    private int y;

    public Block(PImage img) {
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void render(PApplet app) {
        app.image(img, x, y);
    }
}