/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servermessagehandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author ferens
 */
public class ServerMessageHandler {

    standardiouserinterface.StandardIO myUI;
    client.Client myClient;
    static String theCommand= "";

    public ServerMessageHandler(client.Client myClient, standardiouserinterface.StandardIO myUI) {
        this.myClient = myClient;
        this.myUI = myUI;
    }
    
    public void displayServerMessage(String theCommand) {
        myUI.update("Recieved Time: "+theCommand);
         theCommand = "";
    }
    
    public void handleServerMessage(String msg) {
         if (msg.charAt(0)!=0xFFFF) { //Character.toString((char)-1)) = 0xFFFF
            theCommand += msg;
            myUI.update("Recieved Time: "+msg);
        } else {
            myUI.update("Recieved "+theCommand);
            displayServerMessage(theCommand);
           
        }
    }
     
}
