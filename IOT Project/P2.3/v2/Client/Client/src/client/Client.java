package client;

import java.io.*;
import java.net.*;
import servermessagehandler.ServerMessageHandler;

public class Client implements Runnable {
    
    InputStream input;
    OutputStream output;
    Socket clientSocket = null;
    userinterface.UserInterface myUI;
    ServerMessageHandler msgHandler;
    byte clientCommand = ' ';
    int portNumber = 5555, backlog = 1;
    String theCommand = "";
    boolean threadStop;
    
    public Client(int portNumber, int backlog, userinterface.UserInterface myUI) {
        this.portNumber = portNumber;
        this.backlog = backlog;
        this.myUI = myUI;
        msgHandler = new servermessagehandler.ServerMessageHandler(this,myUI);
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

    public void disconnectfromServer() {
        try {
            clientSocket.close();
            input = null;
            output = null;
            clientSocket = null;
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
        String theString="";
        threadStop = false;
        while (!threadStop) {
            try {
                msg = (byte) input.read();
                theString = Character.toString((char) msg);
                msgHandler.handleServerMessage(theString);
            } catch (IOException e) {
                String exceptionMessage = "Cannot read from socket; stopping thread and disconnecting client."+ "error message is: ";
                msgHandler.handleServerMessage(exceptionMessage,e);
                disconnectfromServer();
                threadStop = true;
            }
        }       
    }
}