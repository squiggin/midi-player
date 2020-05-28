package piano;

import processing.core.PApplet;
import processing.core.PImage;

public class App extends PApplet {

    private SessionManager session; 
    PImage[] images;

    public App() {
        // Initialise variables here
        session = new SessionManager(this);
    }

    public void settings() {
        // Don't touch
        size(540, 335);
    }

    public void setup() {
        frameRate(60);
        // Load images here

        Asset[] assetValues = Asset.values();

        images = new PImage[assetValues.length];
        int i = 0;
        for(Asset asset: assetValues) {
            images[i] = this.loadImage(asset.getPath());
            i++;
        }

        session.setup();

    }

    public void mouseClicked() {
        session.clickHandler(this.mouseX, this.mouseY);
    }

    public void draw() {
        // Draw your program here
        session.render();
    }

    public static void main(String[] args) {
        // Don't touch this
        PApplet.main("piano.App");
    }
}