/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servermessagehandler;

import java.io.IOException;

/**
 *
 * @author ferens
 */
public class ServerMessageHandler {

    userinterface.UserInterface myUI;
    client.Client myClient;
    String internalCommand = "";

    public ServerMessageHandler(client.Client myClient, userinterface.UserInterface myUI) {
        this.myClient = myClient;
        this.myUI = myUI;
    }

    public void displayServerMessage(String theCommand) {
        myUI.update(theCommand);
        internalCommand = "";
    }

    public void handleServerMessage(String msg) {
        if (msg.charAt(0) != 0xFFFF) { //Character.toString((char)-1)) = 0xFFFF
            internalCommand += msg;
        } else {

            if ((internalCommand.split(" "))[0].equals("Disconnect") || (internalCommand.split(" "))[0].equals("Quit")) {
                myClient.disconnectfromServer();
            }
            displayServerMessage(internalCommand);
        }
    }

    public void handleServerMessage(String msg, IOException e) {
        myUI.update(msg + e);
    }

//    public void handleDisconnect(String msgServer) {
//        String localSocketAddress = myClient.getClientSocket();
//        String disconnectAck = "Disconnect Ack" + localSocketAddress;
//        if (msgServer.equals(disconnectAck)) {
//            myClient.disconnectfromServer();
//            internalCommand = "";
//        }
//    }
}
