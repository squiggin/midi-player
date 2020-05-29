package piano;

import piano.Buttons.InstrumentNext;
import piano.Buttons.InstrumentPrev;
import piano.Buttons.LoadButton;
import piano.Buttons.MidiWrite;
import piano.Buttons.PlayButton;
import piano.Buttons.ResetButton;
import piano.Buttons.SaveButton;
import piano.Buttons.StopButton;

public class SessionManager {
    App app;

    private PlayButton play;
    private StopButton stop;
    private MidiWrite write;
    private ResetButton reset;
    private SaveButton save;
    private LoadButton load;
    private InstrumentNext nextInst;
    private InstrumentPrev prevInst;
    private Pointer point;
    private Grid grid;
    private AudioManager audioTrack;
    private InstrumentManager instManager;

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
        save = new SaveButton(app.images[Asset.ButtonBack.get()],
                                app.images[Asset.Save.get()]);
        load = new LoadButton(app.images[Asset.ButtonBack.get()],
                                app.images[Asset.Load.get()]);
        nextInst = new InstrumentNext(app.images[Asset.ButtonBack.get()],
                                app.images[Asset.Next.get()]);
        prevInst = new InstrumentPrev(app.images[Asset.ButtonBack.get()],
                                app.images[Asset.Prev.get()]);

        audioTrack.setPointer(point);
        audioTrack.setGrid(grid);

        instManager = audioTrack.setup(app.images[Asset.ButtonBack.get()],
                         app.images[Asset.Banjo.get()],
                         app.images[Asset.Piano.get()],
                         app.images[Asset.Marimba.get()],
                         app.images[Asset.Saxophone.get()]);
        
        nextInst.setInstManager(instManager);
        prevInst.setInstManager(instManager);

        // Single render

        app.image(app.images[Asset.Keyboard.get()], 0, 75);
    }

    /**
     * 
     * @param button
     * @param sizeX
     * @param sizeY
     * @param mouseX
     * @param mouseY
     * @return
     */
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
        
        if(isButtonClicked(play, 40, mouseX, mouseY)) {
            play.tick(this.point, this.audioTrack);
        } else if(isButtonClicked(stop, 40, mouseX, mouseY)) {
            stop.tick(this.point, this.play, this.audioTrack);
        } else if(isButtonClicked(write, 70, 40, mouseX, mouseY)) {
            write.tick();
        } else if(isButtonClicked(reset, 40, mouseX, mouseY)) {
            reset.tick(grid, stop, point, play, audioTrack);
        } else if(isButtonClicked(save, 40, mouseX, mouseY)) {
            save.tick(audioTrack, instManager, grid);
        } else if(isButtonClicked(load, 40, mouseX, mouseY)) {
            load.tick(instManager, grid);
        } else if(isButtonClicked(nextInst, 40, mouseX, mouseY)) {
            nextInst.tick();
        } else if(isButtonClicked(prevInst, 40, mouseX, mouseY)) {
            prevInst.tick();
        } else if(mouseX >= grid.X_COORD &&
                mouseY >= grid.Y_COORD) {
            grid.tick(mouseX, mouseY);
        }
    }

    public void render() {
        grid.render(app);
        app.image(app.images[Asset.MidBanner.get()], 0, 0);
        app.image(app.images[Asset.Banner.get()], 0, 0);
        stop.render(app);
        play.render(app);
        reset.render(app);
        save.render(app);
        load.render(app);
        prevInst.render(app);
        nextInst.render(app);
        write.render(app);
        point.tick();
        point.render(app);
        audioTrack.run(app);
    }

    public InstrumentNext getNextInst() {
        return nextInst;
    }

    public InstrumentPrev getPrevInst() {
        return prevInst;
    }

    public PlayButton getPlay() {
        return play;
    }

    public StopButton getStop() {
        return stop;
    }

    public MidiWrite getWrite() {
        return write;
    }

    public ResetButton getReset() {
        return reset;
    }

    public SaveButton getSave() {
        return save;
    }

    public LoadButton getLoad() {
        return load;
    }

    public Grid getGrid() {
        return grid;
    }

    public AudioManager getAudioTrack() {
        return audioTrack;
    }

    public InstrumentManager getInstManager() {
        return instManager;
    }

}