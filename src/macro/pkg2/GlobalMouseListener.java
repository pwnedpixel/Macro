/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macro.pkg2;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

/**
 *
 * @author Andy
 */
public class GlobalMouseListener implements NativeMouseInputListener
{

    private MouseCollection mouseRecorder;

    @Override
    public void nativeMouseClicked(NativeMouseEvent e)
    {

    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e)
    {
        mouseRecorder.mouseDown(e.getButton());
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e)
    {
        mouseRecorder.mouseUp(e.getButton());
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e)
    {
        this.mouseRecorder.mouseMoved();
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void passRecorder(MouseCollection mouseRecorder)
    {
        this.mouseRecorder = mouseRecorder;
    }

}
