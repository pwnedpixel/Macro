/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macro.pkg2;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author Andy
 */
public class GlobalKeyListener implements NativeKeyListener {

    boolean ctrlDown = false;
    boolean oneDown = false;
    boolean twoDown = false;
    boolean threeDown = false;
    boolean fourDown = false;
    private MouseCollection mouseRecorder;
    private MousePlayback mousePlayback;

    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        //System.out.println("Key Pressed: " + nke.getKeyCode());

        if (nke.getKeyCode() == 29) //control
        {
            ctrlDown = true;
            checkKeys();
        } else if (nke.getKeyCode() == 2)//one
        {
            oneDown = true;
            checkKeys();
        } else if (nke.getKeyCode() == 3)//two
        {
            twoDown = true;
            checkKeys();
        } else if (nke.getKeyCode() == 4)//three
        {
            threeDown = true;
            checkKeys();
        } else if (nke.getKeyCode() == 5)//four
        {
            fourDown = true;
            checkKeys();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {
        // System.out.println(ctrlDown+" "+oneDown+" "+twoDown+" "+threeDown);
        if (nke.getKeyCode() == 29) {
            ctrlDown = false;
        } else if (nke.getKeyCode() == 2) {
            oneDown = false;
        } else if (nke.getKeyCode() == 3) {
            twoDown = false;
        } else if (nke.getKeyCode() == 4)//three
        {
            threeDown = false;
        } else if(nke.getKeyCode()==5)//four
        {
            fourDown=false;
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
        // System.out.println("KeyTyped");
    }

    private void checkKeys() {
        if (ctrlDown == true && oneDown == true) {
            //  System.out.println("Shortcut to start");
            mouseRecorder.startRecording();
        }
        if (ctrlDown == true && twoDown == true) {
            //System.out.println("Shortcut to stop");
            mouseRecorder.stopRecording();
        }
        if (ctrlDown == true && threeDown == true) {
            System.out.println("Stopping Macro");
            mousePlayback.stopPlayback();
        } if (ctrlDown==true&& fourDown==true)
        {
            System.out.println("toggle pause");
            mousePlayback.togglePause();
        }
    }

    public void passParams(MouseCollection mouseRecorder, MousePlayback mousePlayback) {
        this.mouseRecorder = mouseRecorder;
        this.mousePlayback = mousePlayback;
    }

}
