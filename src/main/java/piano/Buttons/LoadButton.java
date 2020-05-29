package piano.Buttons;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import piano.Button;
import piano.Grid;
import piano.InstrumentManager;
import processing.core.PApplet;
import processing.core.PImage;

public class LoadButton extends Button {

    private PImage imgBack;
    private PImage imgFront;
    final int X_COORD = 185;
    final int Y_COORD = 5;

    public LoadButton(PImage imgBack, PImage imgFront) {
        this.imgBack = imgBack;
        this.imgFront = imgFront;
    }

    @Override
    public int[] values() {
        return new int[] { X_COORD, Y_COORD };
    }

    public void tick(InstrumentManager instManager, Grid grid) {
        System.out.println("load");
        try {
            // Getting the instrument byte
            FileInputStream loadStream = new FileInputStream("savedata.sav");
            int readByte = loadStream.read();
            if (readByte > 3 || readByte < 0) {
                System.out.println("Bad file" + " instrument");
                loadStream.close();
                return;
            }
            int instrumentIndex = readByte;

            // Reading the file and making sure it is a valid file
            // Also, preparing all the note positions (blockKeys)
            HashMap<Integer, List<Integer>> blockKeys = new HashMap<>();
            while ((readByte = loadStream.read()) != -1) {
                if (readByte < 0 || readByte > 31) {
                    System.out.println("Bad file"+" tick");
                    loadStream.close();
                    return;
                }
                int tick = readByte;
                blockKeys.put(tick, new ArrayList<Integer>());
                readByte = loadStream.read();
                if(readByte < 0 || readByte > 12) {
                    System.out.println("Bad file"+" size "+readByte);
                    loadStream.close();
                    return;
                }
                int size = readByte;
                for (int i = 0; i < size; i++) {
                    readByte = loadStream.read();
                    if (readByte < 0 || readByte > 12) {
                        System.out.println("Bad file"+" note");
                        loadStream.close();
                        return;
                    }
                    int note = readByte;
                    blockKeys.get(tick).add(note);
                }
            }
            
            loadStream.close();

            // The actual loading part
            instManager.setCurrentIndex(instrumentIndex);
            
            grid.clear();
            for (int tick: blockKeys.keySet()) {
                for (int note: blockKeys.get(tick)) {
                    grid.addRemoveNotes(new int[] {tick, note});
                }
            }

        } catch (IOException e) {
            System.out.println("No saves yet.");
        }
    }

    public void render(PApplet app) {
        app.image(imgBack, X_COORD, Y_COORD);
        app.image(imgFront, X_COORD, Y_COORD);
    }

}