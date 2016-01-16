/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macro.pkg2;

import com.sun.glass.ui.Robot;
import java.awt.AWTException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.InputEvent;
import static javafx.scene.input.MouseEvent.*;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 *
 * @author Andy
 */
public class FXMLDocumentController implements Initializable
{

    private LinkedList<SimpleMouseEvent> mouseEvents = new LinkedList();
    private Robot r = com.sun.glass.ui.Application.GetApplication().createRobot();
    private GlobalMouseListener mouseListener = new GlobalMouseListener();
    private MouseCollection mouseRecorder = new MouseCollection();
    @FXML
    Button btnStartRecord;

    @FXML
    Button btnStopRecord;

    @FXML
    Menu menuBarExit;

    @FXML
    Button btnPlayback;

    @FXML
    private void playbackEvent(ActionEvent e)
    {
        mouseRecorder.stopRecording();
        for (SimpleMouseEvent current : mouseEvents) {
            r.mouseMove(current.x, current.y);
            if (current.click == 1) {
                System.out.println("CLICK");
                r.mousePress(0);
                r.mouseRelease(0);
                //r.mouseRelease(InputEvent.BUTTON1_MASK);
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        mouseRecorder.stopRecording();
//        MousePlayback playbackThread = new MousePlayback();
//        playbackThread.setList(mouseEvents);
//        playbackThread.start();
    }

    @FXML
    private void menuBarExitEvent(ActionEvent e)
    {
        System.out.println("Exit");
        mouseRecorder.kill();
    }

    @FXML
    private void startRecordingEvent(ActionEvent e)
    {
        mouseEvents.clear();
        System.out.println("Start Recording");
        mouseRecorder.startRecording();
        initHooks();
    }

    @FXML
    private void stopRecordingEvent(ActionEvent e)
    {
        System.out.println("Stop Recording");
        mouseRecorder.stopRecording();
        removeHooks();
    }

    private void initHooks()
    {
        // Get the logger for "org.jnativehook" and set the level to warning.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(e.getMessage());
        }

        GlobalScreen.addNativeMouseListener(mouseListener);
        mouseListener.passRecorder(mouseRecorder);
    }

    private void removeHooks()
    {
        GlobalScreen.removeNativeMouseListener(mouseListener);
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        mouseRecorder.setList(mouseEvents);
        mouseRecorder.start();
    }

}
