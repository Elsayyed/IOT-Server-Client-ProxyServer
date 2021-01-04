package client;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

    InputStream input;
    OutputStream output;
    Socket clientSocket = null;
    standardiointerface.StandardIO myUI;
    servermessagehandler.ServerMessageHandler myServer;
    byte clientCommand = ' ';
    int portNumber = 5555, backlog = 1;
    boolean stopThisThread = false;
    String theCommand = "";

    public Client(int portNumber, int backlog, standardiointerface.StandardIO myUI) {
        this.portNumber = portNumber;
        this.backlog = backlog;
        this.myUI = myUI;
    }

    public void openConnectionToServer(String serverIP) {
        try {
             clientSocket = new Socket(serverIP, portNumber);
             input = clientSocket.getInputStream();
             output = clientSocket.getOutputStream();
        } catch (IOException e) {
            System.err.println(e.toString());
            System.err.println("Cannot connect to server. Exiting program.");
            System.exit(0);
        } finally {
        }
    }
    
    public void handleClientMessage(String msg) {
         
         if (msg.charAt(0)!=0xFFFF) { //Character.toString((char)-1)) = 0xFFFF
            theCommand += msg;
        } else {
            myServer = new servermessagehandler.ServerMessageHandler(this,myUI);
            myServer.execute(theCommand);
            theCommand = "";
        }
    }

    public void disconnectfromServer() {
        try {
            clientSocket.close();
            input = null;
            output = null;
            clientSocket = null;
            stopThisThread = true;
        } catch (IOException e) {
            System.err.println("cannot close stream/client socket; exiting.");
            System.exit(0);
        } finally {
        }
    }

    public boolean isConnected(){
        return clientSocket!=null;
    }

    public void getMessageFromServer() {
        Thread messageHandlingThread = new Thread(this);  
        messageHandlingThread.start();
    }

    public void sendMessageToServer(byte msg) {
        try {
            output.write(msg);
            output.flush();
        } catch (IOException e) {
            System.err.println("cannot send to socket; exiting program.");
            System.exit(0);
        } finally {
        }
    }

    public void setPort(int portNumber) {
        this.portNumber = portNumber;
    }

    public int getPort() {
        return this.portNumber;
    }

@Override
    public void run() {
        byte msg; 
        String theClientMessage = "";
        while (stopThisThread == false) {
            try {
                msg = (byte) input.read();
                theClientMessage = Character.toString((char)msg);
                handleClientMessage(theClientMessage);
            } catch (IOException e) {
                myUI.update("IOException: "
                        + e.toString()
                        + ". Stopping thread and disconnecting client: "
                        + clientSocket.getRemoteSocketAddress());
                disconnectfromServer();
                stopThisThread = true;
            }
        }
    }
}