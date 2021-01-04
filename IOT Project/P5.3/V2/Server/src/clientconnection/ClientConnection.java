/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientconnection;

import proxymessagehandler.ProxyMessageHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Elsayyed
 */

public class ClientConnection implements Runnable {

    InputStream input;
    OutputStream output;
    Socket clientSocket = null;
    ProxyMessageHandler myClientCommandHandler;
    server.Server myServer;
    boolean stopThisThread = false;

    public ClientConnection(Socket clientSocket, ProxyMessageHandler myClientCommandHandler, server.Server myServer) {
        this.clientSocket = clientSocket;
        this.myClientCommandHandler = myClientCommandHandler;
        this.myServer = myServer;
        try {
            input = clientSocket.getInputStream();
            output = clientSocket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }

    @Override
    public void run() {
        byte msg;
        String theClientMessage = "";
        while (stopThisThread == false) {
            try {
                msg = (byte) input.read();
                theClientMessage = Character.toString((char)msg);
                myClientCommandHandler.setMyAndroidConnection(this);
                myClientCommandHandler.handleClientMessage(this,theClientMessage);
            } catch (IOException e) {
                disconnectClient();
                stopThisThread = true;
            }
        }
    }

    private String byteToString(byte theByte) {
        byte[] theByteArray = new byte[1];
        String theString = null;
        theByteArray[0] = theByte;
        try {
            theString = new String(theByteArray, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } finally {
            return theString;
        }
    }

    public Socket getClientSocket() { return clientSocket; }

    public void sendMessageToClient(byte msg) {
        try {
            output.write(msg);
            output.flush();
        } catch (IOException e) {
            System.exit(0);
        }
    }

    public void sendStringMessageToClient(String theMessage) {
        for (int i = 0; i < theMessage.length(); i++) {
            byte msg = (byte) theMessage.charAt(i);
            sendMessageToClient(msg);
        }
    }

    public void clientDisconnect() {
        disconnectClient();
    }

    public void disconnectClient() {
        try {
            stopThisThread = true;
            clientSocket.close();
            clientSocket = null;
            input = null;
            output = null;
        } catch (IOException e) {
            System.exit(0);
        } finally {
        }
    }

}
