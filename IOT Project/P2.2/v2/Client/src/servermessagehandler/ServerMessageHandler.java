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

    standardiointerface.StandardIO myUI;
    client.Client myClient;

    public ServerMessageHandler(client.Client myClient, standardiointerface.StandardIO myUI) {
        this.myClient = myClient;
        this.myUI = myUI;
    }
    
    public void execute(String theCommand) {
        myUI.update(theCommand);
    }

}
