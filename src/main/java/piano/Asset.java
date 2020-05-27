package piano;

public enum Asset {
    Banner(0, "banner.png"),
    Block(1, "block.png"),
    ButtonBack(2, "buttonBack.png"),
    Grid(3, "grid.png"),
    Keyboard(4, "keyboard.png"),
    MidBanner(5, "middleBanner.png"),
    Pause(6, "pause.png"),
    Play(7, "play.png"),
    Pointer(8, "pointer.png"),
    Stop(9, "stop.png"),
    Reset(10, "reset.png"),
    Save(11, "save.png"),
    Load(12, "load.png"),
    Prev(13, "prev.png"),
    Next(14, "next.png"),
    Piano(15, "P.png"),
    Marimba(16, "M.png"),
    Banjo(17, "B.png"),
    Saxophone(18, "S.png");
    
    private String file;
    private int index;

    Asset(int index, String file) {
        this.index = index;
        this.file = file;
    }

    public String getPath() {
        return "src/main/resources/" + this.file;
    }

    public int get() {
        return this.index;
    }
}