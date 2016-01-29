/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macro.pkg2;

import java.util.LinkedList;
import javafx.scene.control.TextArea;

/**
 *
 * @author andyk
 */
public class GuiController {
    
    TextArea logOutput;
    TextArea textOutput;
    LinkedList<SimpleMouseEvent> mouseEvents;
    public int count = 1;
    
    public GuiController(TextArea log, TextArea textOutput,LinkedList<SimpleMouseEvent> mouseEvents)
    {
        this.logOutput=log;
        this.textOutput=textOutput;
        this.mouseEvents = mouseEvents;
    }
    
    public void log(String log)
    {
        logOutput.appendText(log+"\n");
    }
    
    public void refreshActionList()
    {
        textOutput.clear();
        String output="";
        int x=1;
        for (SimpleMouseEvent mouseEvent : mouseEvents) {     
           output+=("Action " +  x+ ":    " + mouseEvent.toString() + "\n");
            x++;
        }
        textOutput.setText(output);
    }
    
    public void setLoopCount(int count)
    {
        this.count = count;
        System.out.println(this.count);
    }
    
}
