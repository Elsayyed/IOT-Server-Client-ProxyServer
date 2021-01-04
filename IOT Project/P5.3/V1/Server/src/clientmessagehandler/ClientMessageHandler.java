/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientmessagehandler;

import clientconnection.ClientConnection;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Elsayyed
 */
public class ClientMessageHandler {

    clientconnection.ClientConnection myAndroidConnection;
    client.Client clientToBoard;
    server.Server serverToAndroid;
    String theCommand = "";
    private int flag = 0;

    public ClientMessageHandler(server.Server myServer, client.Client myClient) {
        this.clientToBoard = myClient;
        this.serverToAndroid = myServer;
    }

    public void setMyAndroidConnection(clientconnection.ClientConnection myAndroidConnection){
        this.myAndroidConnection = myAndroidConnection;
    }

    public void handleClientMessage(clientconnection.ClientConnection myAndroidConnection, String msg) {
        setMyAndroidConnection(myAndroidConnection);

        if (msg.charAt(0) != 0xFFFF) { //Character.toString((char)-1)) = 0xFFFF
            theCommand += msg;
        }
        else if (theCommand.contains("Ack") || theCommand.contains("Temp") || theCommand.contains("PB")){ //" Temperature %.4f *C "
            handleCompleteBoardMessage(myAndroidConnection,theCommand);
            theCommand = "";
        }
        else {
            handleCompleteAndroidMessage(myAndroidConnection,theCommand);
            theCommand = "";
        }
    }

    public void handleCompleteBoardMessage(clientconnection.ClientConnection myAndroidConnection,String theCommand) {
            this.myAndroidConnection.sendStringMessageToClient(theCommand);
            this.myAndroidConnection.sendMessageToClient((byte) 0xFF);
    }

    public void handleCompleteAndroidMessage(clientconnection.ClientConnection myAndroidConnection, String theCommand) {

     //   setMyAndroidConnection(myAndroidConnection);

        switch (theCommand) {
            case "d":
                myAndroidConnection.sendStringMessageToClient("Disconnect Ack: " + myAndroidConnection.getClientSocket().getRemoteSocketAddress());
                myAndroidConnection.clientDisconnect();
                break;

            case "t":
                Calendar cal = Calendar.getInstance();
                myAndroidConnection.sendStringMessageToClient("The time is: ");
                cal.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                for (int i = 0; i < sdf.format(cal.getTime()).length(); i++) {
                    byte msg;
                    msg = (byte) sdf.format(cal.getTime()).charAt(i);
                    myAndroidConnection.sendMessageToClient(msg);
                }
                myAndroidConnection.sendMessageToClient((byte) 0xFF);
                break;
            default:
                clientToBoard.sendMessageToServer(theCommand);
                clientToBoard.sendMessageToServer((byte)0xFF);
                break;
        }
    }

    public ClientConnection getMyAndroidConnection() {
        return myAndroidConnection;
    }

    public void update(String theCommand){
        this.myAndroidConnection.sendStringMessageToClient(theCommand);
        this.myAndroidConnection.sendMessageToClient((byte) 0xFF);
    }


}



