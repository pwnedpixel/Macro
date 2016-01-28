/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macro.pkg2;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;

/**
 * Mouse Collection object adds the mouse position to a list at a given inteval. 
 * @author Andy
 *
 */
public class MouseCollection extends Thread
{

    private boolean record = false;
    private boolean keepAlive = true;
    private LinkedList<SimpleMouseEvent> mouseEvents;
    int index = 0;

    /**
     *
     * @param mouseEvents A list of SimpleMouseEvents that will be recorded
     * every 10 ms, or whenever a button is pressed.
     */
    @Override
    public void run()
    {
        System.out.println("Collecting Mouse Mouvement");
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
    private void collect()
    {
        while (record) {
            mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, 0));
            //System.out.println("Added : "+mouseEvents.getLast().toString());
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

        }
    }

    public void mouseDown()
    {
        if (record) {
            mouseEvents.add(new SimpleMouseEvent(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, 1));
            System.out.println("Added : " + mouseEvents.getLast().toString());
        }
    }

    public void kill()
    {
        this.keepAlive = false;
    }

    public void startRecording()
    {
        this.record = true;
    }

    public void stopRecording()
    {
        this.record = false;
    }

    public void setList(LinkedList<SimpleMouseEvent> mouseEvents)
    {
        this.mouseEvents = mouseEvents;
    }
}
