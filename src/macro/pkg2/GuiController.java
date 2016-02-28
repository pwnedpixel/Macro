/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macro.pkg2;

import java.util.LinkedList;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

/**
 *
 * @author andyk
 */
public class GuiController {

    TextArea logOutput;
    TextArea textOutput;
    ProgressBar progressBar;
    LinkedList<SimpleMouseEvent> mouseEvents;
    public double macroDuration=0;//duration in cycles. (each cycle is 2ms);
    public int count = 1;

    public GuiController(TextArea log, TextArea textOutput, LinkedList<SimpleMouseEvent> mouseEvents, ProgressBar progressBar) {
        this.logOutput = log;
        this.textOutput = textOutput;
        this.mouseEvents = mouseEvents;
        this.progressBar = progressBar;
    }

    public void log(String log) {
        logOutput.appendText("\n"+log);
    }
    public void logSL(String log)
    {
        logOutput.appendText(log);
    }

    public void refreshActionList() {
        macroDuration=0;
        textOutput.clear();
        String output = "";
        double x = 1;
        log("Processing...");
        for (SimpleMouseEvent mouseEvent : mouseEvents) {
            progressBar.setProgress(x / mouseEvents.size());
            output += ("Action " + ((int)x) + ":    " + mouseEvent.toString() + "\n");
            if (mouseEvent.gclick()<1)
            {
                macroDuration++;
            } else
            {
                macroDuration+=(mouseEvent.gclick()/2);
            }
            x++;
        }
        textOutput.setText(output);
        logSL(" Done");
    }

    public void setLoopCount(int count) {
        this.count = count;
        System.out.println(this.count);
    }

}
