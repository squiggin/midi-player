package piano;

import processing.core.PApplet;
import processing.core.PImage;

public class App extends PApplet {

    private PlayButton play;
    private StopButton stop;
    private MidiWrite write;
    private Pointer point;
    private Grid grid;
    private AudioManager audioTrack;

    PImage[] images;

    public App() {
        // Initialise variables here
        audioTrack = new AudioManager();
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
        play = new PlayButton(this.images[Asset.ButtonBack.get()]);
        stop = new StopButton(this.images[Asset.ButtonBack.get()]);
        point = new Pointer(this.images[Asset.Pointer.get()]);
        grid = new Grid(this.images[Asset.Grid.get()], this.images[Asset.Block.get()]);
        write = new MidiWrite(this.audioTrack);

        // Single render

        this.image(this.images[Asset.Keyboard.get()], 0, 75);
    }

    public void mouseClicked() {
        if(this.mouseX >= PlayButton.X_COORD &&
                this.mouseX <= PlayButton.X_COORD + 40 &&
                this.mouseY >= PlayButton.Y_COORD &&
                this.mouseY <= PlayButton.Y_COORD + 40) {
            play.tick(this.point, this.audioTrack);
        } else if(this.mouseX >= StopButton.X_COORD &&
                this.mouseX <= StopButton.X_COORD + 40 &&
                this.mouseY >= StopButton.Y_COORD &&
                this.mouseY <= StopButton.Y_COORD + 40) {
            stop.tick(this.point, this.play, this.audioTrack);
        } else if(this.mouseX >= write.X_COORD &&
                this.mouseX <= write.X_COORD + 40 &&
                this.mouseY >= write.Y_COORD &&
                this.mouseY <= write.Y_COORD + 40) {
            write.tick();
        } else if(this.mouseX >= Grid.X_COORD &&
                this.mouseY >= Grid.Y_COORD) {
            grid.tick(this.mouseX, this.mouseY, this.audioTrack);
        }
    }

    public void draw() {
        // Draw your program here
        // this.image(this.images[Asset.Grid.get()], 60, 75);
        grid.render(this);
        this.image(this.images[Asset.MidBanner.get()], 0, 0);
        this.image(this.images[Asset.Banner.get()], 0, 0);
        stop.render(this);
        play.render(this);
        write.render(this);
        point.tick(this, this.audioTrack);
        point.render(this);
    }

    public static void main(String[] args) {
        // Don't touch this
        PApplet.main("piano.App");
    }
}
