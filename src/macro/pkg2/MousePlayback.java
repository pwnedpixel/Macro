package macro.pkg2;

import java.awt.AWTException;
import java.awt.Robot;
import static java.awt.event.InputEvent.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextArea;

/**
 * Will be used to play the mouse events back, reading them from the mouseEvents
 * List.
 *
 * @author Andy
 */
public class MousePlayback extends Thread
{

    LinkedList<SimpleMouseEvent> mouseEvents;
    private boolean keepAlive = true;
    public boolean playback = false;
    GuiController gui;
    private Robot r = null;

    @Override
    public void run()
    {
        try {
            r = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(MousePlayback.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (keepAlive) {
            while (playback) {
                for (int x = 0; x < gui.count; x++) {
                    gui.log("Progress: " + (x + 1) + "/" + gui.count);
                    playback();
                    if (!playback) {
                        break;
                    }
                }
                playback = false;
                gui.log("Macro Finished");
            }

            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void playback()
    {
        System.out.println("Playing Macro...");
        double x = 1;
        for (SimpleMouseEvent current : mouseEvents) {
            if (!playback) {
                break;
            }
            gui.progressBar.setProgress(x / mouseEvents.size());
            x++;
            r.mouseMove(current.x, current.y);
            if (current.click == -1) {
                r.mousePress(BUTTON1_MASK);//press left mouse
            } else if (current.click == -2) {
                r.mouseRelease(BUTTON1_MASK);//release left mouse
            } else if (current.click == -3) {
                r.mousePress(BUTTON3_MASK);//press right mouse
                System.out.println("Press right mouse");
            } else if (current.click == -4) {
                r.mouseRelease(BUTTON3_MASK);//release right mouse
                System.out.println("Release right mouse");
            }else if (current.click > 0) {
                try {
                    Thread.sleep(current.click);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void click()
    {
        r.mousePress(BUTTON1_DOWN_MASK);
        r.mouseRelease(BUTTON1_DOWN_MASK);
    }

    public void stopPlayback()
    {
        playback = false;

    }

    public void setList(LinkedList<SimpleMouseEvent> mouseEvents, GuiController gui)
    {
        this.mouseEvents = mouseEvents;
        this.gui = gui;
    }

    public void kill()
    {
        keepAlive = false;
    }

}
