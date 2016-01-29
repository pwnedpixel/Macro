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

/**
 * Mouse Collection object adds the mouse position to a list at a given inteval.
 *
 * @author Andy
 *
 */
public class MouseCollection extends Thread {

    public boolean record = false;
    GuiController gui;
    private boolean keepAlive = true;
    private LinkedList<SimpleMouseEvent> mouseEvents;
    int index = 0;

    /**
     *
     * @param mouseEvents A list of SimpleMouseEvents that will be recorded
     * every 10 ms, or whenever a button is pressed.
     */
    @Override
    public void run() {
        //System.out.println("Collecting Mouse Mouvement");
        while (keepAlive) {
            collect();
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Starts adding points to the mouseEvents list every 5ms.
     */
    private void collect() {
        while (record) {
            mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, 0));
            //System.out.println("Added : "+mouseEvents.getLast().toString());
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

    public void mouseClicked() {
        if (record) {
            mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, 1));
            //System.out.println("Added : " + mouseEvents.getLast().toString());
        }
    }

    public void mouseDown() {
        if (record) {
            mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, 2));
            //System.out.println("Added: " + mouseEvents.getLast().toString());
        }
    }

    public void mouseUp() {
        if (record) {
            mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, 3));
           // System.out.println("Added: " + mouseEvents.getLast().toString());
        }
    }

    public void kill() {
        this.keepAlive = false;
    }

    public void startRecording() {
        gui.log("Recording Macro...");
        mouseEvents.clear();
        this.record = true;
    }

    public void stopRecording() {
        this.record = false;
    }

    public void setList(LinkedList<SimpleMouseEvent> mouseEvents, GuiController gui) {
        this.mouseEvents = mouseEvents;
        this.gui = gui;
    }
}
