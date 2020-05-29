package piano;

import org.junit.Test;

import processing.core.PApplet;
public class RenderAllTest {
    
    @Test
    public void renderAll() {
        // Just testing whether everything renders without issues.
        App app = new App();
        PApplet.runSketch(new String[] {""}, app);
        app.setup();
        app.draw();
    }
}