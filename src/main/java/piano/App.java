package piano;

import piano.Buttons.MidiWrite;
import piano.Buttons.PlayButton;
import piano.Buttons.ResetButton;
import piano.Buttons.StopButton;
import processing.core.PApplet;
import processing.core.PImage;

public class App extends PApplet {

    private PlayButton play;
    private StopButton stop;
    private MidiWrite write;
    private ResetButton reset;
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
        reset = new ResetButton(this.images[Asset.ButtonBack.get()],
                                this.images[Asset.Reset.get()]);
        point = new Pointer(this.images[Asset.Pointer.get()], audioTrack);
        grid = new Grid(this.images[Asset.Grid.get()], this.images[Asset.Block.get()], audioTrack);
        write = new MidiWrite(this.audioTrack);

        // Single render

        this.image(this.images[Asset.Keyboard.get()], 0, 75);
    }

    private boolean isButtonClicked(Button button, int sizeX, int sizeY) {
        return(this.mouseX >= button.X_COORD &&
        this.mouseX <= button.X_COORD + sizeX &&
        this.mouseY >= button.Y_COORD &&
        this.mouseY <= button.Y_COORD + sizeY);
    }

    private boolean isButtonClicked(Button button, int size) {
        return isButtonClicked(button, size, size);
    }

    public void mouseClicked() {
        if(isButtonClicked(play, 40)) {
            play.tick(this.point, this.audioTrack);
        } else if(isButtonClicked(stop, 40)) {
            stop.tick(this.point, this.play, this.audioTrack);
        } else if(isButtonClicked(write, 70, 40)) {
            write.tick();
        } else if(isButtonClicked(reset, 40)) {
            reset.tick(grid, stop, point, play, audioTrack);
        } else if(this.mouseX >= grid.X_COORD &&
                this.mouseY >= grid.Y_COORD) {
            grid.tick(this.mouseX, this.mouseY);
        }
    }

    public void draw() {
        // Draw your program here
        grid.render(this);
        this.image(this.images[Asset.MidBanner.get()], 0, 0);
        this.image(this.images[Asset.Banner.get()], 0, 0);
        stop.render(this);
        play.render(this);
        reset.render(this);
        write.render(this);
        point.tick(this);
        point.render(this);
        audioTrack.run();
    }

    public static void main(String[] args) {
        // Don't touch this
        PApplet.main("piano.App");
    }
}
