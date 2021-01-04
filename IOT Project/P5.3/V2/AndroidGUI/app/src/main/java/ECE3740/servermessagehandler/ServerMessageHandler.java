/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ECE3740.servermessagehandler;

import java.io.IOException;

/**
 *
 * @author Elsayyed
 */
public class ServerMessageHandler {

    userinterface.UserInterface myUI;
    ECE3740.client.Client myClient;
    String internalCommand = "";

    public ServerMessageHandler(ECE3740.client.Client myClient, userinterface.UserInterface myUI) {
        this.myClient = myClient;
        this.myUI = myUI;
    }

    public void displayServerMessage(String theCommand) {
        myUI.update(theCommand+"\n");
        internalCommand = "";
    }

    public void handleServerMessage(String msg) {
        if (msg.charAt(0) != 0xFFFF) { //Character.toString((char)-1)) = 0xFFFF
            internalCommand += msg;
        } else {

            if ((internalCommand.split(" "))[0].equals("Disconnect") || (internalCommand.split(" "))[0].equals("Quit")) {
                myClient.disconnectfromServer();

            } else if (internalCommand.contains("PB")){
                if (internalCommand.contains("3")) {
                    ((ECE3740.androidgui.MainActivity) (myUI)).setPB3Text(internalCommand);
                } else if (internalCommand.contains("2")) {
                    ((ECE3740.androidgui.MainActivity) (myUI)).setPB2Text(internalCommand);
                } else {
                    ((ECE3740.androidgui.MainActivity) (myUI)).setPB1Text(internalCommand);
                }
            }
            displayServerMessage(internalCommand);
        }
    }
        public void handleServerMessage (String msg, IOException e){
            myUI.update(msg + e);
        }
}
//    public void handleDisconnect(String msgServer) {
//        String localSocketAddress = myClient.getClientSocket();
//        String disconnectAck = "Disconnect Ack" + localSocketAddress;
//        if (msgServer.equals(disconnectAck)) {
//            myClient.disconnectfromServer();
//            internalCommand = "";
//        }
//    }
