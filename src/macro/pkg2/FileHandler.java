/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macro.pkg2;

import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author andyk
 */
public class FileHandler {

    final JFileChooser fc = new JFileChooser();
    GuiController gui;
    String filePath;
    JFrame frame;
    LinkedList<SimpleMouseEvent> mouseEvents;

    public FileHandler(LinkedList<SimpleMouseEvent> mouseEvent, GuiController gui) {
        
        this.gui = gui;
        
    }

    public void openFile() {
        frame.toFront();
        frame.repaint();
       selectFilePath();
       
        

    }
    
    public void saveFile()
    {
        frame.toFront();
        frame.repaint();
        selectFilePath();
    }
    
    private void selectFilePath(){
        int returnVal = fc.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filePath = fc.getSelectedFile().getName();
            System.out.println("Directory: "+filePath);
        }
        frame.dispose();
    }
}
