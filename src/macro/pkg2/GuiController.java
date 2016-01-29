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
        textOutput.clear();
        String output = "";
        double x = 1;
        log("Processing...");
        for (SimpleMouseEvent mouseEvent : mouseEvents) {
            progressBar.setProgress(x / mouseEvents.size());
            output += ("Action " + x + ":    " + mouseEvent.toString() + "\n");
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
