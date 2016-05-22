/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macro.pkg2;

/**
 *
 * @author andyk
 */
public interface MacroEvent {
        
        /**
         * 
         * @param args the parameters of the Event
         */
        public void setParameters(String[] args);
        
        /**
         * 
         * @return The type of event to be performed
         */
        public String getEventType();
        
        /**
         *
         * @return the parameters needed to perform the given action
         */
        public String[] getParameters();
        
}
