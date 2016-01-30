/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macro.pkg2;

import java.awt.Robot;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 *
 * @author Andy
 */
public class FXMLDocumentController implements Initializable {

    private Robot r = null;
    private final Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
    private MousePlayback mousePlayback = new MousePlayback();
    private LinkedList<SimpleMouseEvent> mouseEvents = new LinkedList();
    private GlobalMouseListener mouseListener = new GlobalMouseListener();
    private GlobalKeyListener keyboardListener = new GlobalKeyListener();
    private MouseCollection mouseRecorder = new MouseCollection();
    private FileHandler fileHandler=new FileHandler();
    private GuiController gui;
    
    // <editor-fold desc="FXML Declarations">
    @FXML
    Button btnStartRecord;

    @FXML
    TextArea textOutput;

    @FXML
    Button btnStopRecord;

    @FXML
    MenuItem menuBarExit;

    @FXML
    Button btnPlayback;

    @FXML
    TextArea logOutput;

    @FXML
    TextField loopCount;

    @FXML
    Button setLoop;
    
    @FXML
    MenuItem saveButton;
    
    @FXML
    MenuItem openButton;
    
    @FXML
    ProgressBar progressBar;
    //</editor-fold>

    @FXML
    private void playbackEvent(ActionEvent e) {
        mouseRecorder.stopRecording();
        gui.log("Running Macro...");
        mousePlayback.playback = true;

    }

    @FXML
    private void menuBarExitEvent(ActionEvent e) {
        removeHooks();
        gui.log("Exiting...");
        mouseRecorder.kill();
        mousePlayback.kill();
        Platform.exit();
    }

    @FXML
    private void startRecordingEvent(ActionEvent e) {
        System.out.println("Start Recording");
        mouseRecorder.startRecording();
        // initMouseHooks();
    }

    @FXML
    private void stopRecordingEvent(ActionEvent e) {
        System.out.println("Stop Recording");
        mouseRecorder.stopRecording();
    }
    
    @FXML
    private void saveFileEvent(ActionEvent e)
    {
       fileHandler.saveFile();
    }
    
    @FXML
    private void openFileEvent(ActionEvent e)
    {
        fileHandler.openFile();
    }

    @FXML
    private void setLoopCount(ActionEvent e) {
        gui.setLoopCount(Integer.parseInt(loopCount.getText()));
        
    }

    private void initHooks() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(keyboardListener);
        GlobalScreen.addNativeMouseListener(mouseListener);

        mouseListener.passRecorder(mouseRecorder);
        keyboardListener.passParams(mouseRecorder, mousePlayback);
    }

    private void removeHooks() {
        GlobalScreen.removeNativeMouseListener(mouseListener);
        GlobalScreen.removeNativeKeyListener(keyboardListener);
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.setLevel(Level.WARNING);
        gui = new GuiController(logOutput, textOutput, mouseEvents,progressBar);
        mouseRecorder.setList(mouseEvents, gui);
        mouseRecorder.start();
        mousePlayback.setList(mouseEvents, gui);
        mousePlayback.start();
        fileHandler.setList(mouseEvents, gui);
        initHooks();
    }

}
