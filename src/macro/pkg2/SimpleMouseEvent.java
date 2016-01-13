/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macro.pkg2;

/**
 *
 * @author Andy
 */
public class SimpleMouseEvent
{
    public int x;//x coordinates
    public int y;//y coordinates
    public int click;//click type. 0 = nothing, 1 = click, 2=mouse down, 3=mouse up.
    public SimpleMouseEvent(int x, int y, int click)
    {
        this.x = x;
        this.y = y;
        this.click = click;
    }
    public int gx()
    {
        return x;
    }
    public int gy()
    {
        return y;
    }
    public int gclick()
    {
        return click;
    }
    
    public String toString()
    {
        return (x + " " + y+" "+click);
    }
    
}
