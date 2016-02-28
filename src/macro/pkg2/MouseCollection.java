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
     */
    @Override
    public void run()
    {
        //System.out.println("Collecting Mouse Mouvement");
        while (keepAlive) { //While the Thread is alive, will check for the command to start collection
            collect();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Starts adding points to the mouseEvents list every 2 milliseconds
     *
     */
    private void collect()
    {
        int same = 0;//The number of times that the mouse has been in same position.
        pause = false;//whether or not the mouse is currently idle
        while (record) {
            if (!pause) {
                //If the mouse is not currently idle, A new point is added to the "mouseEvents" List. point has x and y coordinates
                mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, 0));
            }
            //If the mouse is currently set as idle, but mouvement has been detected: that is, if the current position is different than
            //where the mouse is when it was set as idle...
            if (pause && ((MouseInfo.getPointerInfo().getLocation().x) != mouseEvents.getLast().gx() || MouseInfo.getPointerInfo().getLocation().y != mouseEvents.getLast().gy())) {
                unpause();
            }
            //Detects if the current position of the mouse is the same as the last time it was checked. 
            if (mouseEvents.size() > 5 && (mouseEvents.getLast().toString().equals(mouseEvents.get(mouseEvents.size() - 2).toString()))) {
                //If position is the same, the "same" count is increased. 
                same++;
                //Once the mouse has been in the same place for 10 cycles, it is said to be idle.
                if (same == 10) {
                    pause = true;
                    //The stopwatch will count how long the mouse has been idle for
                    stopWatch.start();
                    System.out.println("Pause");
                }
            } else {
                //If the mouse has moved since last cycle, then the number of times it has been in the same 
                //position is set to 0.
                same = 0;
            }
            try {
                Thread.sleep(2);//Delay between cycles.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (!record) {
                gui.log("Recording Stopped.");
                gui.refreshActionList();
            }
        }
    }

    /**
     * Method that is called when a mouse button has been pressed. Used to add a mouse click action
     * to the list of events. -1 for leftmouse and -3 for right mouse
     * @param button The mouse button that was pressed.
     */
    public void mouseDown(int button)
    {//-1 for leftmouseDown, -3 for right mouse down
        if (record) {
            unpause();//ensures that the collection is not paused
            //Adds the appropriate action to the action list
            if (button == 1) {
                mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, -1));
            } else if (button == 2) {
                mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, -3));
            }
            //System.out.println("Added: " + mouseEvents.getLast().toString());
        }
    }

    /**
     * Method that is called when a mouse button has been release. Used to add a mouse click action
     * to the list of events. -2 for left mouse up and -4 for right mouse up.
     * @param button The mouse Button that was pressed.
     */
    public void mouseUp(int button)
    {//-2 for left mouse up, -4 for right mouse up
        if (record) {
            unpause(); //Ensures that the collection is not paused.
            if (button == 1) {
                mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, -2));
            } else if (button == 2) {
                mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, -4));
            }
            //System.out.println("Added: " + mouseEvents.getLast().toString());
        }
    }

    /**
     * Kills the collection thread. used when terminating program
     */
    public void kill()
    {
        this.keepAlive = false;
    }

    /**
     * called when there has been mouse mouvement to unpause the collection. Same as unpause()
     */
    public void mouseMoved()
    {
        unpause();
    }

    /**
     * Unpauses the collection of mouse points
     */
    public void unpause()
    {
        if (pause) {
            System.out.println("unpause");
            stopWatch.stop(); 
            //Adds a mouse event that includes the new mouse locaiton and also the number of miliseconds
            //that the collection has been paused.
            mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x,
                    MouseInfo.getPointerInfo().getLocation().y, (int) stopWatch.getTime()));
            System.out.println(mouseEvents.getLast().toString());
            stopWatch.reset();
            pause = false;
        }
    }

    /**
     * Starts collection mouse information. Clears previous mouse events.
     */
    public void startRecording()
    {
        gui.log("Recording Macro...");
        mouseEvents.clear();
        this.record = true;
    }

    /**
     * Stops the collection of mouse events
     */
    public void stopRecording()
    {
        this.record = false;
        unpause();
    }

    /**
     * Tells the collection which list to write mouse events to as well as which GuiController to use
     * @param mouseEvents The list of mouse events that the collection should write to.
     * @param gui The gui controller for the program.
     */
    public void setList(LinkedList<SimpleMouseEvent> mouseEvents, GuiController gui)
    {
        this.mouseEvents = mouseEvents;
        this.gui = gui;
    }
}
