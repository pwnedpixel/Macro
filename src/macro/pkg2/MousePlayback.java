package macro.pkg2;

import java.awt.AWTException;
import java.awt.Robot;
import static java.awt.event.InputEvent.*;
import java.time.ZonedDateTime;
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
    private ZonedDateTime zdt = ZonedDateTime.now();//used for completion time
    private boolean keepAlive = true;//wether or not to keep the thread running.
    public boolean playback = false;//true if the macro is being played back
    private boolean pause = false;//true if the macro playback is paused
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
                    gui.log("Progress: " + (x + 1) + "/" + gui.count);//Shows how many times the macro has repeated
                    playback();
                    if (!playback) {
                        break;
                    }
                }
                //once the macro has been played back the designated amount of times, playback is discontinued
                playback = false;
                zdt =ZonedDateTime.now();
                gui.log("Macro Finished at " + zdt.getHour() + ":" + zdt.getMinute() + ":" + zdt.getSecond());
            }

            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Executes the recorded macro once.
     */
    private void playback()
    {
        double macroDuration = gui.macroDuration;//the duration of the macro in cycles (a cycle = 2ms)
        double currentCycle = 0;
        System.out.println("Playing Macro...");
        double x = 1;
        //executes one event at a time as long as playback is true, if not, the macro stops
        for (SimpleMouseEvent current : mouseEvents) {
            if (!playback) {
                break;
            }
            //If the macro playback is set to pause, the thread sleeps until the macro is told to resume
            if (pause) {
                pause();
                gui.log("Playback Continued.");
            }
            //displays the progress of the macro execution
            gui.progressBar.setProgress(currentCycle / macroDuration);
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
            } else if (current.click > 0) {
                //If the mouse was idle, it passes the number of cycles it remained so. playback waits that 
                //amount of time before continuing.
                for (int i = 0; i < current.click / 2; i++) {
                    gui.progressBar.setProgress(currentCycle / macroDuration);
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    currentCycle++;
                }
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            currentCycle++;
        }

    }

    public void togglePause()
    {
        pause = !pause;
    }

    public void pause()
    {
        gui.log("Playback Paused...");
        while (pause && playback) {
            try {
                Thread.sleep(250);
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
