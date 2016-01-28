package macro.pkg2;

import java.awt.AWTException;
import java.awt.Robot;
import static java.awt.event.InputEvent.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.InputEvent;

/**
 * Will be used to play the mouse events back, reading them from the mouseEvents
 * List.
 *
 * @author Andy
 */
public class MousePlayback extends Thread {

    LinkedList<SimpleMouseEvent> mouseEvents;
    private boolean keepAlive = true;
    public boolean playback = false;
    private Robot r = null;

    public void run() {
        try {
            r = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(MousePlayback.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (keepAlive) {
            if (playback) {
                System.out.println("Playing back");
                for (SimpleMouseEvent current : mouseEvents) {
                    r.mouseMove(current.x, current.y);
                    if (current.click == 1) {
                        click();
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                playback = false;
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void click() {
        
        System.out.println("CLICKING");
        r.mousePress(BUTTON1_DOWN_MASK);
        r.mouseRelease(BUTTON1_DOWN_MASK);
    }

    public void setList(LinkedList<SimpleMouseEvent> mouseEvents) {
        this.mouseEvents = mouseEvents;
    }
}
