package piano;

import piano.Buttons.MidiWrite;
import piano.Buttons.PlayButton;
import piano.Buttons.ResetButton;
import piano.Buttons.StopButton;

public class SessionManager {
    App app;

    private PlayButton play;
    private StopButton stop;
    private MidiWrite write;
    private ResetButton reset;
    private Pointer point;
    private Grid grid;
    private AudioManager audioTrack;

    SessionManager(App app) {
        this.app = app;
        audioTrack = new AudioManager();
    }

    void setup() {
        
        play = new PlayButton(app.images[Asset.ButtonBack.get()]);
        stop = new StopButton(app.images[Asset.ButtonBack.get()]);
        reset = new ResetButton(app.images[Asset.ButtonBack.get()],
                                app.images[Asset.Reset.get()]);
        point = new Pointer(app.images[Asset.Pointer.get()], audioTrack);
        grid = new Grid(app.images[Asset.Grid.get()], app.images[Asset.Block.get()], audioTrack);
        write = new MidiWrite(audioTrack);

        audioTrack.setPointer(point);
        audioTrack.setGrid(grid);

        audioTrack.setup(app.images[Asset.ButtonBack.get()],
                         app.images[Asset.Banjo.get()],
                         app.images[Asset.Piano.get()],
                         app.images[Asset.Marimba.get()],
                         app.images[Asset.Saxophone.get()]);

        // Single render

        app.image(app.images[Asset.Keyboard.get()], 0, 75);
    }

    private boolean isButtonClicked(Button button, int sizeX, int sizeY, int mouseX, int mouseY) {
        return(mouseX >= button.X_COORD &&
        mouseX <= button.X_COORD + sizeX &&
        mouseY >= button.Y_COORD &&
        mouseY <= button.Y_COORD + sizeY);
    }

    private boolean isButtonClicked(Button button, int size, int mouseX, int mouseY) {
        return isButtonClicked(button, size, size, mouseX, mouseY);
    }

    public void clickHandler(int mouseX, int mouseY) {
        try {
        if(isButtonClicked(play, 40, mouseX, mouseY)) {
            play.tick(this.point, this.audioTrack);
        } else if(isButtonClicked(stop, 40, mouseX, mouseY)) {
            stop.tick(this.point, this.play, this.audioTrack);
        } else if(isButtonClicked(write, 70, 40, mouseX, mouseY)) {
            write.tick();
        } else if(isButtonClicked(reset, 40, mouseX, mouseY)) {
            reset.tick(grid, stop, point, play, audioTrack);
        } else if(mouseX >= grid.X_COORD &&
                mouseY >= grid.Y_COORD) {
            grid.tick(mouseX, mouseY);
        }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void render() {
        grid.render(app);
        app.image(app.images[Asset.MidBanner.get()], 0, 0);
        app.image(app.images[Asset.Banner.get()], 0, 0);
        stop.render(app);
        play.render(app);
        reset.render(app);
        write.render(app);
        point.tick(app);
        point.render(app);
        audioTrack.run();
    }

}