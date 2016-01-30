/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macro.pkg2;

import java.awt.MouseInfo;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextArea;
import org.apache.commons.lang3.time.StopWatch;

/**
 * Mouse Collection object adds the mouse position to a list at a given inteval.
 *
 * @author Andy
 *
 */
public class MouseCollection extends Thread
{

    public boolean record = false;
    public boolean pause = false;
    StopWatch stopWatch = new StopWatch();
    GuiController gui;
    private boolean keepAlive = true;
    private LinkedList<SimpleMouseEvent> mouseEvents;
    int index = 0;

    /**
     *
     * @param mouseEvents A list of SimpleMouseEvents that will be recorded
     * every 2 ms, or whenever a button is pressed.
     */
    @Override
    public void run()
    {
        //System.out.println("Collecting Mouse Mouvement");
        while (keepAlive) {
            collect();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Starts adding points to the mouseEvents list every 5ms.
     */
    private void collect()
    {
        int same = 0;
        pause = false;
        while (record) {
            if (!pause) {
                mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, 0));
            }
            if (pause && ((MouseInfo.getPointerInfo().getLocation().x) != mouseEvents.getLast().gx() || MouseInfo.getPointerInfo().getLocation().y != mouseEvents.getLast().gy())) {
                unpause();
            }
            if (mouseEvents.size() > 5 && (mouseEvents.getLast().toString().equals(mouseEvents.get(mouseEvents.size() - 2).toString()))) {
                same++;
                if (same == 10) {
                    pause = true;
                    stopWatch.start();
                    System.out.println("Pause");
                }
            } else {
                same = 0;
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (!record) {
                gui.log("Recording Stopped.");
                gui.refreshActionList();
            }
        }
    }

    public void mouseDown()
    {
        if (record) {
            unpause();
            mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, -1));
            //System.out.println("Added: " + mouseEvents.getLast().toString());
        }
    }

    public void mouseUp()
    {
        if (record) {
            unpause();
            mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, -2));
            // System.out.println("Added: " + mouseEvents.getLast().toString());
        }
    }

    public void kill()
    {
        this.keepAlive = false;
    }

    public void mouseMoved()
    {
        unpause();
    }

    public void unpause()
    {
        if (pause) {
            System.out.println("unpause");
            stopWatch.stop();
            mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x,
                    MouseInfo.getPointerInfo().getLocation().y, (int) stopWatch.getTime()));
            System.out.println(mouseEvents.getLast().toString());
            stopWatch.reset();
            pause = false;
        }
    }

    public void startRecording()
    {
        gui.log("Recording Macro...");
        mouseEvents.clear();
        this.record = true;
    }

    public void stopRecording()
    {
        this.record = false;
        unpause();
    }

    public void setList(LinkedList<SimpleMouseEvent> mouseEvents, GuiController gui)
    {
        this.mouseEvents = mouseEvents;
        this.gui = gui;
    }
}
