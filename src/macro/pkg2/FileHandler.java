/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macro.pkg2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author andyk
 */
public class FileHandler
{

    final JFileChooser fc = new JFileChooser();
    GuiController gui;
    String filePath;
    JFrame frame = new JFrame();
    LinkedList<SimpleMouseEvent> mouseEvents;
    FileReader fileReader;

    public void openFile()
    {
        selectFilePath();
        mouseEvents.clear();
        File file = new File(filePath);
        Scanner scan = null;
        String strx="",stry="",strclick="";
        int x,y,click;
        String currentLine;
        try {
            scan = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        gui.log("Opening "+filePath+"\t");
        while (scan.hasNextLine())
        {
            currentLine=scan.nextLine();
            //find x          
            int i=0;
            while(currentLine.length()>i&&currentLine.charAt(i)!=' ')
            {
                strx+=currentLine.charAt(i);
                i++;
            }
            x=Integer.parseInt(strx);
            i++;
            //find y
            while(currentLine.length()>i&&currentLine.charAt(i)!=' ')
            {
                stry+=currentLine.charAt(i);
                i++;
            }
             y=Integer.parseInt(stry);
            i++;
            //Find click
             while(currentLine.length()>i&&currentLine.charAt(i)!=' ')
            {
               strclick+=currentLine.charAt(i);
                i++;
            }
              click=Integer.parseInt(strclick);
             mouseEvents.add(new SimpleMouseEvent(x, y, click));
            System.out.println("Added " + mouseEvents.getLast().toString());
             strx="";
             stry="";
             strclick="";
        }
        gui.refreshActionList();
        gui.log("File Opened");
    }

    public void saveFile()
    {
        selectFilePath();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "utf-8"))) {
            gui.log("Saving... ");
            float x = 1;
            for (SimpleMouseEvent mouseEvent : mouseEvents) {
                writer.write(mouseEvent.toString() + "\n");
                gui.progressBar.setProgress(x / mouseEvents.size());
                x++;
            }
            gui.logSL("Done.");

        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void selectFilePath()
    {
        int returnVal = fc.showOpenDialog(frame);
        frame.toFront();
        frame.repaint();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filePath = fc.getSelectedFile().getAbsolutePath();
            System.out.println("Directory: " + filePath);
        }
        frame.dispose();
    }

    public void setList(LinkedList<SimpleMouseEvent> mouseEvents, GuiController gui)
    {
        this.mouseEvents = mouseEvents;
        this.gui = gui;
    }
}
