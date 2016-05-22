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
public class keyEvent implements MacroEvent {

        String[] parameters = new String[2];

        public keyEvent(String[] args) {
                parameters = args.clone();
        }

        @Override
        public void setParameters(String[] args) {
                 parameters = args.clone();
        }

        @Override
        public String getEventType() {
                return "key";
        }

        @Override
        public String[] getParameters() {
               return parameters;
        }

}
