package macro.pkg2;

import com.sun.glass.ui.Robot;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Andy
 */
public class MousePlayback extends Thread
{

    LinkedList<SimpleMouseEvent> mouseEvents;
    private boolean keepAlive = true;
    private final Robot r = com.sun.glass.ui.Application.GetApplication().createRobot();

    public void run()
    {
        System.out.println("Playing back");
        for (SimpleMouseEvent current : mouseEvents) {
            r.mouseMove(current.x, current.y);
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void setList(LinkedList<SimpleMouseEvent> mouseEvents)
    {
        this.mouseEvents = mouseEvents;
    }
}
