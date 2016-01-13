/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macro.pkg2;

import java.util.LinkedList;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

/**
 *
 * @author Andy
 */
public class GlobalMouseListener implements NativeMouseInputListener
{
    LinkedList<MouseCollection> mouseCollectors;
    @Override
    public void nativeMouseClicked(NativeMouseEvent e)
    {
        System.out.println("Mouse Clicked");
        mouseCollectors.get(0).mouseDown();
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e)
    {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e)
    {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e)
    {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setThreads(LinkedList<MouseCollection> mouseCollectors)
    {
        this.mouseCollectors = mouseCollectors;
    }
    
}
