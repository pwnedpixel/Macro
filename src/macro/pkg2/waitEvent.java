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
public class waitEvent implements MacroEvent{
        String[] parameters = new String[1];

        /**
         *
         * @param args contains the amount of time to wait.
         */
        public waitEvent(String[] args)
        {
                parameters = args.clone();
        }
        
        @Override
        public String getEventType() {
               return "wait";
        }

        @Override
        public String[] getParameters() {
                return parameters;
        }

        @Override
        public void setParameters(String[] args) {
                parameters = args.clone();
        }
        
       
}
