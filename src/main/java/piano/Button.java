package piano;

import processing.core.PApplet;

/** Abstract class Button. A superclass for all buttons.
 * Implements the Drawable interface.
 */
public abstract class Button implements Drawable {
    int X_COORD;
    int Y_COORD;

    public Button() {
        int[] values = values();
        X_COORD = values[0];
        Y_COORD = values[1];
    }

    /**
     * 
     * @return  The x coordinate and y coordinate associated with the button,
     *          wrapped in an array.
     */
    public abstract int[] values();

    public abstract void render(PApplet app);
}