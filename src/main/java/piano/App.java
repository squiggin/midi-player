package piano;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * The App class. Sets up and launches the main loop. 
 */
public class App extends PApplet {

    private SessionManager session; 
    PImage[] images;

    /** Constructor for the App class. Initialises a SessionManager object. */
    public App() {
        // Initialise variables here
        session = new SessionManager(this);
    }

    /** PApplet settings method */
    public void settings() {
        // Don't touch
        size(540, 335);
    }

    /** PApplet setup method */
    public void setup() {
        frameRate(60);
        // Load images here

        Asset[] assetValues = Asset.values();

        // Loading all images mentioned in enum Asset into the array images.
        images = new PImage[assetValues.length];
        int i = 0;
        for(Asset asset: assetValues) {
            images[i] = this.loadImage(asset.getPath());
            i++;
        }

        // Setting the SessionManager object up
        session.setup();

    }

    public void mouseClicked() {
        session.clickHandler(this.mouseX, this.mouseY);
    }

    /** Main draw loop for the PApplet */
    public void draw() {
        // Draw your program here
        session.render();
    }

    
    /** App entry point.
     * @param args Public static void main args
     */
    public static void main(String[] args) {
        // Don't touch this
        PApplet.main("piano.App");
    }

    
    /** Getter for SessionManager object.
     * For testing.
     * @return SessionManager object.
     */
    public SessionManager getSession() {
        return session;
    }
}